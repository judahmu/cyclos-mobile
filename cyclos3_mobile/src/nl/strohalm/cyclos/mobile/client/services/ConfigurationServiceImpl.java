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
package nl.strohalm.cyclos.mobile.client.services;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nl.strohalm.cyclos.mobile.client.Configuration;
import nl.strohalm.cyclos.mobile.client.model.ConfigurationStatus;
import nl.strohalm.cyclos.mobile.client.utils.ConnectAction;
import nl.strohalm.cyclos.mobile.client.utils.ConnectRequest;
import nl.strohalm.cyclos.mobile.client.utils.ScreenHelper;
import nl.strohalm.cyclos.mobile.client.utils.Storage;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;
import nl.strohalm.cyclos.mobile.client.utils.ConnectionHelper;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Implementation for {@link ConfigurationService}.
 */
public class ConfigurationServiceImpl implements ConfigurationService {

    @Override
    public void configureApplication(final String url, final String language, final AsyncCallback<ConfigurationStatus> callback) {                                                     
        try {
            
            // Set screen size
            setScreenSize();
            
            // Set language
            final ConfigurationStatus status = setLanguage(language);
            
            // If URL is fixed, stop processing
            if(!Configuration.get().isURLConfigEnabled()) {
                if(status == null) {
                    callback.onSuccess(ConfigurationStatus.CONFIGURED);
                } else {
                    callback.onSuccess(status);
                }                   
                return;
            }
            
            // Create possibles URL        
            String httpUrl = ConnectionHelper.appendHTTP(url);
            String httpsUrl = ConnectionHelper.appendHTTPs(url);  
            String restHttpUrl = httpUrl + "/rest/general";
            String restHttpsUrl = httpsUrl + "/rest/general";
            String fileHttpUrl = httpUrl + "/cyclosMobileRedirect.txt";
            String fileHttpsUrl = httpsUrl + "/cyclosMobileRedirect.txt";
           
            // Create connect actions for each URL
            List<ConnectAction> actions = new LinkedList<ConnectAction>(Arrays.asList(
                                                    new ConnectAction(httpUrl, restHttpUrl), 
                                                    new ConnectAction(httpsUrl, restHttpsUrl),
                                                    new ConnectAction(fileHttpUrl, true), 
                                                    new ConnectAction(fileHttpsUrl, true)));
            
            // Find a valid URL
            new ConnectRequest(actions) {
                @Override
                protected void onResponseFinished(String resultUrl) {                   
                    ConfigurationStatus currentStatus = ConfigurationStatus.INVALID_URL;
                    if(resultUrl != null) {             
                        // Set application URL with the URL given by user
                        Configuration.get().setServerAppUrl(url);
                        // Set server URL with the URL found
                        Configuration.get().setServerRootUrl(resultUrl);                        
                        // A valid URL has been found, check if a status was already set
                        if(status != ConfigurationStatus.RELOAD_APP) {
                            currentStatus = ConfigurationStatus.CONFIGURED;
                        } else {
                            currentStatus = status;
                        }
                    }
                    callback.onSuccess(currentStatus);               
                }           
            };            
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }    
    
    /**
     * Sets the application language
     */
    private ConfigurationStatus setLanguage(String language) {    
        String userLanguage = Configuration.get().getUserLanguage();
        
        // No user language has been set
        if(StringHelper.isEmpty(userLanguage)) {
            // Set default language
            if(StringHelper.isEmpty(language) || language.equals(userLanguage)) {
                Configuration.get().setUserLanguage(Configuration.get().getDefaultLanguage());
                // No application reload needed as the default language is already being displayed
                return null;
            }                     
        }                
        
        // Set the new language and reload application
        if(StringHelper.isNotEmpty(language) && !language.equals(userLanguage)) {    
            Configuration.get().setUserLanguage(language);
            return ConfigurationStatus.RELOAD_APP;
        }
        
        return null;
    }   
    
    /**
     * Sets the screen size
     */
    private void setScreenSize() {
        Storage storage = Storage.get();
        // Store higher and lower values of the screen at this moment
        // due to OS problems to get the screen size when application starts 
        String high = storage.getItem("high");
        String low = storage.getItem("low");                                   
        if(StringHelper.isEmpty(high) && StringHelper.isEmpty(low)) {                 
            int width = ScreenHelper.getScreenWidth();
            int height = ScreenHelper.getScreenHeight();                
            if(width > height) {
                storage.setItem("high", String.valueOf(width));
                storage.setItem("low", String.valueOf(height));                 
            } else {
                storage.setItem("high", String.valueOf(height));
                storage.setItem("low", String.valueOf(width));              
            }                                                                         
        }        
    }
    
}
