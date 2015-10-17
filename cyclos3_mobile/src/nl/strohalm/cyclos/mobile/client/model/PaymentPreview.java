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

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.mobile.client.CyclosMobile;
import nl.strohalm.cyclos.mobile.client.utils.AmountHelper;
import nl.strohalm.cyclos.mobile.client.utils.JsonHelper;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

/**
 * Overlay type for a payment preview.
 */
public class PaymentPreview extends JavaScriptObject {
    
    protected PaymentPreview() {        
    }
    
    public final JsArray<Fee> getFees() {
        if(CyclosMobile.get().getGeneralData().getCyclosVersion().equals("3.7")) {
            return ensureFees();
        }
        return getNativeFees();
    }
    
    public final String getFormattedFinalAmount() {
        if(CyclosMobile.get().getGeneralData().getCyclosVersion().equals("3.7")) {
            return ensureFormattedFinalAmount();
        }
        return getNativeFormattedFinalAmount();
    }      
    
    public final native Member getFrom() /*-{
        return $wnd.cleanObject(this.from);
    }-*/;
    
    public final native Member getTo() /*-{
        return $wnd.cleanObject(this.to);
    }-*/;       
    
    public final native boolean isWouldRequireAuth() /*-{
        return this.wouldRequireAuthorization;
    }-*/;

    public final native Double getFinalAmount() /*-{
        return @java.lang.Double::valueOf(Ljava/lang/String;)($wnd.cleanString(this.finalAmount));
    }-*/;           
    
    public final native JsArrayString getCustomFieldKeys() /*-{
        return $wnd.getMapKeys(this.customValues);
    }-*/;    
    
    public final native String getCustomFieldValue(String key) /*-{
        var value = $wnd.getMapValue(this.customValues, key);
        if(value) {
            return $wnd.cleanString(value);
        }
        return null;
    }-*/;
    
    public final native TransferType getTransferType() /*-{
        return $wnd.cleanObject(this.transferType);
    }-*/;
    
    private final native JsArray<Fee> getNativeFees() /*-{
        return $wnd.cleanObject(this.fees);
    }-*/;
    
    private final native String getNativeFormattedFinalAmount() /*-{
        return $wnd.cleanString(this.formattedFinalAmount);
    }-*/;

    private final native JsArrayString getFeeKeys() /*-{
        return $wnd.getMapKeys(this.fees);
    }-*/;
    
    private final native Double getFeeAmount(String key) /*-{
        var value = $wnd.getMapValue(this.fees, key);
        if(value) {
            return @java.lang.Double::valueOf(Ljava/lang/String;)(value); 
        }                      
        return 0.0;
    }-*/;
    
    private final native String getUnitsPattern() /*-{
        return $wnd.cleanString(this.unitsPattern);
    }-*/;
    
    private final JsArray<Fee> ensureFees() {
        JsArrayString keys = getFeeKeys();
        List<Fee> fees = new ArrayList<Fee>();
        if(keys != null && keys.length() > 0) {
            for(int i = 0; i < keys.length(); i++) {     
                String key = keys.get(i);
                Fee fee = (Fee)JavaScriptObject.createObject().cast();
                fee.setName(key);
                fee.setFormattedAmount(AmountHelper.getFormattedAmount(getFeeAmount(key), getUnitsPattern()));
                fees.add(fee);
            }
        }
        return JsonHelper.createGenericArray(fees);
    }
    
    private final String ensureFormattedFinalAmount() {
        return AmountHelper.getFormattedAmount(getFinalAmount(), getUnitsPattern());
    }
    
}
