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
 * Overlay type for Transfer.
 */
public class Transfer extends EntityWithCustomFields {
    
    protected Transfer() {        
    }
    
    public final native String getDate() /*-{
        return $wnd.cleanString(this.date);
    }-*/;
    
    public final native String getFormattedDate() /*-{
        return $wnd.cleanString(this.formattedDate);
    }-*/;    

    public final native String getProcessDate() /*-{
        return $wnd.cleanString(this.processDate);
    }-*/;
    
    public final native String getFormattedProcessDate() /*-{
        return $wnd.cleanString(this.formattedProcessDate);
    }-*/;
    
    public final native Double getAmount() /*-{
        return @java.lang.Double::valueOf(Ljava/lang/String;)($wnd.cleanString(this.amount));
    }-*/;
    
    public final native String getFormattedAmount() /*-{
        return $wnd.cleanString(this.formattedAmount);
    }-*/;
    
    public final native TransferType getTransferType() /*-{
       return $wnd.cleanObject(this.transferType); 
    }-*/;
    
    public final native String getDescription() /*-{
        return $wnd.cleanString(this.description);
    }-*/;
    
    public final native BasicMember getBasicMember() /*-{
        return $wnd.cleanObject(this.member);
    }-*/;
    
    public final native String getName() /*-{
        return $wnd.cleanString(this.name);
    }-*/;
    
    public final native String getSystemAccountName() /*-{
        return $wnd.cleanString(this.systemAccountName);
    }-*/;
    
    public final native String getTransactionNumber() /*-{
        return $wnd.cleanString(this.transactionNumber);
    }-*/;
    
}
