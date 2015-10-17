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

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.InlineHTML;

/**
 * Widget for editing a boolean value with a checkbox 
 */
public class BooleanField extends Composite {

    private class CheckBox extends FocusWidget {
        public CheckBox() {
            super(Document.get().createCheckInputElement());
        }        
        public HandlerRegistration addClickHandler(ClickHandler handler) {
            return addDomHandler(handler, ClickEvent.getType());
        }        
        public boolean getValue() {
            return DOM.getElementPropertyBoolean(getElement(), "checked");
        }        
    }
    
    private FlowPanel  container;
    private CheckBox   checkbox;
    private InlineHTML box;
    private InlineHTML tick;
    
    public BooleanField() {
        container = new FlowPanel();
        container.setStyleName("custom-checkbox");
        
        checkbox = new CheckBox();
                
        tick = new InlineHTML();
        tick.setStyleName("tick");
        
        box = new InlineHTML(tick.toString());
        box.setStyleName("box");
                
        container.add(checkbox);
        container.add(box);
        
        initWidget(container);
    } 
    
    /**
     * Returns checkbox value
     */
    public boolean getValue() {
        return checkbox.getValue();
    }
    
}
