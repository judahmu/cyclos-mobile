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
package nl.strohalm.cyclos.mobile.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Overlay type for initial data.
 * 
 * @author luis
 */
public class InitialData extends JavaScriptObject {

    protected InitialData() {
    }

    public final native JsArray<Account> getAccounts() /*-{
        return $wnd.cleanObject(this.accounts);
    }-*/;

    public final native MyProfile getProfile() /*-{
        return $wnd.cleanObject(this.profile);
    }-*/;

    public final native boolean isRequireTransactionPassword() /*-{
        return this.requireTransactionPassword;
    }-*/;
    
    public final native boolean canMakeSystemPayments() /*-{
        return this.canMakeSystemPayments;  
    }-*/;
    
    public final native boolean canMakeMemberPayments() /*-{
        return this.canMakeMemberPayments;
    }-*/;
    
    public final native int getDecimalCount() /*-{
        return this.decimalCount;
    }-*/;
    
    public final native String getDecimalSeparator() /*-{
        return $wnd.cleanString(this.decimalSeparator);
    }-*/;

}
