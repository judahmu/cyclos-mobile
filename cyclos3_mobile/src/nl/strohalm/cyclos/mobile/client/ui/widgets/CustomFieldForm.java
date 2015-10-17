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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.mobile.client.model.CustomField;
import nl.strohalm.cyclos.mobile.client.model.CustomFieldType;
import nl.strohalm.cyclos.mobile.client.ui.UpdateWidget;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A form which renders custom fields and resolves parent-child dependencies between them 
 */
public class CustomFieldForm extends Composite implements UpdateWidget {
    
    private FlowPanel container;
    private JsArray<CustomField> customFields;
    private List<CustomValueField> customValueFields;
    private Map<CustomValueField, List<CustomValueField>> parents;
    
    public CustomFieldForm() {
        this.customValueFields = new ArrayList<CustomValueField>();
        this.parents = new LinkedHashMap<CustomValueField, List<CustomValueField>>();
        
        container = new FlowPanel();
        
        initWidget(container);
    }
    
    /**
     * Returns the custom value fields set in the form
     */
    public List<CustomValueField> getCustomValueFields() {
        return customValueFields;
    }
    
    /**
     * Sets the form's custom fields
     */
    public void setCustomFields(JsArray<CustomField> customFields) {
        this.customFields = customFields;
        this.customValueFields.clear();
        this.parents.clear();
        container.clear();
        render();
    }
    
    /**
     * Adds a custom value field to the form according to the given custom field
     */
    private CustomValueField addCustomField(CustomField field) {
        CustomValueField valueField = new CustomValueField(field);
        LabelField label = new LabelField(field.getDisplayName(), "custom-field-label");
        container.add(label);
        container.add(valueField);
        customValueFields.add(valueField);   
        return valueField;
    }
       
    /**
     * Renders the form and resolves custom value field's parent-child dependencies
     */
    private void render() {
        if(customFields != null && customFields.length() > 0) {
            for(int i = 0; i < customFields.length(); i++) {                
                CustomField field = customFields.get(i);
                // Parents
                if(field.getParentId() == null) {
                    // Add to form
                    final CustomValueField parent = addCustomField(field);       
                    parent.addChangeHandler(new ChangeHandler() {
                        @Override
                        public void onChange(ChangeEvent event) {
                            notifyUpdate(parent);
                        }                        
                    });
                                       
                    // Add to map
                    parents.put(parent, new ArrayList<CustomValueField>());
                }
            }
            for(CustomValueField parent : parents.keySet()) {
                for(int i = 0; i < customFields.length(); i++) {
                    CustomField field = customFields.get(i);
                    // Children
                    if(field.getParentId() != null && parent.getCustomField().getId().equals(field.getParentId())) {
                        // Add to form
                        CustomValueField fieldValue = addCustomField(field);
                        // Add to map
                        parents.get(parent).add(fieldValue);
                    }
                }
            }
            updatePossibleValues();
        }
    }
    
    /**
     * Sets the parents possible values for enumerated types
     */
    private void updatePossibleValues() {
        for(CustomValueField parent : parents.keySet()) {
            if(parent.getCustomField().getType() == CustomFieldType.ENUMERATED) {
                parent.setPossibleValues(parent.getCustomField().getPossibleValues());
            }
        }
     }

    @Override
    public void notifyUpdate(Widget widget) {
        // Notify children that parent has changed
        CustomValueField parent = (CustomValueField) widget;
        List<CustomValueField> childs = parents.get(parent);
        if(childs != null && childs.size() > 0) {
            for(CustomValueField child : childs) {
                child.updateWidget(parent);
            }
        }        
    }
    
}
