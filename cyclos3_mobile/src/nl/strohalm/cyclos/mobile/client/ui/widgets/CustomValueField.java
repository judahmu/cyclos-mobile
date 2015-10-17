/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.mobile.client.ui.widgets;

import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.model.CustomField;
import nl.strohalm.cyclos.mobile.client.model.CustomFieldType;
import nl.strohalm.cyclos.mobile.client.model.CustomValueException;
import nl.strohalm.cyclos.mobile.client.model.CustomValueException.ValidationType;
import nl.strohalm.cyclos.mobile.client.model.InitialData;
import nl.strohalm.cyclos.mobile.client.model.PossibleValue;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays an according editor depending on the custom field type
 */
public class CustomValueField extends Composite implements UpdatableWidget {   
    
    private FlowPanel   container;
    private Widget      editor;
    private CustomField customField;
    private NativeEvent event;

    public CustomValueField(CustomField customField) {
        this.customField = customField;
        
        event = Document.get().createChangeEvent();       

        container = new FlowPanel();
        editor = resolveEditor();

        container.add(editor);

        initWidget(container);
    }
    
    /**
     * Sets the editor possible values for enumerated custom fields
     */
    @SuppressWarnings("unchecked")
    public void setPossibleValues(JsArray<PossibleValue> possibleValues) {
        if(customField.getType() == CustomFieldType.ENUMERATED) {
            switch (customField.getControl()) {
                case RADIO:
                    RadioButtonGroupField<PossibleValue> radioGroup = (RadioButtonGroupField<PossibleValue>) editor;
                    radioGroup.setOptions(possibleValues);
                    radioGroup.selectFirst();
                    break;
                case SELECT:
                    SelectionField<PossibleValue> selection = (SelectionField<PossibleValue>) editor;
                    selection.setItems(possibleValues);
                    selection.selectFirst();
                    break;
            }
        }
    }
    
    /**
     * Adds a change event handler
     */
    public void addChangeHandler(ChangeHandler handler) {
        addDomHandler(handler, ChangeEvent.getType());
    }

    /**
     * Returns the handled custom field
     */
    public CustomField getCustomField() {
        return customField;
    }
    
    /**
     * Returns the used custom field editor
     */
    public Widget getEditor() {
        return editor;
    }
    
    /**
     * Returns the according JSONValue depending on the custom field type
     */
    @SuppressWarnings("unchecked")
    public JSONObject getValue() {
        JSONObject object = new JSONObject();
        object.put(ParameterKey.INTERNAL_NAME, new JSONString(customField.getInternalName()));
        switch (customField.getType()) {
            case BOOLEAN:
                BooleanField bool = (BooleanField) editor;
                Boolean boolVal = bool.getValue();
                validate(boolVal);
                object.put(ParameterKey.VALUE, new JSONString(String.valueOf(boolVal)));
                break;
            case DATE:
                DateField date = (DateField) editor;
                String strDate = date.getValue();
                validate(strDate);
                object.put(ParameterKey.VALUE, new JSONString(strDate));
                break;
            case INTEGER:
            case DECIMAL:
                NumberField number = (NumberField) editor;
                String strNum = number.getValue();
                validate(strNum);
                object.put(ParameterKey.VALUE, new JSONString(strNum));
                break;
            case ENUMERATED:
                switch (customField.getControl()) {
                    case RADIO:
                        RadioButtonGroupField<PossibleValue> radio = (RadioButtonGroupField<PossibleValue>) editor;
                        PossibleValue radioValue = radio.getValue();
                        String radioVal = radioValue != null ? radioValue.getId().toString() : "";
                        validate(radioVal);
                        object.put(ParameterKey.POSSIBLE_VALUE_ID, new JSONString(radioVal));                        
                        break;
                    case SELECT:
                        SelectionField<PossibleValue> selection = (SelectionField<PossibleValue>) editor;
                        PossibleValue listValue = selection.getSelectedItem();
                        String listVal = listValue != null ? listValue.getId().toString() : ""; 
                        validate(listVal);
                        object.put(ParameterKey.POSSIBLE_VALUE_ID, new JSONString(listVal));
                        break;
                }
            case STRING:
            case URL:
                switch (customField.getControl()) {
                    case TEXTAREA:
                    case RICH_EDITOR:
                        TextAreaField textArea = (TextAreaField) editor;
                        String areaVal = textArea.getValue();
                        validate(areaVal);
                        object.put(ParameterKey.VALUE, new JSONString(textArea.getValue()));
                        break;
                    case TEXT:
                        TextField textField = (TextField) editor;
                        String txtVal = textField.getValue();
                        validate(txtVal);
                        object.put(ParameterKey.VALUE, new JSONString(textField.getValue()));
                        break;
                }
        }
        return object;
    }
    
    /**
     * Validates the current custom field control value
     */
    private void validate(Object value) {
        if(customField.isRequired() && (value == null || StringHelper.isEmpty(value.toString()))) {
            throw new CustomValueException(customField, ValidationType.REQUIRED);            
        }  
        Integer minLength = customField.getMinLength();
        if(minLength != null && minLength > 0) {
            if(value == null || StringHelper.isEmpty(value.toString()) || value.toString().length() < customField.getMinLength()) {
                throw new CustomValueException(customField, ValidationType.MIN_LENGTH);
            }          
        }
        Integer maxLength = customField.getMaxLength();
        if(maxLength != null && maxLength > 0) {
            if(value != null && StringHelper.isNotEmpty(value.toString()) && value.toString().length() > customField.getMaxLength()) {
                throw new CustomValueException(customField, ValidationType.MAX_LENGTH);
            }
        }       
    }
    
    /**
     * Returns the selected possible value in case of custom field type is enumerated.<br>
     * Otherwise this method will return null.
     */
    @SuppressWarnings("unchecked")
    public PossibleValue getSelectedPossibleValue() {
        switch(customField.getType()) {
            case ENUMERATED:
                switch(customField.getControl()) {
                    case RADIO:
                        RadioButtonGroupField<PossibleValue> radioGroup = (RadioButtonGroupField<PossibleValue>) editor;
                        return radioGroup.getValue();
                    case SELECT:
                        SelectionField<PossibleValue> selection = (SelectionField<PossibleValue>) editor;
                        return selection.getSelectedItem();
                }
        }
        return null;
    }

    /**
     * Resolves the widget editor for this custom field depending on the custom field type
     */
    private Widget resolveEditor() {
        switch (customField.getType()) {
            case BOOLEAN:
                return new BooleanField();
            case DATE:
                return new DateField();
            case INTEGER:
                return new NumberField();
            case DECIMAL:
                InitialData data = LoggedUser.get().getInitialData();
                return new NumberField(data.getDecimalCount(), data.getDecimalSeparator());
            case ENUMERATED:
                switch (customField.getControl()) {
                    case RADIO:
                        RadioButtonGroupField<PossibleValue> radioGroup = new RadioButtonGroupField<PossibleValue>(customField.getInternalName()) {
                            @Override
                            protected String getRadioValue(PossibleValue option) {
                                return option.getValue();
                            }       
                            @Override
                            protected void onOptionSelected(PossibleValue option) {
                                DomEvent.fireNativeEvent(event, CustomValueField.this);
                            }
                            @Override
                            protected boolean isDefault(PossibleValue option) {
                                return option.isDefault();
                            }
                        };
                        return radioGroup;
                    case SELECT:
                        return new SelectionField<PossibleValue>() {
                            @Override
                            protected String getDisplayName(PossibleValue option) {
                                return option.getValue();
                            }
                            @Override
                            protected void onItemSelected(PossibleValue item) {
                                DomEvent.fireNativeEvent(event, CustomValueField.this);
                            }
                            @Override
                            protected boolean isDefault(PossibleValue option) {
                                return option.isDefault();
                            }
                        };
                }
            case STRING:
            case URL:
                switch (customField.getControl()) {
                    case TEXTAREA:
                    case RICH_EDITOR:
                        return new TextAreaField();
                    case TEXT:
                        return new TextField();
                }
        }
        return null;
    }

    @Override
    public void updateWidget(Widget widget) {
        if(widget instanceof CustomValueField) {
            CustomValueField parent = (CustomValueField) widget;
            if(parent.getSelectedPossibleValue() != null) {
                JsArray<PossibleValue> values = resolvePossibleValues(parent.getSelectedPossibleValue().getId());
                setPossibleValues(values);
            }
        }
    }
    
    /**
     * Resolves the possible values for each possible value parent
     */
    private JsArray<PossibleValue> resolvePossibleValues(Long possibleValueParentId) {
        JsArray<PossibleValue> values = getNativeArray();
        if(customField.getPossibleValues() != null && customField.getPossibleValues().length() > 0) {
            for(int i = 0; i < customField.getPossibleValues().length(); i++) {
                PossibleValue value = customField.getPossibleValues().get(i);
                if(value.getParentId().equals(possibleValueParentId)) {
                    values.push(value);
                }
            }   
        }
        return values;
    }
 
    /**
     * Creates an empty native array of possible values
     */
    private native JsArray<PossibleValue> getNativeArray() /*-{
        var nativeArray = [];
        return nativeArray;
    }-*/;
}
