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
package nl.strohalm.cyclos.mobile.client.users;

import java.util.Arrays;
import java.util.List;

import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.model.BasicMember;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;
import nl.strohalm.cyclos.mobile.client.utils.ParameterKey;
import nl.strohalm.cyclos.mobile.client.utils.ResultPage;

/**
 * A {@link Page} used to select an user before make a payment 
 */
public class UserSelectPage extends UsersPage {
    
    @Override
    public String getHeading() {
        return messages.selectUserHeading();
    }
    
    /**
     * Go to make payment's page sending selected member id as parameter
     */
    @Override
    protected void navigateTo(Long memberId) {
        Parameters params = new Parameters();
        params.add(ParameterKey.ID, memberId);   
        Navigation.get().go(PageAnchor.MAKE_PAYMENT, params);    
    }
    
    @Override
    protected void onDataLoaded(ResultPage<BasicMember> result) {
        // If only one user exists with the given name 
        // continue to make payment's page
        if(result.getTotalCount() == 1) {
            Long memberId = result.getElements().get(0).getId();
            Parameters params = new Parameters();
            params.add(ParameterKey.ID, memberId);   
            Navigation.get().goNoHistory(PageAnchor.MAKE_PAYMENT, params);    
        }
    }
    
    @Override
    public List<PageAction> getPageActions() {
        return Arrays.asList(getCancelAction());
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

}
