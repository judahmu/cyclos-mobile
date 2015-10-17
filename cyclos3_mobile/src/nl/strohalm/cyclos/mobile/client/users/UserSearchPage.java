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
package nl.strohalm.cyclos.mobile.client.users;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.Notification;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.ui.Icon;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.panels.SquarePanel;
import nl.strohalm.cyclos.mobile.client.ui.widgets.IconLabel;
import nl.strohalm.cyclos.mobile.client.ui.widgets.TextField;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

/**
 * A {@link Page} used to display user search.
 */
public class UserSearchPage extends Page {
    
    private TextField username;

    @Override
    public String getHeading() {
        return messages.searchUserHeading();
    }

    @Override
    public Widget initialize() {
        
        SquarePanel container = new SquarePanel();
        container.addStyleName("users");
        
        IconLabel searchMessage = new IconLabel(messages.searchUser(), Icon.USER_SEARCH.image());
        container.add(searchMessage);
        
        username = new TextField(messages.typeUserOrLoginNameHere());  
        container.add(username);
        
        return container;
    }
    
    @Override
    public List<PageAction> getPageActions() {
        return Arrays.asList(getSearchAction());
    }

    /**
     * Returns search action
     */
    private PageAction getSearchAction() {
        return new PageAction() {
            @Override
            public void run() {
                // Go to users page
                if(StringHelper.isNotEmpty(username.getValue())) {
                    Parameters params = new Parameters();
                    params.add(ParameterKey.KEYWORDS, username.getValue());
                    Navigation.get().go(PageAnchor.USERS, params);  
                } else { 
                    // Validate value entered
                    Notification.get().error(messages.userOrLoginNameIsRequired());
                }
            }
            @Override
            public String getLabel() {                
                return messages.search();
            }            
        };
    }
    
}
