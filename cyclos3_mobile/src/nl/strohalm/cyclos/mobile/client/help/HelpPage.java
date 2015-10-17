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
package nl.strohalm.cyclos.mobile.client.help;

import java.util.Arrays;
import java.util.List;

import nl.strohalm.cyclos.mobile.client.CyclosMobile;
import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.model.AppState;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.PageGroup;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link Page} used to display help information.
 */
public class HelpPage extends Page {
    
    private FlowPanel container;

    @Override
    public String getHeading() {
        return messages.helpHeading();
    }
    
    @Override
    public AppState getMinimumAppState() {
        return AppState.SERVER_NOT_CONFIGURED;
    }

    @Override
    public boolean isDisplayMenu() {    
        return LoggedUser.get().isLoggedIn();
    }
    
    @Override
    public List<PageAction> getPageActions() {
        // Display back action to go login or configuration
        if(!LoggedUser.get().isLoggedIn()) {
            return Arrays.asList(getBackAction());
        }
        return null;
    }
    
    /**
     * Returns a back action depending on application state    
     */
    private PageAction getBackAction() {
        return new PageAction() {
            @Override
            public void run() {
                if(CyclosMobile.get().getAppState() == AppState.NO_LOGGED_USER) {
                    Navigation.get().go(PageAnchor.LOGIN);                    
                } else {
                    Navigation.get().go(PageAnchor.CONFIGURATION);
                }
            }
            @Override
            public String getLabel() {                
                if(CyclosMobile.get().getAppState() == AppState.NO_LOGGED_USER) {
                    return messages.goToLogin();
                     
                } 
                return messages.goToConfiguration();
            }            
        };
    }
    
    @Override
    public Widget initialize() {
        container = new FlowPanel() {
            @Override
            protected void onLoad() {                
                scrollIntoSection();
            }
        };
        container.setStyleName("help");        
        
        createSection(PageGroup.GENERAL.name(), messages.help(), messages.helpText());
        createSection(PageGroup.LOGIN.name(), messages.login(), messages.loginHelpText());
        createSection(PageGroup.CONFIGURATION.name(), messages.configuration(), messages.configurationHelpText());
        createSection(PageGroup.ACCOUNTS.name(), messages.accounts(), messages.accountsHelpText());
        createSection(PageGroup.PAYMENTS.name(), messages.payments(), messages.paymentsHelpText());
        createSection(PageGroup.CONTACTS.name(), messages.contacts(), messages.contactsHelpText());
                
        return container;
    }        
    
    /**
     * Creates a help section with the given title and text, adds an anchor to title for focus purposes      
     */
    private void createSection(String anchor, String title, String text) {
       
        Label titleLabel = new Label(title);
        titleLabel.getElement().setId(anchor);
        titleLabel.setStyleName("help-title");
        
        HTML textLabel = new HTML(text);       
        textLabel.setStyleName("help-text");
        
        container.add(titleLabel);
        container.add(textLabel);        
    }
    
    /**
     * Scrolls the page into the given section
     */
    private void scrollIntoSection() {
        Parameters params = getParameters();
        if(params != null) {
            String sectionParam = params.getString("section");
            if(StringHelper.isNotEmpty(sectionParam)) {
                try {
                    PageAnchor anchor = PageAnchor.valueOf(sectionParam);
                    Element element = DOM.getElementById(anchor.name());               
                    if(element != null) {
                        scrollIntoView(element);
                    }               
                } catch(Exception e) {
                    // No scrolling into section
                }
            }
        }
    } 
    
    /**
     * Scrolls the element into view aligning it to the top    
     */
    private final native void scrollIntoView(Element element) /*-{
         element.scrollIntoView(true);
    }-*/;

}
