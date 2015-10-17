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
package nl.strohalm.cyclos.mobile.client.accounts;

import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.Notification;
import nl.strohalm.cyclos.mobile.client.Notification.NotificationLayout;
import nl.strohalm.cyclos.mobile.client.model.Account;
import nl.strohalm.cyclos.mobile.client.model.AccountData;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.services.AccountService;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.widgets.AccountRow;
import nl.strohalm.cyclos.mobile.client.ui.widgets.SimpleDataList;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link Page} used to display accounts.
 * 
 * @author luis
 */
public class AccountsPage extends Page {
    
    private AccountService accountService = GWT.create(AccountService.class);
    private JsArray<Account> accounts;
    
    @Override
    public String getHeading() {
        accounts = LoggedUser.get().getInitialData().getAccounts();
        
        // If exists only an account display details title
        if(accounts.length() == 1) {
            return messages.accountDetailsHeading();
        } 
        return messages.accountsHeading();
    }

    @Override
    public boolean hasCustomScroll() {
        return true;
    }
    
    @Override
    public Widget initialize() {                           
        
        if(accounts.length() == 1) {
            // If exists only an account display details page
            final Account account = accounts.get(0);
            
            Parameters params = new Parameters();
            params.add(ParameterKey.ID, account.getId());   
            
            Page page = new AccountDetailsPage();
            page.setParameters(params);

            return page.initialize();
        }
        
        // List pages uses fixed notification layout
        Notification.get().setLayout(NotificationLayout.FIXED);
        
        // If there are multiple accounts, display it
        SimpleDataList<AccountData> dataList = new SimpleDataList<AccountData>() {
       
            @Override
            protected Widget onRender(Context context, AccountData data) {                
                
                // Value can be null
                if (data == null) {
                  return null;
                }
                
                // Create row widget
                AccountRow row = new AccountRow();
                row.setHeading(data.getAccount().getType().getName());
                row.setSub(LoggedUser.get().getInitialData().getProfile().getName());
                row.setValue(data.getStatus().getFormattedBalance(), data.getStatus().getBalance() > 0d);
                
                return row;
            }

            @Override
            protected void onSearchData(AsyncCallback<JsArray<AccountData>> callback) {
                // Fetch account data
                accountService.getAccountsData(callback);
            }     
            
            @Override
            protected void onRowSelected(AccountData value) { 
                // Go to account details
                navigateToAccountDetails(value.getAccount());
            }
        };

        return dataList;
    }
    
    /**
     * Navigates to account details sending according parameters     
     */
    private void navigateToAccountDetails(Account account) {
        Parameters params = new Parameters();
        params.add(ParameterKey.ID, account.getId());   
        Navigation.get().go(PageAnchor.ACCOUNT_DETAILS, params);      
    }

}
