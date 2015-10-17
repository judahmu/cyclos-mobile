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
import nl.strohalm.cyclos.mobile.client.utils.RestRequest;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Implementation for {@link ContactService}
 */
public class ContactServiceImpl implements ContactService {

    @Override
    public void saveContact(Long memberId, AsyncCallback<Contact> callback) {
        RestRequest<Contact> request = new RestRequest<Contact>(RequestBuilder.POST, "contacts/byMemberId/"+memberId);                           
        request.sendAuthenticated(callback);       
    }

    @Override
    public void getContactById(Long memberId, AsyncCallback<Contact> callback) {
        RestRequest<Contact> request = new RestRequest<Contact>(RequestBuilder.GET, "contacts/byMemberId/"+memberId);
        request.sendAuthenticated(callback);     
    }
    
    @Override
    public void removeContact(Long memberId, AsyncCallback<Void> callback) {
        RestRequest<Void> request = new RestRequest<Void>(RequestBuilder.DELETE, "contacts/byMemberId/"+memberId);
        request.sendAuthenticated(callback);
    }

    @Override
    public void list(AsyncCallback<JsArray<Contact>> callback) {
        RestRequest<JsArray<Contact>> request = new RestRequest<JsArray<Contact>>(RequestBuilder.GET, "contacts");
        request.sendAuthenticated(callback);
    }    

}
