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
package nl.strohalm.cyclos.mobile.client.utils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Overlay type for a result page (object returned on search methods)
 * 
 * @author luis
 */
public class ResultPage<T extends JavaScriptObject> extends JavaScriptObject {

    protected ResultPage() {
    }

    /**
     * Returns the current page on the search
     */
    public final native int getCurrentPage() /*-{
        return this.currentPage;
    }-*/;

    /**
     * Returns the elements array
     */
    public final native JsArray<T> getElements() /*-{
        return $wnd.cleanObject(this.elements);
    }-*/;

    /**
     * Returns the page size
     */
    public final native int getPageSize() /*-{
        return this.pageSize;
    }-*/;

    /**
     * Returns the total record count
     */
    public final native int getTotalCount() /*-{
        return this.totalCount;
    }-*/;

}
