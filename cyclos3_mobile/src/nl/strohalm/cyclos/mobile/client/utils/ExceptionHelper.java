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
package nl.strohalm.cyclos.mobile.client.utils;

import nl.strohalm.cyclos.mobile.client.Messages;
import nl.strohalm.cyclos.mobile.client.model.CustomField;
import nl.strohalm.cyclos.mobile.client.model.CustomValueException.ValidationType;
import nl.strohalm.cyclos.mobile.client.model.ErrorType;

/**
 * Helper class for resolve exception messages
 */
public class ExceptionHelper {

    private static Messages messages = Messages.Accessor.get();
   
    /**
     * Resolves a RequestTimeOutException's message
     */
    public static String resolveRequestTimeoutMessage() {
       return messages.requestTimeout(); 
    }
    
    /**
     * Resolves a StatusCodeException's message by the given status code id
     */
    public static String resolveStatusCodeMessage(int statusCode) {        
        switch(statusCode) {
            case 0:
            case 404:
                return messages.connectionError();
            case 401:
                return messages.permissionDenied();
            case 500:
            case 503:
                return messages.serverError();
        }      
        return messages.statusCodeRequestedAction(statusCode);
    }
    
    /**
     * Resolves a general exception message. Returns the given message if not empty, otherwise returns a default message
     */
    public static String resolveGeneralMessage(String localizedMessage) {
        if(StringHelper.isNotEmpty(localizedMessage)) {           
            if(localizedMessage.length() > 75) { // Limit output
                localizedMessage = localizedMessage.substring(0, 75) + " ...";
            }                          
            return messages.uncaughtError(localizedMessage); 
        }
       return messages.unknownError();
    }
    
    /**
     * Resolves a custom value field validation
     */
    public static String resolveCustomValueMessage(CustomField customField, ValidationType type) {
        switch (type) {
            case REQUIRED: 
                return messages.customFieldIsRequired(customField.getDisplayName());
            case MAX_LENGTH:
                return messages.customFieldExceedsMaxLength(customField.getDisplayName());
            case MIN_LENGTH:
                return messages.customFieldNotExceedsMinLength(customField.getDisplayName());                            
        }
        return messages.invalidCustomField(customField.getDisplayName());
    }
    
    /**
     *  Resolves a RestServiceException's message according to the given error type      
     */
    public static String resolveRestServiceMessage(ErrorType type) {
        switch (type) {
            case BLOCKED_CREDENTIALS:
                return messages.blockedCredentials();
            case BLOCKED_TRANSACTION_PASSWORD:
                return messages.blockedTransactionPassword();
            case CHANNEL_DISABLED:
                return messages.channelDisabled();
            case INACTIVE_TRANSACTION_PASSWORD:
                return messages.inactiveTransactionPassword();
            case INTERNAL_SERVER_ERROR:
                return messages.serverError();
            case INVALID_AMOUNT:
                return messages.invalidAmount();            
            case INVALID_CREDENTIALS:
                return messages.invalidCredentials();
            case INVALID_TRANSACTION_PASSWORD:
                return messages.invalidTransactionPassword();
            case MAX_AMOUNT_PER_DAY_EXCEEDED:
                return messages.maxAmountPerDayExceeded();
            case MISSING_TRANSACTION_PASSWORD:
                return messages.missingTransactionPassword();
            case NOT_ENOUGH_FUNDS:
                return messages.insufficientBalance();
            case NOT_FOUND:
                return messages.dataNotFound();
            case NO_POSSIBLE_TRANSFER_TYPES:
                return messages.cannotMakePayments();
            case PERMISSION_DENIED:
                return messages.permissionDenied();                
            case TRANSFER_MINIMUM_PAYMENT:
                return messages.transferMinimumPayment();
            case UNAUTHORIZED:
                return messages.unauthorized(); 
            case UNKNOWN_PAYMENT_ERROR:
                return messages.unknownPaymentError();
            case UPPER_CREDIT_LIMIT_REACHED:
                return messages.upperCreditLimitReached();
        }
        return messages.unknownError();
    }    
    
    /**
     * Resolves a number format exception's message
     */
    public static String resolveNumberFormatMessage() {
        return messages.invalidNumberFormat();
    }
    
    /**
     * Resolves an illegal argument exception's message
     */
    public static String resolveIllegalArgumentMessage(String argument) {
        return messages.missingParameter(argument);
    }
    
    /**
     * Resolves a positive number exception's message
     */
    public static String resolvePositiveNumberMessage() {
        return messages.positiveNumber();
    }
    
    /**
     * Resolves a date format exception's message
     */
    public static String resolveDateFormatMessage(String pattern) {
        return messages.invalidDateFormat(pattern);
    }
    
}
