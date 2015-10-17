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

import com.google.gwt.core.client.JsArray;

/**
 * Overlay type for custom field.
 * 
 * @author luis
 */
public class CustomField extends Entity {

    protected CustomField() {
    }
    
    public final native String getDisplayName() /*-{
        return $wnd.cleanString(this.displayName);
    }-*/;
    
    public final native String getInternalName() /*-{
        return $wnd.cleanString(this.internalName);
    }-*/;
    
    public final native CustomFieldType getType() /*-{
        return @nl.strohalm.cyclos.mobile.client.model.CustomFieldType::valueOf(Ljava/lang/String;)(this.type);
    }-*/;

    public final native CustomFieldControl getControl() /*-{
        return @nl.strohalm.cyclos.mobile.client.model.CustomFieldControl::valueOf(Ljava/lang/String;)(this.control);
    }-*/;

    public final native boolean isRequired() /*-{
        return this.required;
    }-*/;

    public final native String getMask() /*-{
        return $wnd.cleanString(this.mask);
    }-*/;    

    public final native Long getParentId() /*-{
        var val = $wnd.cleanString(this.parentId);        
        return $wnd.isNumeric(val) ? @java.lang.Long::valueOf(Ljava/lang/String;)(val) : null;
    }-*/;
    
    public final native JsArray<PossibleValue> getPossibleValues() /*-{
        return $wnd.cleanObject(this.possibleValues);
    }-*/;
    
    public final native Integer getMaxLength() /*-{
        var val = $wnd.cleanString(this.maxLength);        
        return $wnd.isNumeric(val) ? @java.lang.Integer::valueOf(Ljava/lang/String;)(val) : null;
    }-*/;
    
    public final native Integer getMinLength() /*-{
        var val = $wnd.cleanString(this.minLength);
        return $wnd.isNumeric(val) ? @java.lang.Integer::valueOf(Ljava/lang/String;)(val) : null;
    }-*/;

}
