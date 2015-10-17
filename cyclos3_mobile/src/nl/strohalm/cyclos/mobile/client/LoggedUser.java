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

import nl.strohalm.cyclos.mobile.client.model.InitialData;
import nl.strohalm.cyclos.mobile.client.services.exceptions.SessionExpiredException;
import nl.strohalm.cyclos.mobile.client.utils.JsonHelper;
import nl.strohalm.cyclos.mobile.client.utils.RestRequest;
import nl.strohalm.cyclos.mobile.client.utils.Storage;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.user.client.Timer;

/**
 * Holds data for the currently authenticated user.
 * 
 * @author luis
 */
public class LoggedUser {

    private static LoggedUser INSTANCE;    

    /**
     * Returns the {@link LoggedUser} instance
     */
    public static LoggedUser get() {
        if (INSTANCE == null) {
            INSTANCE = new LoggedUser();            
        }
        return INSTANCE;
    }

    private String      principal;
    private String      password;
    private Boolean     isLoggedIn;
    private InitialData initialData;
    private Timer       sessionTimeout;
    private boolean     sessionExpired = false;
    
    /**
     * Returns initial data fetched on login
     */
    public InitialData getInitialData() {
        if(initialData == null) {
            initialData = (InitialData) JsonHelper.parseObject(Storage.get().getItem("initialData"));            
        }
        return initialData;
    }
    
    /**
     * Returns the user id
     */
    public Long getUserId() {
        return initialData.getProfile().getId();
    }

    /**
     * Initializes the data on this instance
     */
    public void initialize(String principal, String password, InitialData initialData) {
        this.principal = principal;
        this.password = password;
        this.initialData = initialData;
        this.sessionExpired = false;
        setLoggedIn(true);
        storageLocalData();
        initSessionTimeout();
    }
    
    /**
     * Destroy user's session
     */
    public void destroy() {
        this.principal = null;
        this.password = null;
        this.initialData = null;
        this.sessionExpired = false;
        setLoggedIn(false);
    }
    
    /**
     * Storages user data in local storage
     */
    private void storageLocalData() {                      
        Storage.get().setItem("principal", principal);       
        Storage.get().setItem("initialData", JsonHelper.stringify(initialData));    
    }       
    
    /**
     * Returns session's principal    
     */
    public String getPrincipal() {               
        principal = Storage.get().getItem("principal");        
        return principal;        
    }       
    
    /**
     * Returns in memory password     
     */
    private String getPassword() {            
        return password;        
    }

    /**
     * Returns whether there is an user currently logged in
     */
    public boolean isLoggedIn() {        
        isLoggedIn =  StringHelper.isNotEmpty(Storage.get().getItem("loggedIn"));                        
        return isLoggedIn && StringHelper.isNotEmpty(principal) && StringHelper.isNotEmpty(password);       
    }
    
    /**
     * Sets user logged in / out  
     */
    private void setLoggedIn(boolean loggedIn) {
        Storage storage = Storage.get();
        isLoggedIn = loggedIn;        
        if(isLoggedIn) {
            storage.setItem("loggedIn", "1");
            storage.setItem("principal", principal);;
        } else {
            storage.removeItems("loggedIn", "initialData");
        }
    }

    /**
     * Sets the current user data on the given request<br>
     * If session has expired redirects user to login page
     */
    public void prepare(RestRequest<?> request) {
        if(sessionExpired) {
            throw new SessionExpiredException();
        }
        request.setUsername(getPrincipal());
        request.setPassword(getPassword());
    }    
    
    /**
     * Initializes the session timeout for the logged user
     */
    public void initSessionTimeout() {
        if(sessionTimeout == null) {
            sessionTimeout = new Timer() {
                @Override
                public void run() {
                    destroy();
                    // Set session expired
                    sessionExpired = true;                    
                }              
            };
        }
        // If user is logged in, reset timeout, otherwise cancel it
        sessionTimeout.cancel();        
        if(isLoggedIn()) {
            sessionTimeout.schedule(Configuration.get().getSessionTimeout());
        }
    }
    
    /**
     * Returns if user session is expired
     */
    public boolean isSessionExpired() {
        return sessionExpired;
    }

}
