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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Overlay type for custom field value
 */
public class CustomFieldValue extends JavaScriptObject {

    protected CustomFieldValue() {        
    }
    
    public final native String getInternalName() /*-{
        return $wnd.cleanString(this.internalName);
    }-*/;
    
    public final native String getDisplayName() /*-{
        return $wnd.cleanString(this.displayName);
    }-*/;
    
    public final native String getValue() /*-{
        return $wnd.cleanString(this.value);
    }-*/;
    
    public final native Long getPossibleValueId() /*-{
        var val = $wnd.cleanString(this.possibleValueId);        
        return $wnd.isNumeric(val) ? @java.lang.Long::valueOf(Ljava/lang/String;)(val) : null;
    }-*/;
}
