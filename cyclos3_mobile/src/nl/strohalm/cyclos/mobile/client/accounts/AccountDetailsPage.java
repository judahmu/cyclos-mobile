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
package nl.strohalm.cyclos.mobile.client.accounts;

import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.Notification;
import nl.strohalm.cyclos.mobile.client.Notification.NotificationLayout;
import nl.strohalm.cyclos.mobile.client.model.Account;
import nl.strohalm.cyclos.mobile.client.model.AccountStatus;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.model.Transfer;
import nl.strohalm.cyclos.mobile.client.services.AccountService;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.widgets.AccountRow;
import nl.strohalm.cyclos.mobile.client.ui.widgets.DataList;
import nl.strohalm.cyclos.mobile.client.utils.BaseAsyncCallback;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;
import nl.strohalm.cyclos.mobile.client.utils.ResultPage;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link Page} used to display account details.
 */
public class AccountDetailsPage extends Page {

    private FlowPanel container;
    private AccountRow accountInformation;
    private DataList<Transfer> dataList;
    private Long accountId;
    private String accountName;
    
    private AccountService accountService = GWT.create(AccountService.class);
    
    @Override
    public String getHeading() {
        return messages.accountDetailsHeading();
    }

    @Override
    public boolean hasCustomScroll() {     
        return true;
    }
    
    @Override
    public Widget initialize() {
        
        // List pages uses fixed notification layout
        Notification.get().setLayout(NotificationLayout.FIXED);
        
        container = new FlowPanel();            
        
        accountId = getParameters().getRequiredLong(ParameterKey.ID);
        
        createAccountInformation();
        createDataList();                       
        
        return container;
    }
    
    /**
     * Creates account information bar
     */
    private void createAccountInformation() {

        // Retrieve account name from initial accounts
        accountName = getInitialAccountName(accountId);               
      
        accountInformation = new AccountRow();          
        accountInformation.addStyleName("account-information-row");
        accountInformation.setRightStyle("account-information-row-right-column");
        accountInformation.setHeading(accountName, "account-information-heading");
        
        // Fetch account status data
        accountService.getAccountStatus(accountId, new BaseAsyncCallback<AccountStatus>() {
            @Override
            public void onSuccess(AccountStatus status) {
                accountInformation.setSub(messages.lowerCreditLimit(status.getFormattedCreditLimit()), "account-information-sub");
                accountInformation.setSub(messages.availableBalance(status.getFormattedAvailableBalance()), "account-information-sub");
                accountInformation.setValue(status.getFormattedBalance(), "account-information-value");
            }                
        });                           
    }
    
    /**
     * Retrieves account name from initial accounts by the given id
     */
    private String getInitialAccountName(Long accountId) {
        JsArray<Account> accounts = LoggedUser.get().getInitialData().getAccounts();
        for(int i = 0; i < accounts.length(); i++) {
            Account account = accounts.get(i);
            if(account.getId().longValue() == accountId) {
                return account.getType().getName();
            }
        }
        return "";
    }
    
    /**
     * Creates data list which fetches and render transfers
     */
    private void createDataList() {                
        
        dataList = new DataList<Transfer>() {
            @Override
            protected Widget onRender(Context context, Transfer data) {

                // Value can be null
                if (data == null) {
                  return null;
                }
                
                // If it is a user payment return user name otherwise return account name
                String name = data.getBasicMember() == null ? data.getSystemAccountName() : data.getBasicMember().getName();
                
                // Create row widget
                AccountRow row = new AccountRow();
                row.setHeading(name);
                row.setSub(data.getFormattedProcessDate());
                
                boolean positiveAmount = data.getAmount() > 0d;
                
                // If amount is positive add a + sign
                String amount = positiveAmount ? "+ " + data.getFormattedAmount() : StringHelper.insertCharsAt(1, " ", data.getFormattedAmount());
                                
                row.setValue(amount, positiveAmount);
                
                return row;
            }

            @Override
            protected void onSearchData(int page, int length, AsyncCallback<ResultPage<Transfer>> callback) {
                // Fetch transfers
                Parameters parameters = new Parameters();
                parameters.set(ParameterKey.CURRENT_PAGE, String.valueOf(page));
                parameters.set(ParameterKey.PAGE_SIZE, String.valueOf(length));
                accountService.searchAccountHistory(accountId, parameters, callback);   
            }         
         
            @Override
            protected void onRowSelected(Transfer value) {                 
                // Go to transfer details
                navigateToTransferDetails(value);
            }
            
            @Override
            protected Widget addHeader() {                
                return accountInformation;
            }
        };
        container.add(dataList);
    }      
    
    /**
     * Navigates to transfer details sending according parameters     
     */
    private void navigateToTransferDetails(Transfer transfer) {
        Parameters params = new Parameters();
        params.add(ParameterKey.ID, transfer.getId());
        Navigation.get().go(PageAnchor.PAYMENT_DETAILS, params);
    }

}
