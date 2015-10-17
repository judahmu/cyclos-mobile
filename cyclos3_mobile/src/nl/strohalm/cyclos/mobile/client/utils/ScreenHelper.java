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
 * Helper class for screen management 
 */
public class ScreenHelper {
    
    /**
     * Screen density enum types     
     */
    public enum ScreenDensity {
        MDPI,
        HDPI,
        XHDPI
    }
    
    /**
     * Returns the screen density enum type
     */
    public static ScreenDensity getScreenDensity() {
        if(isHdpi()) {
            return ScreenDensity.HDPI;
        } else if(isXhdpi()) {
            return ScreenDensity.XHDPI;
        }
        return ScreenDensity.MDPI;
    }

    /**
     * Returns whether the screen has a high density pixel
     */
    public static native boolean isHdpi() /*-{
        return $wnd.isHdpi();
    }-*/;
    
    /**
     * Returns whether the screen has a extra high density pixel
     */
    public static native boolean isXhdpi() /*-{
        return $wnd.isXhdpi();
    }-*/;
    
    /**
     * Returns the screen avail width
     */
    public static native int getScreenWidth() /*-{
        return $wnd.getScreenWidth();
    }-*/;
    
    /**
     * Returns the screen avail height
     */
    public static native int getScreenHeight() /*-{
        return $wnd.getScreenHeight();
    }-*/;
    
    /**
     * Returns if the device supports touch events
     */
    public static native boolean supportsTouch() /*-{
        return $wnd.isTouchDevice();
    }-*/;
    
    /**
     * Returns if the device supports reload UI
     */
    public static native boolean supportsReload() /*-{
        return $wnd.supportsReload();
    }-*/;
    
}
