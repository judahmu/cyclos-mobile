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
package nl.strohalm.cyclos.mobile.client.payments;

import java.util.Arrays;
import java.util.List;

import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.Notification;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.ui.Icon;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.panels.SquarePanel;
import nl.strohalm.cyclos.mobile.client.ui.widgets.IconButton;
import nl.strohalm.cyclos.mobile.client.ui.widgets.LabelField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.TextField;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link Page} used to display user's payments. 
 */
public class PaymentsPage extends Page {
        
    private TextField username;    

    @Override
    public String getHeading() {
        return messages.paymentsHeading();
    }

    @Override
    public Widget initialize() {
        SquarePanel container = new SquarePanel();
        
        boolean canMakeSystemPayments = LoggedUser.get().getInitialData().canMakeSystemPayments();
        boolean canMakeMemberPayments = LoggedUser.get().getInitialData().canMakeMemberPayments();        
        
        LabelField receiver = new LabelField(messages.makePayment());
        receiver.addStyleName("payment-label-field");
        
        IconButton selectSystem = new IconButton(messages.toSystemAccount(), Icon.SYSTEM.image(), true);
        selectSystem.addClickHandler(getSelectSystemAccountHandler());
        selectSystem.setVisible(canMakeSystemPayments);              
        
        IconButton selectContact = new IconButton(messages.toMyContact(), Icon.CONTACT_SELECT.image(), true);
        selectContact.addClickHandler(getSelectContactHandler());
        selectContact.setVisible(canMakeMemberPayments);
        
        username = new TextField(messages.orTypeUserNameHere());
        username.setVisible(canMakeMemberPayments);                
        
        container.add(receiver);
        container.add(selectSystem);
        container.add(selectContact);
        container.add(username);
        
        if(!canMakeSystemPayments && !canMakeMemberPayments) {
            container.setVisible(false);
            Notification.get().information(messages.cannotMakePayments());
        }    
        
        return container;
    }
    
    @Override
    public List<PageAction> getPageActions() {
        boolean canMakeMemberPayments = LoggedUser.get().getInitialData().canMakeMemberPayments();        
        if(canMakeMemberPayments) {
            return Arrays.asList(getNextAction());
        }
        return null;
    }
   
    /**
     * Returns next's page action
     */
    private PageAction getNextAction() {
        return new PageAction() {
            @Override
            public void run() {
                // Go to users page
                Parameters params = null;
                if(StringHelper.isNotEmpty(username.getValue())) {
                    params = new Parameters();
                    params.add(ParameterKey.KEYWORDS, username.getValue());
                }
                Navigation.get().go(PageAnchor.USER_SELECT, params);  
            }
            @Override
            public String getLabel() {
                return messages.next();
            }            
        };
    }
    
    /**
     * Returns select contact button's click handler
     */
    private ClickHandler getSelectContactHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Navigation.get().go(PageAnchor.CONTACT_SELECT);
            }            
        };
    }
    
    /**
     * Returns select system account button's click handler
     */
    private ClickHandler getSelectSystemAccountHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Parameters params = new Parameters();
                params.add(ParameterKey.IS_SYSTEM_ACCOUNT, true);   
                Navigation.get().goNoHistory(PageAnchor.MAKE_PAYMENT, params);    
            }                
        };
    }
      
}
