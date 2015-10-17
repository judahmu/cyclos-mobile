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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.Notification;
import nl.strohalm.cyclos.mobile.client.model.AccountStatus;
import nl.strohalm.cyclos.mobile.client.model.InitialData;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.model.PaymentData;
import nl.strohalm.cyclos.mobile.client.model.PaymentPreview;
import nl.strohalm.cyclos.mobile.client.model.TransferTypeDetailed;
import nl.strohalm.cyclos.mobile.client.services.PaymentService;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.panels.SquarePanel;
import nl.strohalm.cyclos.mobile.client.ui.widgets.CustomFieldForm;
import nl.strohalm.cyclos.mobile.client.ui.widgets.CustomValueField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.FormField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.NumberField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.SelectionField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.TextAreaField;
import nl.strohalm.cyclos.mobile.client.utils.BaseAsyncCallback;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link Page} used to make a payment. 
 */
public class MakePaymentPage extends Page {

    private PaymentService paymentService = GWT.create(PaymentService.class);
    
    private InitialData data; 
    private FormField fields;
    private CustomFieldForm customFields;
    private SquarePanel container;
    private PaymentData paymentData;
    private NumberField amount;
    private TextAreaField description;
    private SelectionField<TransferTypeDetailed> transferTypes;
    private boolean isSystem;
            
    @Override
    public String getHeading() {
        return messages.makePaymentHeading();
    }

    @Override
    public Widget initialize() {
       
        container = new SquarePanel();  
        container.setVisible(false);
        
        data = LoggedUser.get().getInitialData();
        
        fields = new FormField();               

        transferTypes = new SelectionField<TransferTypeDetailed>() {
            @Override
            protected String getDisplayName(TransferTypeDetailed item) {
                return item.getName();
            }
            @Override
            protected void onItemSelected(TransferTypeDetailed item) {
                renderData(item);
            }
        };      
        
        amount = new NumberField(data.getDecimalCount(), data.getDecimalSeparator(), messages.amount());  
        amount.setOnlyPositive(true);
        
        description = new TextAreaField(messages.description());               
        
        customFields = new CustomFieldForm();
        
        container.add(fields);
        container.add(transferTypes);
        container.add(amount);
        container.add(description);   
        container.add(customFields);
        
        getPaymentData();
        
        return container;
    }
    
    /**
     * Fetches payment data and displays the page
     */
    private void getPaymentData() {
             
        Parameters params = new Parameters();                 
        Boolean isSystem = getParameters().getBoolean(ParameterKey.IS_SYSTEM_ACCOUNT);
              
        if(isSystem != null && isSystem) {
            this.isSystem = true;
            params.set(ParameterKey.DESTINATION, "SYSTEM");
        } else {           
            this.isSystem = false;
            Long memberId = getParameters().getRequiredLong(ParameterKey.ID);     
            params.set(ParameterKey.DESTINATION, "MEMBER");
            params.set(ParameterKey.TO_MEMBER_ID, memberId);
        }
        
        // Fetch payment data
        paymentService.getPaymentData(params, new BaseAsyncCallback<PaymentData>() {
            @Override
            public void onSuccess(PaymentData result) {
                paymentData = result;                
                
                if(paymentData != null && paymentData.getTransferTypes() != null && paymentData.getTransferTypes().length() > 0) {            
                    
                    // Populate selection options and preselect the first item
                    transferTypes.setItems(paymentData.getTransferTypes());    
                    transferTypes.selectFirst();
                    
                    // If only exists a transfer type, hide selection field  
                    if(paymentData.getTransferTypes().length() == 1) {
                        transferTypes.setVisible(false);
                    }
                    
                    // Display data for the first transfer type retrieved
                    renderData(paymentData.getTransferTypes().get(0));
                    
                    // Display loaded data
                    container.setVisible(true);
                }       
            }                               
        });                              
    }
    
    @Override
    public List<PageAction> getPageActions() {
        return Arrays.asList(getCancelAction(), getMakePaymentAction());
    }
    
    /**
     * Renders page data according to the given transfer type
     */
    private void renderData(TransferTypeDetailed item) {
        Map<String, String> formData = new LinkedHashMap<String, String>();
              
        AccountStatus status = paymentData.get(item.getFrom().getId());
        
        // Display account from and balance
        String html = item.getFrom().getName() + "<br><i>" + 
                      messages.balance() + ": " +
                      status.getFormattedBalance() + "</i>";
        
        formData.put(messages.from(), html);
        
        // If it is system show account name, otherwise display member name
        if(isSystem) {
            formData.put(messages.to(), item.getTo().getName());
        } else {
            formData.put(messages.to(), paymentData.getMemberTo().getName());
        }  
        
        fields.setData(formData);
        
        // Update amount place holder with currency name
        amount.setPlaceHolder(messages.amountIn(item.getTo().getCurrency().getName()));
        
        // Add custom fields
        customFields.setCustomFields(item.getCustomFields());     
    }

    /**
     * Returns make payment's action
     */
    private PageAction getMakePaymentAction() {
        return new PageAction() {
            @Override
            public void run() {  
                if(validatePayment()) {
                    setPageParameters();                   
                }
            }
            @Override
            public String getLabel() {             
                return messages.makePayment();
            }          
        };
    }
    
    /**
     * Sets the parameters to be used in payment preview and confirmation page
     */
    private void setPageParameters() {
        Parameters params = new Parameters();                    
        params.set(ParameterKey.TRANSFER_TYPE_ID, transferTypes.getSelectedItem().getId());
        params.set(ParameterKey.AMOUNT, amount.getValue());
        params.set(ParameterKey.DESCRIPTION, description.getValue());
        if(!isSystem) {
            params.set(ParameterKey.MEMBER_TO_ID, paymentData.getMemberTo().getId());
        }
        JSONArray values = new JSONArray();
        int i = 0;
        for(CustomValueField customValueField : customFields.getCustomValueFields()) {                        
            values.set(i, customValueField.getValue());
            i++;
        }
        params.setObjectList(ParameterKey.CUSTOM_VALUES, values);        
        // Get data and go to payment preview page
        getPaymentPreviewData(params);
    }
    
    /**
     * Returns the payment preview data
     */
    private void getPaymentPreviewData(final Parameters params) {       
        paymentService.getPaymentPreview(isSystem, params, new BaseAsyncCallback<PaymentPreview>() {
            @Override
            public void onSuccess(PaymentPreview result) {
                PaymentPreview preview = result; 
                params.setObject(ParameterKey.PAYMENT_PREVIEW, preview);
                Navigation.get().goNoHistory(PageAnchor.PAYMENT_PREVIEW, params);
            }          
        });
    }
    
    /**
     * Returns cancel's page action
     */
    private PageAction getCancelAction() {
        return new PageAction() {
            @Override
            public void run() {
                Navigation.get().go(PageAnchor.PAYMENTS);
            }
            @Override
            public String getLabel() {
                return messages.cancel();
            }            
        };
    }
    
    /**
     * Client side validation before make a payment
     */
    private boolean validatePayment() {
        if(StringHelper.isEmpty(amount.getValue())) {
            Notification.get().error(messages.amountIsRequired());
            return false;
        }       
        return true;               
    }
}
