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
import nl.strohalm.cyclos.mobile.client.model.Fee;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.model.PaymentConfirm;
import nl.strohalm.cyclos.mobile.client.model.PaymentPreview;
import nl.strohalm.cyclos.mobile.client.model.TransferType;
import nl.strohalm.cyclos.mobile.client.services.PaymentService;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.panels.SquarePanel;
import nl.strohalm.cyclos.mobile.client.ui.widgets.FormField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.LabelField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.PasswordField;
import nl.strohalm.cyclos.mobile.client.utils.BaseAsyncCallback;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link Page} used to display a payment preview and transaction password confirmation if needed.
 */
public class PaymentPreviewPage extends Page {
    
    private PaymentService paymentService = GWT.create(PaymentService.class);

    private PaymentPreview preview;
    private SquarePanel container;
    private FormField form;
    private FlowPanel password;
    private PasswordField passwordField;
    private LabelField title;
    
    private boolean isSystem;
    private boolean requirePassword;
    
    @Override
    public String getHeading() {
        return messages.paymentConfirmationHeading();
    }

    @Override
    public Widget initialize() {
        
        isSystem = StringHelper.isEmpty(getParameters().getString(ParameterKey.MEMBER_TO_ID));
        requirePassword = LoggedUser.get().getInitialData().isRequireTransactionPassword();
        preview = (PaymentPreview) getParameters().getObject(ParameterKey.PAYMENT_PREVIEW);
        
        container = new SquarePanel();
        container.setVisible(false);
                
        form = new FormField();      
        title = new LabelField(messages.youAreAboutToPerformTheFollowingPayment());
        title.addStyleName("payment-label-field");
        
        password = new FlowPanel();
        
        container.add(title);
        container.add(form);
        container.add(password);
        
        renderPreviewData();
        
        return container;
    }       
    
    /**
     * Renders the form with the data
     */
    private void renderPreviewData() {        
       
        TransferType transferType = preview.getTransferType();            
        JsArray<Fee> fees = preview.getFees();
        JsArrayString customFieldKeys = preview.getCustomFieldKeys();
        String to = preview.getTo() != null ? preview.getTo().getName() : transferType.getTo().getName(); 
        String description = getParameters().getString(ParameterKey.DESCRIPTION);                 
        boolean requireAuth = preview.isWouldRequireAuth();
        
        String feesData = "";
        if(fees != null && fees.length() > 0) {
            for(int i = 0; i < fees.length(); i++) {             
                Fee fee = fees.get(i);
                feesData += fee.getName() + "<br>" + messages.amount() + ": " + fee.getFormattedAmount() + "<br>";
            }
        }      
                       
        Map<String, String> formData = new LinkedHashMap<String, String>();                               
        formData.put(messages.transactionType(), transferType.getName());
        formData.put(messages.to(), to);
        formData.put(messages.amount(), preview.getFormattedFinalAmount());        
        formData.put(messages.description(), description);    
        
        if(customFieldKeys != null && customFieldKeys.length() > 0) {
            for(int i = 0; i < customFieldKeys.length(); i++) {
                String displayName = customFieldKeys.get(i);
                formData.put(displayName, preview.getCustomFieldValue(displayName));
            }       
        }      
        
        formData.put(messages.appliedFees(), feesData);
        
        form.setData(formData);
        
        if(requirePassword) {
            LabelField supplyPassword = new LabelField(messages.supplyTransactionPassword());
            supplyPassword.addStyleName("transaction-password-label-field");
            passwordField = new PasswordField(messages.typeYourTransactionPasswordHere());
            password.add(supplyPassword);
            password.add(passwordField);            
        }          
        
        if(requireAuth) {
            title.setText(messages.youAreAboutToPerformTheFollowingPaymentWithAuth());
        }
        container.setVisible(true);
    }
    
    @Override
    public List<PageAction> getPageActions() {
        return Arrays.asList(getCancelAction(), getConfirmAction());
    }
    
    /**
     * Validates input data before confirm the payment
     */
    private boolean validateData() {
        if(requirePassword && StringHelper.isEmpty(passwordField.getValue())) {
            Notification.get().error(messages.transactionPasswordIsRequired());
            return false;
        }
        return true;
    }
    
    /**
     * Returns confirm payment's page action
     */
    private PageAction getConfirmAction() {
        return new PageAction() {
            @Override
            public void run() {                
                if(!validateData()) {
                    return;
                }        
                // Set the parameters to be used in confirmation
                Parameters confirmParams = new Parameters();
                confirmParams.setValues(getParameters().getValues());
                confirmParams.setObjectList(getParameters().getObjectList());                
                if(requirePassword) {
                    confirmParams.set(ParameterKey.TRANSACTION_PASSWORD, passwordField.getValue());
                }
                paymentService.doPayment(isSystem, confirmParams, new BaseAsyncCallback<PaymentConfirm>() {
                    @Override
                    public void onSuccess(PaymentConfirm result) {
                        // Go to payment details page
                        Parameters params = new Parameters();
                        params.set(ParameterKey.ID, result.getPaymentId().toString());
                        if(result.isPending()) {
                            params.set(ParameterKey.PAYMENT_PENDING, true);                            
                        } else {
                            params.set(ParameterKey.PAYMENT_DONE, true);
                        }
                        Navigation.get().go(PageAnchor.PAYMENT_DETAILS, params);
                    }                    
                });
            }
            @Override
            public String getLabel() {             
                return messages.confirmPayment();
            }            
        };
    }
    
    /**
     * Returns the cancel's action page
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
}
