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
package nl.strohalm.cyclos.mobile.client.ui.panels;

import java.util.List;

import nl.strohalm.cyclos.mobile.client.ui.widgets.ActionButton;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

/**
 * A fixed bottom bar. It can display a list of actions.
 */
public class BottomPanel extends Composite {

    private Grid container;
    
    public BottomPanel() {
        container = new Grid(1,0);
        container.setStyleName("bottom-bar");
        initWidget(container);
    }
    
    /**
     * Shows the bar
     */
    public void show() {
        container.setVisible(true);
    }
    
    /**
     * Hides the bar
     */
    public void hide() {
        container.setVisible(false);
    }
    
    /**
     * Clear current action buttons
     */
    public void clearButtons() {
        container.clear();
    }
    
    /**
     * Set actions to the bottom bar creating an ActionButton per given PageAction and
     * adjusting the size of each button to fit proportionally
     */
    public void setActionButtons(List<PageAction> pageActions) {
        // Clear current actions
        container.clear();
        
        // Set new actions
        if(pageActions != null && pageActions.size() > 0) {                      
           switch(pageActions.size()) {
               case 1:
                   container.resizeColumns(3);  
                   container.getColumnFormatter().setWidth(0, "25%");
                   container.getColumnFormatter().setWidth(1, "50%");
                   container.getColumnFormatter().setWidth(2, "25%");                                                  
                   container.setWidget(0, 1, createAction(pageActions.get(0)));                   
                   break;
               case 2:
                   container.resizeColumns(2);
                   container.getColumnFormatter().setWidth(0, "50%");
                   container.getColumnFormatter().setWidth(1, "50%");                     
                   container.setWidget(0, 0, createAction(pageActions.get(0)));                   
                   container.setWidget(0, 1, createAction(pageActions.get(1)));                   
                   break;                             
            }                                                    
        }
    }
    
    /**
     * Creates a custom bottom panel action button
     */
    private ActionButton createAction(PageAction action) {
        ActionButton button = new ActionButton(action);
        button.addStyleName("bottom-bar-button");     
        return button;
    }
 
}
