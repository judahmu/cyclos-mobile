/*
   This file is part of Cyclos.

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

import nl.strohalm.cyclos.mobile.client.CyclosMobile;
import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.ui.Icon;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.widgets.LogoutDialog;
import nl.strohalm.cyclos.mobile.client.ui.widgets.Spinner;
import nl.strohalm.cyclos.mobile.client.ui.widgets.ToolbarButton;
import nl.strohalm.cyclos.mobile.client.ui.widgets.ToolbarButton.ButtonType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Holds the current page title, the spinner, the logout button and the help button.
 * 
 * @author luis
 */
public class TopPanel extends Composite {

    private Label         heading;
    private Spinner       spinner;
    private ToolbarButton logout;
    private ToolbarButton help;
    private ToolbarButton configuration;
    private FlowPanel     container;
    private PageAnchor    fromPage;
    
    public TopPanel() {
        container = new FlowPanel();
        container.setStyleName("top-bar");
        
        heading = new Label();
        heading.setStylePrimaryName("topmenu-heading");
        container.add(heading);
        
        spinner = new Spinner(Icon.LOAD.image());
        spinner.addStyleName("clickable");
        spinner.addDomHandler(getSpinnerClickHandler(), ClickEvent.getType());
        container.add(spinner);   
        
        configuration = new ToolbarButton(Icon.SETTINGS.image(), ButtonType.TOGGLE);
        configuration.addClickHandler(getConfigurationClickHandler());              
        container.add(configuration);        
        
        help = new ToolbarButton(Icon.HELP.image(), ButtonType.TOGGLE);       
        help.addClickHandler(getHelpClickHandler());
        container.add(help);

        logout = new ToolbarButton(Icon.LOGOUT.image(), ButtonType.PUSH);
        logout.setVisible(false);
        logout.addClickHandler(getLogoutClickHandler());
        container.add(logout);                            
        
        initWidget(container);
    }    
    
    /**
     * Returns login button's click handler  
     */
    private ClickHandler getLogoutClickHandler() {
       return new ClickHandler() {           
            @Override
            public void onClick(ClickEvent event) {
                // Display logout confirmation prompt
                LogoutDialog logoutDialog = new LogoutDialog();
                logoutDialog.display();
            }
        };
    }
    
    /**
     * Returns spinner's click handler
     */
    private ClickHandler getSpinnerClickHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Refresh the actual page
                CyclosMobile.get().getMainLayout().show(null);
            }
        };        
    }
    
    /**
     * Returns help button's click handler
     */
    private ClickHandler getHelpClickHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // If it is going from the same page, prevent event
                if(fromPage != null && fromPage == PageAnchor.HELP) {                    
                    return;
                }
                
                // Sets Help Page's scroll into
                Parameters params = null;
                if(fromPage != null) {
                    params = new Parameters();
                    params.set("section", fromPage.getPageGroup().name());
                }     
                // Go to Help Page
                Navigation.get().go(PageAnchor.HELP, params);
            }
        };
    }
    
    /**
     * Returns configuration button's click handler
     */
    private ClickHandler getConfigurationClickHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // If it is going from the same page, prevent event
                if(fromPage != null && fromPage == PageAnchor.CONFIGURATION) {                    
                    return;
                }
                
                // Go to Configuration Page
                Navigation.get().go(PageAnchor.CONFIGURATION);
            }
        };
    }
   
    
    /**
     * Resets all selected buttons
     */
    private void resetButtons() {    
        help.setDown(false);
        configuration.setDown(false);
    }
    
    /**
     * Sets the Help Page's anchor to scroll into   
     */
    public void setFromPage(PageAnchor fromPage) {
        this.fromPage = fromPage;
    }    
    
    /**
     * Selects a button according to the given PageAnchor     
     */
    public void select(PageAnchor pageAnchor) {
        // Reset any pressed button
        resetButtons();
        
        // Select according button
        switch (pageAnchor) {
            case HELP:
                help.setDown(true);         
                break;
            case CONFIGURATION:
                configuration.setDown(true);
        }
    }
    
    /**
     * Displays buttons if they are accessible, otherwise hides them
     */
    public void displayButtons() {
        boolean loggedIn = LoggedUser.get().isLoggedIn();
        logout.setVisible(loggedIn);  
        configuration.setVisible(!loggedIn);
    }
   
    /**
     * Sets the page heading
     */
    public void setHeading(String heading) {
        this.heading.setText(heading);
    }
    
    /**
     * Rotates load icon
     */
    public void startLoading() {
        spinner.startSpinner();
    }
    
    /**
     * Stop load icon rotation
     */
    public void stopLoading() {
        spinner.stopSpinner();
    }
    
    /**
     * Sets button visible
     */
    public void setButtonsVisible(boolean visible) {
        logout.setVisible(visible);
    }

}
