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

/**
 * Overlay type for Account Status.
 */
public class AccountStatus extends Entity {
    
    protected AccountStatus() {
    }
       
    public final native Double getBalance() /*-{
        return @java.lang.Double::valueOf(Ljava/lang/String;)($wnd.cleanString(this.balance));
    }-*/;
    
    public final native String getFormattedBalance() /*-{
        return $wnd.cleanString(this.formattedBalance);
    }-*/;
    
    public final native Double getReservedAmount() /*-{
        return @java.lang.Double::valueOf(Ljava/lang/String;)($wnd.cleanString(this.reservedAmount));
    }-*/;

    public final native String getFormattedReservedAmount() /*-{
        return $wnd.cleanString(this.formattedReservedAmount);
    }-*/;
    
    public final native Double getAvailableBalance() /*-{
        return @java.lang.Double::valueOf(Ljava/lang/String;)($wnd.cleanString(this.availableBalance));        
    }-*/;

    public final native String getFormattedAvailableBalance() /*-{
        return $wnd.cleanString(this.formattedAvailableBalance);
    }-*/;
    
    public final native Double getCreditLimit() /*-{
        return @java.lang.Double::valueOf(Ljava/lang/String;)($wnd.cleanString(this.creditLimit));
    }-*/;
    
    public final native String getFormattedCreditLimit() /*-{
        return $wnd.cleanString(this.formattedCreditLimit);
    }-*/;
    
    public final native Double getUpperCreditLimit() /*-{
        return @java.lang.Double::valueOf(Ljava/lang/String;)($wnd.cleanString(this.upperCreditLimit));        
    }-*/;
    
    public final native String getFormattedUpperCreditLimit() /*-{
        return $wnd.cleanString(this.formattedUpperCreditLimit);
    }-*/;    
    
}
