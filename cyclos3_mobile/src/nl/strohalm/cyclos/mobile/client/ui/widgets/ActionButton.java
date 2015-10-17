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
import nl.strohalm.cyclos.mobile.client.utils.PageAction;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A button which displays two states on click (up / down) and executes a PageAction. 
 * It can be added to bottom bar actions.
 */
public class ActionButton extends Composite {
    
    private FlowPanel container;
    private SimplePanel clickSurface;
    
    private PageAction action;
    
    public ActionButton(PageAction action) {
        this.action = action;
        
        container = new FlowPanel();
        container.setStyleName("action-button");
        
        container.getElement().setInnerHTML(this.action.getLabel());                       
        
        clickSurface = new SimplePanel();
        clickSurface.setStyleName("action-button-click-surface");
        
        container.add(clickSurface);
        
        addClick();
        addStyleEvents();
        
        initWidget(container);
    }
    
    /**
     * On click execute action
     */
    private void addClick() {
        clickSurface.addDomHandler(new ClickHandler() {           
            @Override
            public void onClick(ClickEvent event) {
               ActionButton.this.action.run();
            }
        }, ClickEvent.getType());
    }
    
    /**
     * Add events to change button style
     */
    private void addStyleEvents() {
        ComponentEventHelper.addPressEvents(container, "action-button-down");
    }

}
