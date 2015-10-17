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

/**
 * Provides different alternatives for storage information like cookies or local storage. 
 * This component relies on JavaScript native functionality.
 */
public class Storage {

    private static Storage instance;
    
    public static Storage get() {
        if(instance == null) {
            instance = new Storage();
        }
        return instance;
    }      
    
    /**
     * Clears the storage
     */
    public native void clear() /*-{
        $wnd.getLocalStorage().clear();
    }-*/;
    
    /**
     * Sets an item into storage
     */
    public native void setItem(String key, String data) /*-{
       $wnd.getLocalStorage().setItem(key,data);
    }-*/;
    
    /**
     * Gets an stored item
     */
    public native String getItem(String key) /*-{
        return $wnd.getLocalStorage().getItem(key);
    }-*/; 
    
    /**
     * Removes an item from the storage
     */
    public native void removeItem(String key) /*-{
        $wnd.getLocalStorage().removeItem(key);
    }-*/;
    
    /**
     * Removes the given items from the storage
     */
    public void removeItems(String... keys) {
        for(String key : keys) {
            removeItem(key);
        }        
    }
}
