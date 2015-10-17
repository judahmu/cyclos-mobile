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

import nl.strohalm.cyclos.mobile.client.Messages;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.ui.Icon;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.widgets.MenuButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * A fixed menu bar. Displays Application's menu buttons.
 */
public class MenuPanel extends Composite {

    private FlowPanel container;
    
    private MenuButton accounts;
    private MenuButton payments;
    private MenuButton contacts;
    
    public MenuPanel() {
        
        Messages messages = Messages.Accessor.get();
        
        container = new FlowPanel();
        container.setStyleName("menu-bar");
        
        accounts = new MenuButton(messages.accounts(), Icon.ACCOUNTS.image());
        accounts.addClickHandler(getButtonClickHandler(PageAnchor.ACCOUNTS));
        
        payments = new MenuButton(messages.payments(), Icon.PAYMENTS.image());
        payments.addClickHandler(getButtonClickHandler(PageAnchor.PAYMENTS));
        
        contacts = new MenuButton(messages.contacts(), Icon.CONTACTS.image());
        contacts.addClickHandler(getButtonClickHandler(PageAnchor.CONTACTS));
        
        container.add(accounts);
        container.add(payments);
        container.add(contacts);        
        
        initWidget(container);
    }
    
    /**
     * Returns menu button's click action   
     */
    private ClickHandler getButtonClickHandler(final PageAnchor pageAnchor) {
       return new ClickHandler() {           
            @Override
            public void onClick(ClickEvent event) {
                Navigation.get().go(pageAnchor);
            }
        };
    }
    
    /**
     * Selects a button according to the given PageAnchor     
     */
    public void select(PageAnchor pageAnchor) {
        if(pageAnchor != null) {
            // Reset any pressed button
            resetButtons();
            
            // Select according button
            switch (pageAnchor.getPageGroup()) {
                case ACCOUNTS:
                    accounts.setDown(true);
                    break;
                case PAYMENTS:              
                    payments.setDown(true);
                    break;
                case CONTACTS:
                    contacts.setDown(true);
                    break;          
            }
        }
    }
    
    /**
     * Resets all selected buttons
     */
    private void resetButtons() {    
        accounts.setDown(false);
        payments.setDown(false);
        contacts.setDown(false);
    }
    
}
