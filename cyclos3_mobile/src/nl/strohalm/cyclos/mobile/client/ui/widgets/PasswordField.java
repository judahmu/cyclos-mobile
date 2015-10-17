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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A input password which can handle a placeholder and has custom style. 
 */
public class PasswordField extends Composite {

    private PasswordTextBox password;
    private SimplePanel     container;
    
    public PasswordField() {
        this(null);
    }
    
    public PasswordField(String placeholder) {
        container = new SimplePanel();
        container.setStyleName("form-input");
                
        password = new PasswordTextBox();    
        password.setStyleName("form-input-field");
        
        setPlaceHolder(placeholder);
        
        container.setWidget(password);
        
        initWidget(container);
    }
    
    /**
     * Sets password place holder     
     */
    private void setPlaceHolder(String placeholder) {
        if(placeholder != null && !placeholder.isEmpty()) {
            DOM.setElementProperty(password.getElement(), "placeholder", placeholder);
        }
    }
    
    /**
     * Returns password's value
     */
    public String getValue() {
        return password.getValue();
    }
    
    /**
     * Clear password's value
     */
    public void clear() {
        password.setValue("");
    }

}
