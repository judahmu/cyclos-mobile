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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A menu button which handles two states (up / down).
 * It can be added to menu bar. 
 */
public class MenuButton extends Composite {

    private FlowPanel   container;
    
    private SimplePanel iconContainer;
    private SimplePanel textContainer;
    
    private boolean     down = false;
    
    public MenuButton(String text, Image icon) {
        container = new FlowPanel();
        container.setStyleName("menu-button");
        
        iconContainer = new SimplePanel();
        iconContainer.setStyleName("menu-button-image");
        iconContainer.setWidget(icon);        
        
        textContainer = new SimplePanel();
        textContainer.setStyleName("menu-button-text");
        textContainer.setWidget(new LabelField(text));
        
        container.add(iconContainer);
        container.add(textContainer);
        
        addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setDown(!down);
            }            
        });
        
        initWidget(container);
    }
    
    /**
     * Adds or remove style when button is up / down
     */
    private void updateStyles() {
        if(down) {
            container.addStyleName("menu-button-down");            
        } else {
            container.removeStyleName("menu-button-down");
        }
    }
    
    /**
     * Sets button down and updates the style     
     */
    public void setDown(boolean down) {
        this.down = down;
        updateStyles();
    }        
    
    /**
     * Adds button click handler     
     */
    public void addClickHandler(ClickHandler handler) {        
        container.addDomHandler(handler, ClickEvent.getType()); 
    }
    
}
