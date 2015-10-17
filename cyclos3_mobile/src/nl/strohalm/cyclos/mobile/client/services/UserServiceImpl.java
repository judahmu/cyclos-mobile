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

import nl.strohalm.cyclos.mobile.client.model.BasicMember;
import nl.strohalm.cyclos.mobile.client.model.MemberData;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.utils.RestRequest;
import nl.strohalm.cyclos.mobile.client.utils.ResultPage;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Implementation for {@link UserService}.
 */
public class UserServiceImpl implements UserService {

    @Override
    public void searchMembers(Parameters parameters, AsyncCallback<ResultPage<BasicMember>> callback) {
        RestRequest<ResultPage<BasicMember>> request = new RestRequest<ResultPage<BasicMember>>(RequestBuilder.GET, "members", parameters);                      
        request.sendAuthenticated(callback);
    }

    @Override
    public void getMemberData(Long memberId, AsyncCallback<MemberData> callback) {
        RestRequest<MemberData> request = new RestRequest<MemberData>(RequestBuilder.GET, "members/memberData/"+memberId);                      
        request.sendAuthenticated(callback);
    }

}
