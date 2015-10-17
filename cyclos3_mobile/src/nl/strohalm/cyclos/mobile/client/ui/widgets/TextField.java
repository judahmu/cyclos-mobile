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

import nl.strohalm.cyclos.mobile.client.utils.UIHelper;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * A text field with custom style and displays a label instead of input when is read only. 
 */
public class TextField extends Composite {
    
    private TextBox          textBox;
    private SimplePanel      container;
    private LabelField       label;
    
    public TextField() {
        this(null);
    }
    
    @Override
    public void setVisible(boolean visible) {
        container.setVisible(visible);
    }
    
    public TextField(String placeholder) {
        container = new SimplePanel();
        container.setStyleName("form-input");
                
        textBox = new TextBox();    
        textBox.setStyleName("form-input-field");
        
        label = new LabelField();
        label.setStyleName("form-input-field-readonly");
        
        UIHelper.setPlaceHolder(placeholder, textBox.getElement());
        
        container.setWidget(textBox);
        
        initWidget(container);
    }       
    
    /**
     * Sets the field as read only display a label instead of the text box
     */
    public void setReadOnly(boolean readOnly) {
        textBox.setReadOnly(readOnly);
        if(readOnly) {
            if(getValue() != null && !getValue().isEmpty()) {
                label.setText(getValue());
            }
            container.setWidget(label);            
        } else {
            container.setWidget(textBox);
        }
    }
    
    /**
     * Returns inner {@link #textBox}
     */
    public TextBox getTextBox() {
        return textBox;
    }
    
    /**
     * Clears text box value
     */
    public void clear() {
        textBox.setValue("");
    }
    
    /**
     * Returns text box value
     */
    public String getValue() {
        return textBox.getValue();
    }
    
    /**
     * Sets text box value
     */
    public void setValue(String value) {
       textBox.setValue(value);
    }
    
}
