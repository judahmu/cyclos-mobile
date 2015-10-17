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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * An icon button which can handle two states (down / up).
 * It can be added in top bar. 
 */
public class ToolbarButton extends Composite {
    
    public enum ButtonType { PUSH, TOGGLE }
    
    private SimplePanel  button;
    private ButtonType   type;
    private boolean      down = false;    
    
    public ToolbarButton(Image image, ButtonType type) {
        this.type = type;
       
        button = new SimplePanel();
        button.setWidget(image);
        
        switch (this.type) {
            case TOGGLE:
                addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {  
                        setDown(true);
                    }            
                });
                break;
        }
        initWidget(button);
        setStyleName("topmenu-button");                
    }
       
    /**
     * Sets down the button if it is type TOOGLE     
     */
    public void setDown(boolean down) {
        if(type == ButtonType.TOGGLE) {
            this.down = down;
            updateStyles();
        }
    }       
    
    /**
     * Adds button's click handler
     */
    public void addClickHandler(ClickHandler handler) {
        button.addDomHandler(handler, ClickEvent.getType());
    }

    /**
     * Adds or remove style when button is up / down
     */
    private void updateStyles() {
        if(down) {
            button.addStyleName("topmenu-button-down");            
        } else {
            button.removeStyleName("topmenu-button-down");
        }
    }
    
}
