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
package nl.strohalm.cyclos.mobile.client.ui;

import nl.strohalm.cyclos.mobile.client.model.Parameters;

/**
 * Contains the anchors for pages, and their URL parameters.
 * 
 * @author luis
 */
public enum PageAnchor {
    ACCOUNTS(PageGroup.ACCOUNTS),
    ACCOUNT_DETAILS(PageGroup.ACCOUNTS),
    CONFIGURATION(PageGroup.CONFIGURATION),
    CONTACTS(PageGroup.CONTACTS),
    CONTACT_DETAILS(PageGroup.CONTACTS),
    CONTACT_SELECT(PageGroup.PAYMENTS),
    HELP(PageGroup.GENERAL),
    LOAD_GENERAL_DATA(PageGroup.GENERAL),
    LOGIN(PageGroup.LOGIN),    
    MAKE_PAYMENT(PageGroup.PAYMENTS),
    MY_PROFILE(PageGroup.GENERAL),    
    PAYMENTS(PageGroup.PAYMENTS),
    PAYMENT_DETAILS(PageGroup.ACCOUNTS),
    PAYMENT_PREVIEW(PageGroup.PAYMENTS),
    USERS(PageGroup.CONTACTS),
    USER_DETAILS(PageGroup.CONTACTS),
    USER_SEARCH(PageGroup.CONTACTS),
    USER_SELECT(PageGroup.PAYMENTS);
    
    private PageGroup pageGroup;
    
    public PageGroup getPageGroup() {
        return pageGroup;
    }
    private PageAnchor(PageGroup pageGroup) {
        this.pageGroup = pageGroup;
    }
    /**
     * Returns the history token for this page anchor and the given parameters
     */
    public String toToken() {
        return name().toLowerCase();
    }

    /**
     * Returns the history token for this page anchor and the given parameters
     */
    public String toToken(Parameters parameters) {
        String token = toToken();
        String paramsToken = parameters == null ? null : parameters.toToken();
        if (paramsToken != null && !paramsToken.isEmpty()) {
            token += "!" + paramsToken;
        }
        return token;
    }
}
