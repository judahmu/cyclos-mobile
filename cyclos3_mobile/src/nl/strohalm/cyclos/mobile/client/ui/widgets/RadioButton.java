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
 * A radio button widget.
 */
public class RadioButton<T> extends Composite {
    
    private class Radio extends FocusWidget {        
        public Radio(String name) {
            super(Document.get().createRadioInputElement(name));
        }                
        public HandlerRegistration addClickHandler(ClickHandler handler) {
            return addDomHandler(handler, ClickEvent.getType());
        }        
        public boolean getValue() {
            return DOM.getElementPropertyBoolean(getElement(), "checked");
        }        
        public void setValue(boolean value) {
            DOM.setElementPropertyBoolean(getElement(), "checked", value);
        }
    }
    
    private FlowPanel  container;
    private Radio      radio;
    private InlineHTML box;
    private InlineHTML dot;
    private InlineHTML val;
    private T          option;
    
    public RadioButton(String name, String value, T option) {
        this.option = option;
        
        container = new FlowPanel();
        container.setStyleName("custom-radio");
        
        radio = new Radio(name);
        radio.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                RadioButton.this.onClick(RadioButton.this.option);
            }            
        });
                
        dot = new InlineHTML();
        dot.setStyleName("dot");
        
        box = new InlineHTML(dot.toString());
        box.setStyleName("box");
        
        val = new InlineHTML(value);
        val.setStyleName("value");
                
        container.add(radio);
        container.add(box);
        container.add(val);
        
        initWidget(container);
    } 
    
    /**
     * Returns if checkbox is checked
     */
    public boolean getValue() {
        return radio.getValue();
    }
    
    /**
     * Sets the radio value
     */
    public void setValue(boolean value, boolean fireEvent) {
        radio.setValue(value);
        if(fireEvent) {
            onClick(option);
        }
    }
    
    /**
     * Called when the radio was clicked
     */
    protected void onClick(T option) {        
    }
}
