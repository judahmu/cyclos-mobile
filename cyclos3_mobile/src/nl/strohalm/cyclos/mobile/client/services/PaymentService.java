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
package nl.strohalm.cyclos.mobile.client.services;

import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.model.PaymentConfirm;
import nl.strohalm.cyclos.mobile.client.model.PaymentData;
import nl.strohalm.cyclos.mobile.client.model.PaymentPreview;
import nl.strohalm.cyclos.mobile.client.model.TransferData;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface which provides operations for payment rest services.
 */
public interface PaymentService {

    /**
     * Gets details of an specific transfer and the payment custom fields declared in the transfer type
     */
    void getTransferData(Long transferId, AsyncCallback<TransferData> callback);
    
    /**
     * Gets the needed data for make a payment
     */
    void getPaymentData(Parameters params, AsyncCallback<PaymentData> callback);
    
    /**
     * Gets the payment preview with all the related information (Amount, Fees, TransferType...)<br>
     * If isSystem flag is true the service will return system payment preview otherwise it will return member payment preview
     */
    void getPaymentPreview(boolean isSystem, Parameters params, AsyncCallback<PaymentPreview> callback);
    
    /**
     * Perform a payment from the authenticated member to another member or system
     */
    void doPayment(boolean isSystem, Parameters params, AsyncCallback<PaymentConfirm> callback);
}
