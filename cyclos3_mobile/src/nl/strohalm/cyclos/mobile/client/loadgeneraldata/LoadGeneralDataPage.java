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
package nl.strohalm.cyclos.mobile.client.loadgeneraldata;

import nl.strohalm.cyclos.mobile.client.Configuration;
import nl.strohalm.cyclos.mobile.client.CyclosMobile;
import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.model.AppState;
import nl.strohalm.cyclos.mobile.client.model.GeneralData;
import nl.strohalm.cyclos.mobile.client.model.Image;
import nl.strohalm.cyclos.mobile.client.services.GeneralService;
import nl.strohalm.cyclos.mobile.client.ui.Page;
import nl.strohalm.cyclos.mobile.client.ui.panels.SquarePanel;
import nl.strohalm.cyclos.mobile.client.utils.BaseAsyncCallback;
import nl.strohalm.cyclos.mobile.client.utils.Storage;
import nl.strohalm.cyclos.mobile.client.utils.ImageHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link Page} which represents the very initial state of the application.
 * 
 * @author luis
 */
public class LoadGeneralDataPage extends Page {

    private GeneralService generalService = GWT.create(GeneralService.class);          
    private SquarePanel container;

    @Override
    public String getHeading() {
        return messages.loadGeneralDataHeading();
    }

    @Override
    public boolean isDisplayMenu() {
        return false;
    }
    
    @Override
    public AppState getMinimumAppState() {
        return AppState.SERVER_DATA_NOT_LOADED;
    }

    @Override
    public Widget initialize() {
                     
        container = new SquarePanel();              
        container.add(new HTML(messages.loadGeneralDataMessage()));                

        getGeneralData();          
          
        return container;
    }
    
    /**
     * Returns application's general data
     */
    private void getGeneralData() {
        generalService.getGeneralData(new BaseAsyncCallback<GeneralData>() {
            
            @Override
            public void onFailure(Throwable caught) {
                container.setVisible(false);
                super.onFailure(caught);
            }
            
            @Override
            public void onSuccess(GeneralData generalData) {
                CyclosMobile.get().setGeneralData(generalData);
                
                // If the functionality is enabled, save splash screen 
                // in the local storage and preload it for the next login 
                if(Configuration.get().isSplashScreenEnabled()) {                   
                    saveSplashScreen("splash", ImageHelper.getSplashScreen());
                }
                Navigation.get().loadInitialPage();
            }
        });
    }
    
    /**
     * Saves the given splash screen in the local storage
     * and preloads the image for the next login
     */
    private void saveSplashScreen(String key, Image splash) {
        // Storage data                     
        if(splash != null) {
            String src = splash.getFullUrl() + "&" + splash.getLastModified().toString();                        
            Storage.get().setItem(key, src);   
            ImageHelper.preloadImage(src);
        }        
    }

}
