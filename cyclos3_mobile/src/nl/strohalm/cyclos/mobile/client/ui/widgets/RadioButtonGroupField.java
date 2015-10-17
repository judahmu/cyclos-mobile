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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.strohalm.cyclos.mobile.client.Messages;
import nl.strohalm.cyclos.mobile.client.model.Entity;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * A field consisting in a group of radio buttons
 */
public abstract class RadioButtonGroupField<T extends Entity> extends Composite {

    private String groupName;
    private FlowPanel container;
    private JsArray<T> options;
    private Map<RadioButton<T>, T> map;
    
    public RadioButtonGroupField(String groupName) {
        this.groupName = groupName;
        this.map = new LinkedHashMap<RadioButton<T>, T>();
        
        container = new FlowPanel();
        container.setStyleName("radio-group");

        initWidget(container);
    }
    
    /**
     * Sets the group buttons. If buttons were 
     * already set, this action will replace them.
     */
    public void setOptions(JsArray<T> options) {
        this.options = options;
        container.clear();
        map.clear();
        createRadios();
    }
    
    /**
     * Creates the radio buttons with the given options
     */
    private void createRadios() {
        if(options != null && options.length() > 0) {
            for(int i = 0; i < options.length(); i++) {
                T option = options.get(i);
                RadioButton<T> radio = new RadioButton<T>(groupName, getRadioValue(option), option) {
                    protected void onClick(T option) {
                        onOptionSelected(option);
                    };
                };
                radio.addStyleName("radio-group-button");
                container.add(radio);
                map.put(radio, option);
            }
        } else {
            container.add(new LabelField( Messages.Accessor.get().notAvailableOptions()));
        }
    }
    
    /**
     * Returns the selected value
     */
    public T getValue() {
        for(RadioButton<T> radio : map.keySet()) {
            if(radio.getValue()) {
                return map.get(radio);
            }
        }
        return null;
    }
    
    /**
     * Called when an option has been selected
     */
    protected void onOptionSelected(T option) {       
    }
    
    /**
     * Returns the radio button label to be displayed
     */
    protected abstract String getRadioValue(T option);
    
    /**
     * Returns if the given option is the default value
     */
    protected boolean isDefault(T option) {
        return false;
    }
    
    /**
     * Selects the first element in the store
     */
    public void selectFirst() {
        if(map != null && map.keySet().size() > 1) {                        
            RadioButton<T> radio = map.keySet().iterator().next();
            radio.setValue(true, true);
        }
    }
    
    /**
     * Selects the default value if available. 
     * Otherwise the first value will be selected
     */
    public void selectDefault() {       
        if(map != null && map.keySet().size() > 1) {
            boolean selected = false;
            Iterator<RadioButton<T>> iterator = map.keySet().iterator();            
            while(iterator.hasNext()) {
                RadioButton<T> radio = iterator.next();
                if(isDefault(map.get(radio))) {
                    selected = true;
                    radio.setValue(true, true);
                    break;
                }
            }
            if(!selected) {
                selectFirst();
            }
        }        
    }
}
