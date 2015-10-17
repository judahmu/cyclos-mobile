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
 * Overlay type for general data.
 * 
 * @author luis
 */
public class GeneralData extends JavaScriptObject {

    protected GeneralData() {
    }

    public final native String getWelcomeMessage() /*-{
        return $wnd.cleanString(this.welcomeMessage);
    }-*/;
    
    public final native String getApplicationName() /*-{
        return $wnd.cleanString(this.applicationName);
    }-*/;

    public final native CredentialType getCredentialType() /*-{
        return @nl.strohalm.cyclos.mobile.client.model.CredentialType::valueOf(Ljava/lang/String;)(this.credentialType);
    }-*/;

    public final native String getCyclosVersion() /*-{
        return $wnd.cleanString(this.cyclosVersion);
    }-*/;

    public final native CustomField getPrincipalCustomField() /*-{
        return $wnd.cleanObject(this.principalCustomField);
    }-*/;

    public final native PrincipalType getPrincipalType() /*-{
        return @nl.strohalm.cyclos.mobile.client.model.PrincipalType::valueOf(Ljava/lang/String;)(this.principalType);
    }-*/;
    
    public final native JsArray<Image> getImages() /*-{
        return $wnd.cleanObject(this.images);
    }-*/;
    
}
