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

import nl.strohalm.cyclos.mobile.client.model.Contact;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface which encapsulates operations in the /contacts rest services.
 */
public interface ContactService {
    
    /**
     * Saves a contact by member id and returns the related information about him
     */
    void saveContact(Long memberId, AsyncCallback<Contact> callback);
    
    /**
     * Returns contact by member id
     */
    void getContactById(Long memberId, AsyncCallback<Contact> callback);
    
    /**
     * Removes a contact by member id
     */
    void removeContact(Long memberId, AsyncCallback<Void> callback);
    
    /**
     * List all contacts
     */
    void list(AsyncCallback<JsArray<Contact>> callback);
}
