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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Displays an icon and a label in the same line.
 */
public class IconLabel extends Composite {

    private FlowPanel   container;
    
    private SimplePanel iconContainer;
    private SimplePanel textContainer;
    
    public IconLabel(String text, Image icon) {
        container = new FlowPanel();
        container.setStyleName("icon-label");
        
        iconContainer = new SimplePanel();
        iconContainer.setStyleName("icon-label-image");
        iconContainer.setWidget(icon);        
        
        textContainer = new SimplePanel();
        textContainer.setStyleName("icon-label-text");
        textContainer.setWidget(new LabelField(text));
        
        container.add(iconContainer);
        container.add(textContainer);
        
        initWidget(container);
    }
    
    /**
     * Adds an icon style name    
     */
    public void addIconStyleName(String style) {
        iconContainer.addStyleName(style);
    }
    
    /**
     * Adds a text style name     
     */
    public void addTextStyleName(String style) {
        textContainer.addStyleName(style);
    }
}
