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
package nl.strohalm.cyclos.mobile.client;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.utils.Storage;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.i18n.client.LocaleInfo;

/**
 * Holds the mobile application configuration.
 * 
 * @author luis
 */
public class Configuration {   
    
    private static Configuration instance;    
    private static String ROOT_URL;
    private static String APP_URL;
    private static boolean DEFAULT_SPLASH_SCREEN = true;
    private static int DEFAULT_TIMEOUT = 180; // Seconds
    
    private DateTimeFormat format;

    public static Configuration get() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }
    
    private Configuration() {
        this.format = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
    }

    /**
     * Returns DateTimeFormat instance for date formatting
     */
    public DateTimeFormat getDateTimeFormat() {
        return this.format;
    }
    
    /**
     * Sets the server root URL which will be used
     * for services location
     */
    public void setServerRootUrl(String rootUrl) {    
        if(StringHelper.isNotEmpty(rootUrl)) {
            rootUrl = rootUrl.trim(); // Trim spaces
        }
        ROOT_URL = rootUrl;
        Storage.get().setItem("rootUrl", rootUrl);              
    }
    
    /**
     * Sets the visible server URL for the user
     */
    public void setServerAppUrl(String appUrl) {
        APP_URL = appUrl;
        Storage.get().setItem("appUrl", appUrl);        
    }
    
    /**
     * Returns the server application URL given by the user
     */
    public String getServerAppUrl() {
        if(APP_URL == null) {            
            APP_URL = Storage.get().getItem("appUrl");            
        }               
        return APP_URL;
    }
    
    /**
     * Returns server root URL
     */
    public String getServerRootUrl() {
        if(ROOT_URL == null) {            
            // Check for global options if any
            ROOT_URL = readProperty("hostUrl");

            if(StringHelper.isEmpty(ROOT_URL)) {
                ROOT_URL = Storage.get().getItem("rootUrl");             
            }                
        }               
        return ROOT_URL;
    }
    
    /**
     * Returns if the URL configuration functionality is enabled
     */
    public boolean isURLConfigEnabled() {
        String url = readProperty("hostUrl");
        return StringHelper.isEmpty(url);
    }
    
    /**
     * Returns if the splash screen is enabled
     */
    public boolean isSplashScreenEnabled() {
        String value = readProperty("splashScreen");
        if(StringHelper.isNotEmpty(value)) {
            return Boolean.valueOf(value);
        }
        return DEFAULT_SPLASH_SCREEN;
    }
    
    /**
     * Returns the session timeout in milliseconds
     */
    public int getSessionTimeout() {      
        String value = readProperty("sessionTimeout");
        if(StringHelper.isNotEmpty(value)) {
            return Integer.valueOf(value) * 1000;
        }               
        return DEFAULT_TIMEOUT * 1000;
    }
    
    /**
     * Returns the application version set in properties file
     */
    public String getAppVersion() {
        return readProperty("version");        
    }
    
    /**
     * Returns the full URL for the given service relative path
     */
    public String getServiceUrl(String path) {
        return getServiceUrl(path, null);
    }

    /**
     * Returns the full URL for the given service relative path
     */
    public String getServiceUrl(String path, Parameters queryParameters) {
        String url = getServerRootUrl() + "/rest/" + path;
        if (queryParameters != null) {
            String token = queryParameters.toToken();
            if (!token.isEmpty()) {
                url += "?" + token;
            }
        }
        return url;
    }
    
    /**
     * Returns the default language
     */
    public String getDefaultLanguage() {
        return LocaleInfo.getCurrentLocale().getLocaleName();
    }    

    /**
     * Returns the user language
     */
    public String getUserLanguage() {
        return Storage.get().getItem("language");
    } 
    
    /**
     * Sets the user language
     */
    public void setUserLanguage(String language) {
        Storage.get().setItem("language", language);        
    }
    
    /**
     * Returns whether the server is configured on this instance
     */
    public boolean isServerConfigured() {  
        String url = getServerRootUrl();     
        return StringHelper.isNotEmpty(url);
    }
    
    /**
     * Returns the current defined general locale list
     */
    public List<String> getLocales() {
        List<String> locales = new ArrayList<String>();
        for(String localeName : LocaleInfo.getAvailableLocaleNames()) {
            // Add general locale only
            if(!localeName.equalsIgnoreCase("default") && localeName.indexOf("_") < 1) { 
                locales.add(localeName);
            }
        }
        return locales;
    }

    /**
     * Reads the given property from the properties dictionary.<br>
     * 
     * @return null if the property is empty or was not found
     */
    private String readProperty(String property) {
        // Check if global option is set
        try {
            Dictionary opts = Dictionary.getDictionary("options");
            if(opts != null) {
                String value = opts.get(property);
                if(StringHelper.isNotEmpty(value)) {
                    return value;
                }
            }
        } catch(Exception e) {
            // Does nothing, global options not defined
        }
        return null;
    }
}
