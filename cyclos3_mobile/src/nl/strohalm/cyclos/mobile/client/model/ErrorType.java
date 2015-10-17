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
package nl.strohalm.cyclos.mobile.client.model;

/**
 * Error types used for exception handling
 */
public enum ErrorType {    
    BLOCKED_CREDENTIALS,
    BLOCKED_TRANSACTION_PASSWORD,
    CHANNEL_DISABLED,
    NOT_FOUND, 
    INVALID_AMOUNT,
    INVALID_ARGUMENT,         
    INVALID_CREDENTIALS,
    INTERNAL_SERVER_ERROR,
    INACTIVE_TRANSACTION_PASSWORD,
    INVALID_TRANSACTION_PASSWORD,
    MAX_AMOUNT_PER_DAY_EXCEEDED,
    MISSING_TRANSACTION_PASSWORD,
    NO_POSSIBLE_TRANSFER_TYPES,
    NOT_ENOUGH_FUNDS,
    PERMISSION_DENIED, 
    TRANSFER_MINIMUM_PAYMENT,
    UNAUTHORIZED,             
    UNKNOWN_PAYMENT_ERROR,
    UNKNOWN_ERROR,
    UPPER_CREDIT_LIMIT_REACHED    
}
