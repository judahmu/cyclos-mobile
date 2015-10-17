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

import com.google.gwt.http.client.Response;

/**
 * Helper class for handling URLs and connection data 
 */
public class ConnectionHelper {
    
    private static String HTTP = "http://";
    private static String HTTPS = "https://";

    /**
     * Returns if the URL contains HTTP protocol
     */
    public static boolean isHTTP(String url) {
        if(StringHelper.isNotEmpty(url)) {
            return url.toLowerCase().indexOf(HTTP) != -1;
        }
        return false;
    }
    
    /**
     * Returns if the URL contains HTTPS protocol
     */
    public static boolean isHTTPs(String url) {
        if(StringHelper.isNotEmpty(url)) {
            return url.toLowerCase().indexOf(HTTPS) != -1;
        }
        return false;
    }
    
    /**
     * Appends HTTP protocol to the given URL only if it is not present in it
     */
    public static String appendHTTP(String url) {
        if(isHTTP(url)) {
            return url;
        } else {
            return HTTP + url;
        }
    }
    
    /**
     * Appends HTTPS protocol to the given URL only if it is not present in it
     */
    public static String appendHTTPs(String url) {
        if(isHTTPs(url)) {
            return url;
        } else {
            return HTTPS + url;
        }
    }        
    
    /**
     * Returns if the response value is JSON
     */
    public static boolean isJSON(Response response) {
        String contentType = response.getHeader("Content-Type");
        boolean isJson = StringHelper.isNotEmpty(contentType) && (contentType.indexOf("application/json") != -1);
        return isJson;
    }
    
}
