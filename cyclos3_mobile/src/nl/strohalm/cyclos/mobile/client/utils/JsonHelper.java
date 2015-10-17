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
package nl.strohalm.cyclos.mobile.client.utils;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Provides helper methods for JSON manipulation
 */
public class JsonHelper {
    
    @SuppressWarnings("unchecked")
    public static <T extends JavaScriptObject> T deepCopy(T obj) {
        return (T) deepCopyImpl(obj);
    }

    private static native JavaScriptObject deepCopyImpl(JavaScriptObject obj) /*-{

        if (obj == null) return obj;

        var copy;        

        if (obj instanceof Date) {
            // Handle Date
            copy = new Date();
            copy.setTime(obj.getTime());
        } else if (obj instanceof Array) {
             // Handle Array
            copy = [];
            for (var i = 0, len = obj.length; i < len; i++) {
                if (obj[i] == null || typeof obj[i] != "object") copy[i] = obj[i];
                else copy[i] = @nl.strohalm.cyclos.mobile.client.utils.JsonHelper::deepCopyImpl(Lcom/google/gwt/core/client/JavaScriptObject;)(obj[i]);
            }
        } else {
            // Handle Object
            copy = {};
            for (var attr in obj) {
                if (obj.hasOwnProperty(attr)) {
                    if (obj[attr] == null || typeof obj[attr] != "object") copy[attr] = obj[attr];
                    else copy[attr] = @nl.strohalm.cyclos.mobile.client.utils.JsonHelper::deepCopyImpl(Lcom/google/gwt/core/client/JavaScriptObject;)(obj[attr]);
                }
            }
        }        
        return copy;
    }-*/;

    public static native String stringify(JavaScriptObject json) /*-{
        return $wnd.JSON.stringify(json);
    }-*/;
    
    public static native JsArray<? extends JavaScriptObject> parseArray(String json) /*-{
        return $wnd.JSON.parse(json);  
    }-*/;
    
    public static native <T extends JavaScriptObject> T parseObject(String json) /*-{
        return $wnd.JSON.parse(json);
    }-*/;
    
    public static <T extends JavaScriptObject> JsArray<T> createGenericArray(List<T> objects) {
        JsArray<T> array = JavaScriptObject.createArray().cast();
        for (T object : objects) {
            array.push(object);
        }
        return array;
    }

} 