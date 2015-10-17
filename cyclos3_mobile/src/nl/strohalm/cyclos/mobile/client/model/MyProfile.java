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

import com.google.gwt.core.client.JsArrayString;

/**
 * Overlay type for my profile data.
 * 
 * @author luis
 */
public class MyProfile extends Entity {

    protected MyProfile() {
    }
    
    public final native String getName() /*-{
        return $wnd.cleanString(this.name);
    }-*/;
    
    public final native String getUsername() /*-{
        return $wnd.cleanString(this.username);
    }-*/;
    
    public final native String getEmail() /*-{
        return $wnd.cleanString(this.username);
    }-*/;

    public final native JsArrayString getHiddenFields() /*-{
        return $wnd.cleanObject(this.hiddenFields);
    }-*/;

}
