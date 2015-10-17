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

import nl.strohalm.cyclos.mobile.client.CyclosMobile;
import nl.strohalm.cyclos.mobile.client.model.GeneralData;
import nl.strohalm.cyclos.mobile.client.model.Image;

import com.google.gwt.core.client.JsArray;

/**
 * Provides helper methods for manipulating images
 */
public class ImageHelper {

    private static final String SPLASH_SMALL = "mobileSplash_small";
    private static final String SPLASH_MEDIUM = "mobileSplash_medium";
    private static final String SPLASH_LARGE = "mobileSplash_large";
    
    /**
     * Resolves the given splash screen based on general data images
     */
    public static Image resolveSplashScreen(String splashName) {
        
        GeneralData data = CyclosMobile.get().getGeneralData();        
        if(data != null && data.getImages() != null) {
            return findImage(splashName, data.getImages());
        }
        // No splash screen found
        return null;       
    }
    
    /**
     * Returns the splash screen which should fit the current screen size
     */
    public static Image getSplashScreen() {
        if(isSmallSplash()) {
            return resolveSplashScreen(SPLASH_SMALL);
        } else if(isMediumSplash()) {
            return resolveSplashScreen(SPLASH_MEDIUM);            
        } else if(isLargeSplash()) {
            return resolveSplashScreen(SPLASH_LARGE);
        }
        return null;
    }
    
    /**
     * Preloads the given image source
     */
    public static native void preloadImage(String imageSrc) /*-{
        $wnd.preloadImage(imageSrc);
    }-*/;
            
    /**
     * Finds an image by the given name into the given image array
     */
    private static Image findImage(String imageName, JsArray<Image> images) {
        for(int i = 0; i < images.length(); i++) {
            Image image = images.get(i);
            if(image.getCaption().contains(imageName)) {
                return image;
            }
        }
        return null;
    }
    
    /**
     * Returns if the splash screen is the small one
     */
    private static native boolean isSmallSplash() /*-{
        return $wnd.isSmallSplash();
    }-*/;
    
    /**
     * Returns if the splash screen is the medium one
     */
    private static native boolean isMediumSplash() /*-{
        return $wnd.isMediumSplash();
    }-*/;
    
    /**
     * Returns if the splash screen is the large one
     */
    private static native boolean isLargeSplash() /*-{
        return $wnd.isLargeSplash();
    }-*/;
    
}
