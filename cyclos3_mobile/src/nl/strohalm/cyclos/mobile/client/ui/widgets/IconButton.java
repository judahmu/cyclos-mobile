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

import nl.strohalm.cyclos.mobile.client.utils.ComponentEventHelper;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A button with custom style which can hold an icon and a label 
 */
public class IconButton extends Composite {

    private FlowPanel container;
    
    public IconButton(String name, Image icon) {
        this(name, icon, false);
    }
    
    @Override
    public void setVisible(boolean visible) {
        container.setVisible(visible);
    }
    
    /**
     * Creates an icon button specifying button name, icon image, and if button
     * has fixed width (should be true when is in a form to not be padded out)
     */
    public IconButton(String name, Image icon, boolean fixedWidth) {
        
        SimplePanel correction = new SimplePanel();
        if(fixedWidth) {
            correction.addStyleName("form-correction");
        }
        
        container = new FlowPanel();
        container.addStyleName("icon-button");
        
        icon.addStyleName("icon-button-image");    
        container.add(icon);
        
        if(StringHelper.isNotEmpty(name)) {
            InlineLabel label = new InlineLabel(name);
            container.add(label);
        }                

        addStyleEvents();
        
        correction.setWidget(container);
        
        initWidget(correction);
    }    
    
    /**
     * Add events to change button style
     */
    private void addStyleEvents() {
        ComponentEventHelper.addPressEvents(container, "icon-button-down");
    }
    
    /**
     * Add handler to button click event
     */
    public void addClickHandler(ClickHandler handler) {
        container.addDomHandler(handler, ClickEvent.getType());
    }
}
