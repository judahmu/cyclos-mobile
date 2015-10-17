
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
package nl.strohalm.cyclos.mobile.client;

import com.google.gwt.core.client.GWT;

/**
 * I18N messages interface.
 * 
 * @author luis
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {

    /**
     * Accessor class for the messages interface
     * 
     * @author luis
     */
    public static class Accessor {
        private static final Messages INSTANCE = GWT.create(Messages.class);

        public static Messages get() {
            return INSTANCE;
        }
    }

    String accounts();
    
    String accountDetailsHeading();
    
    String accountsHeading();
    
    String accountsHelpText();
    
    String addToContacts();
    
    String addNewContact();
    
    String amount();
    
    String amountIn(String currencyName);
    
    String amountIsRequired();
    
    String appliedFees();
    
    String availableBalance(String value);
    
    String blockedCredentials();
    
    String channelDisabled();
    
    String goToConfiguration();
    
    String goToLogin();
    
    String balance();
    
    String blockedTransactionPassword();
    
    String cancel();
    
    String cannotMakePayments();
    
    String changeUser();
    
    String close();
    
    String configuration();

    String configurationHeading();
    
    String configurationHelpText();
    
    String configurationSaved();
    
    String confirmPayment();
    
    String connectionError();
    
    String contacts();
    
    String contactAdded();
    
    String contactDetailsHeading();
    
    String contactsHelpText();
    
    String contactsHeading();
    
    String contactRemoved();
    
    String cyclosUrl();
    
    String cyclosUrlIsRequired();
    
    String customFieldExceedsMaxLength(String fieldName);
    
    String customFieldNotExceedsMinLength(String fieldName);
    
    String customFieldIsRequired(String fieldName);
    
    String dataNotFound();
    
    String date();
    
    String deleteContact(String contactName);
    
    String description();
    
    String email();
    
    String error();
    
    String forExample();
        
    String from();
    
    String help();
    
    String helpHeading();
    
    String helpText();
    
    String insufficientBalance();
    
    String invalidAmount();
    
    String invalidCredentials();
    
    String invalidCustomField(String fieldName);
    
    String invalidDateFormat(String pattern);
    
    String invalidNumberFormat();
    
    String inactiveTransactionPassword();
    
    String invalidTransactionPassword();
    
    String invalidUrlConfiguration();       
    
    String language();

    String loadGeneralDataHeading();

    String loadGeneralDataMessage();

    String loadingApplicationError();
    
    String loadingConfiguration();
    
    String loadingData();

    String login();
    
    String loginAs();
    
    String loginFieldsCannotBeEmpty();
    
    String loginHeading();
    
    String loginHelpText();
    
    String logoutConfirmation();
    
    String logoutConfirmationText();
    
    String lowerCreditLimit(String value);
    
    String makePayment();
    
    String makePaymentHeading();
    
    String maxAmountPerDayExceeded();

    String missingParameter(String parameter);
    
    String missingTransactionPassword();
    
    String name();
    
    String newPayment();
    
    String newSearch();
    
    String next();
    
    String no();
    
    String notAvailableData();
    
    String notAvailableOptions();
    
    String notice();
    
    String ok();
    
    String orTypeUserNameHere();
    
    String pageNotFoundError();
    
    String payments();
    
    String paymentConfirmationHeading();
    
    String paymentDetailsHeading();
    
    String paymentsHelpText();
    
    String paymentsHeading();
    
    String paymentNumber();
    
    String paymentPerformedSuccessfully();
    
    String paymentRequireAuthorization();
    
    String permissionDenied();    
    
    String positiveNumber();
        
    String requestTimeout();
    
    String removeContact();
    
    String save();
    
    String search();
    
    String searchUser();
    
    String searchUserHeading();
    
    String secureLogin();
    
    String selectContactHeading();       
    
    String sessionExpired();
    
    String supplyTransactionPassword();
    
    String toMyContact();
    
    String toSystemAccount();        
    
    String selectUserHeading();
    
    String serverError();
    
    String statusCodeRequestedAction(int statusCode);
    
    String theLanguageHasChanged();
    
    String theLanguageHasChangedNoReload();
    
    String to();
    
    String type();
    
    String typeCyclosUrlHere();         
    
    String typeUserOrLoginNameHere();    
    
    String typeYourCardHere();   
    
    String typeYourCardSecurityCodeHere();
    
    String typeYourCustomFieldHere(String customFieldName);
    
    String typeYourEmailHere();
    
    String typeYourPasswordHere();
    
    String typeYourPinHere();
    
    String typeYourTransactionPasswordHere();
    
    String typeYourUsernameHere();
    
    String typeUserNameOrSelectAContact();
    
    String transactionPasswordIsRequired();
    
    String transactionType();
    
    String transferMinimumPayment();
    
    String unauthorized();

    String uncaughtError(String message);
    
    String unknownPaymentError();
    
    String unknownError();
    
    String upperCreditLimitReached();
    
    String username();
    
    String userOrLoginNameIsRequired();
    
    String userDetailsHeading();
    
    String usersHeading();
    
    String version();
    
    String welcome(String appName);
    
    String yes();
    
    String youAreAboutToPerformTheFollowingPayment();
    
    String youAreAboutToPerformTheFollowingPaymentWithAuth();

}
