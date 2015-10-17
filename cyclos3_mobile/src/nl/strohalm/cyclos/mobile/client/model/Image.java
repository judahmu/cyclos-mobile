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

import nl.strohalm.cyclos.mobile.client.Configuration;

/**
 * Overlay type for images.
 * 
 * @author luis
 */
public class Image extends Entity {
    
    protected Image() {      
    }

    public final native String getCaption() /*-{
        return $wnd.cleanString(this.caption);
    }-*/;

    public final native String getFullUrl() /*-{
        return $wnd.cleanString(this.fullUrl);
    }-*/;

    public final native String getThumbnailUrl() /*-{
        return $wnd.cleanString(this.thumbnailUrl);
    }-*/;

    public final Long getLastModified() {
        try {        
            return Configuration.get().getDateTimeFormat().parse(getLastModifiedDate()).getTime();
        } catch(Exception e) {    
        }
        return 0l;
    }
    
    private final native String getLastModifiedDate() /*-{
        return $wnd.cleanString(this.lastModified);
    }-*/;
}
