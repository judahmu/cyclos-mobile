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
package nl.strohalm.cyclos.mobile.client.contacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.mobile.client.CyclosMobile;
import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.Notification;
import nl.strohalm.cyclos.mobile.client.model.Contact;
import nl.strohalm.cyclos.mobile.client.model.CustomFieldValue;
import nl.strohalm.cyclos.mobile.client.model.Member;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.services.ContactService;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.ui.panels.SquarePanel;
import nl.strohalm.cyclos.mobile.client.ui.widgets.EmailField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.FormField;
import nl.strohalm.cyclos.mobile.client.ui.widgets.PromptDialog;
import nl.strohalm.cyclos.mobile.client.utils.BaseAsyncCallback;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link Page} used to display contact details.
 */
public class ContactDetailsPage extends Page {
    
    private ContactService contactService = GWT.create(ContactService.class);
    
    private Contact contact;
    private FormField form;        
    private List<PageAction> actions;    
    private Long memberId;
    private SquarePanel container;

    @Override
    public String getHeading() {
        return messages.contactDetailsHeading();
    }

    @Override
    public Widget initialize() {        
        
        container = new SquarePanel(true);
        container.setVisible(false);
                        
        // Create form and sets labels
        form = new FormField(); 
        
        memberId = getParameters().getRequiredLong(ParameterKey.ID);
        Boolean save = getParameters().getBoolean(ParameterKey.SAVE); 
        
        // If save, show success notification
        if(save != null && save) { 
            Notification.get().success(messages.contactAdded());
        }
        
        // Actions will be rendered asynchronously when a member contact is retrieved
        actions = new ArrayList<PageAction>();
        actions.add(getRemoveContactAction());
        if(LoggedUser.get().getInitialData().canMakeMemberPayments()) {
            actions.add(getMakePaymentAction());
        }
        
        // Display contact information
        getContact(memberId);
        
        container.add(form);
        
        return container;
    }
       
    /**
     * Retrieves contact data and renders the form
     */
    private void getContact(Long memberId) {
        contactService.getContactById(memberId, new BaseAsyncCallback<Contact>() {
            @Override
            public void onSuccess(Contact result) {
                contact = result;
                if(contact != null) {
                    renderContactData();
                }
            }            
        });
    }
    
    /**
     * Renders form data
     */
    private void renderContactData() {
        
        Member member = contact.getMember();
        JsArray<CustomFieldValue> customFieldValues = null;
        
        Map<String, String> formData = new LinkedHashMap<String, String>();
        
        // Add member properties
        if(member != null) {
            formData.put(messages.username(), member.getUsername());
            formData.put(messages.name(), member.getName());
            formData.put(messages.email(), new EmailField(member.getEmail()).getHTML());
            customFieldValues = member.getCustomValues();
           
            // Add actions asynchronously
            CyclosMobile.get().getMainLayout().addActions(actions, true);
        }
            
        // Add custom fields
        if(customFieldValues != null && customFieldValues.length() > 0) {
            for(int i = 0; i < customFieldValues.length(); i++) {
                CustomFieldValue value = customFieldValues.get(i);
                formData.put(value.getDisplayName(), value.getValue());  
            }         
        }   
        form.setData(formData);
        
        // Display loaded data
        container.setVisible(true);
    }
        
    /**
     * Returns make payment page action
     */
    private PageAction getMakePaymentAction() {
        return new PageAction() {
            @Override
            public void run() {
                Parameters params = new Parameters();
                params.set(ParameterKey.ID, memberId);
                Navigation.get().go(PageAnchor.MAKE_PAYMENT, params);
            }
            @Override
            public String getLabel() {                
                return messages.makePayment();
            }           
        };
    }
    
    /**
     * Returns remove contact page action    
     */
    private PageAction getRemoveContactAction() {
        return new PageAction() {
            @Override
            public void run() {
                // Prompts user before remove contact
                PromptDialog dialog = new PromptDialog(messages.notice(), messages.deleteContact(contact.getMember().getName())) {
                    @Override
                    protected void onYesActionPressed() {
                        contactService.removeContact(memberId, new BaseAsyncCallback<Void>() {
                            @Override
                            public void onSuccess(Void result) {
                                Notification.get().success(messages.contactRemoved());
                                Navigation.get().go(PageAnchor.CONTACTS);
                            }                    
                        });
                    }
                };
                dialog.display();
            }
            @Override
            public String getLabel() {
                return messages.removeContact();
            }
        };  
    }
    
    /**
     * Returns add contact page action
     */
    private PageAction getAddToContactsAction() {
        return new PageAction() {
            @Override
            public void run() { 
                contactService.saveContact(memberId, new BaseAsyncCallback<Contact>() {
                    @Override
                    public void onSuccess(Contact result) {                        
                        Notification.get().success(messages.contactAdded());
                        updateActions(false);
                    }            
               });                                                                 
            }
            @Override
            public String getLabel() {                
                return messages.addToContacts();
            }         
        };
    }
    
    /**
     * Update page actions according to the page status
     */
    private void updateActions(boolean isAdd) {
        List<PageAction> actions = new ArrayList<PageAction>();
        if(isAdd) {
            actions.add(getAddToContactsAction());
        } else {
            actions.add(getRemoveContactAction());
        }
        if(LoggedUser.get().getInitialData().canMakeMemberPayments()) {
            actions.add(getMakePaymentAction());
        }      
        CyclosMobile.get().getMainLayout().addActions(actions, true);
    }

}
