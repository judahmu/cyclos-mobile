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

import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;

/**
 * A Dialog box which prompts logout confirmation and handles yes / no actions
 */
public class LogoutDialog extends PromptDialog {
    
    public LogoutDialog() {
        super(messages.logoutConfirmation(), messages.logoutConfirmationText());                         
    }

    @Override
    protected void onYesActionPressed() {
        // Destroy session
        LoggedUser.get().destroy();
        
        // Go to Login Page   
        Navigation.get().go(PageAnchor.LOGIN);      
    }
    
    @Override
    protected void onNoActionPressed() {        
        // Go to initial page
        Navigation.get().loadInitialPage();
    }
    
}
