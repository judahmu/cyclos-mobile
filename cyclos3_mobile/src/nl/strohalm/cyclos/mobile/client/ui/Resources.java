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
package nl.strohalm.cyclos.mobile.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Defines all application resources including the images which will be combined into a single optimized image by client bundle generator.
 */
public interface Resources extends ClientBundle {

    Resources INSTANCE = GWT.create(Resources.class);

    @Source("icons/accounts.png")
    ImageResource accounts();
    
    @Source("icons/hdpi/accounts.png")
    ImageResource accountsHdpi();
    
    @Source("icons/xhdpi/accounts.png")
    ImageResource accountsXhdpi();
    
    @Source("icons/contact.png")
    ImageResource contact();
    
    @Source("icons/hdpi/contact.png")
    ImageResource contactHdpi();
    
    @Source("icons/xhdpi/contact.png")
    ImageResource contactXhdpi();
    
    @Source("icons/contacts.png")
    ImageResource contacts();
    
    @Source("icons/hdpi/contacts.png")
    ImageResource contactsHdpi();          
    
    @Source("icons/xhdpi/contacts.png")
    ImageResource contactsXhdpi();
    
    @Source("icons/contact_select.png")
    ImageResource contactSelect();
    
    @Source("icons/hdpi/contact_select.png")
    ImageResource contactSelectHdpi();
    
    @Source("icons/xhdpi/contact_select.png")
    ImageResource contactSelectXhdpi();
    
    @Source("icons/date.png")
    ImageResource date();
    
    @Source("icons/hdpi/date.png")
    ImageResource dateHdpi();
    
    @Source("icons/xhdpi/date.png")
    ImageResource dateXhdpi();
    
    @Source("icons/down.png")
    ImageResource down();
        
    @Source("icons/help.png")
    ImageResource help();
    
    @Source("icons/hdpi/help.png")
    ImageResource helpHdpi();
    
    @Source("icons/xhdpi/help.png")
    ImageResource helpXhdpi();
    
    @Source("icons/load.png")
    ImageResource load();
    
    @Source("icons/hdpi/load.png")
    ImageResource loadHdpi();
    
    @Source("icons/xhdpi/load.png")
    ImageResource loadXhdpi();
    
    @Source("icons/login.png")
    ImageResource login();
    
    @Source("icons/hdpi/login.png")
    ImageResource loginHdpi();
    
    @Source("icons/xhdpi/login.png")
    ImageResource loginXhpdi();
    
    @Source("icons/logout.png")
    ImageResource logout();
    
    @Source("icons/hdpi/logout.png")
    ImageResource logoutHdpi();
    
    @Source("icons/xhdpi/logout.png")
    ImageResource logoutXhdpi();
    
    @Source("icons/error.png")
    ImageResource notificationError();  
    
    @Source("icons/hdpi/error.png")
    ImageResource notificationErrorHdpi();
    
    @Source("icons/xhdpi/error.png")
    ImageResource notificationErrorXhdpi();
    
    @Source("icons/information.png")
    ImageResource notificationInformation();
    
    @Source("icons/hdpi/information.png")
    ImageResource notificationInformationHdpi();
    
    @Source("icons/xhdpi/information.png")
    ImageResource notificationInformationXdhpi();
    
    @Source("icons/success.png")
    ImageResource notificationSuccess();
    
    @Source("icons/hdpi/success.png")
    ImageResource notificationSuccessHdpi();
    
    @Source("icons/xhdpi/success.png")
    ImageResource notificationSuccessXhdpi();
    
    @Source("icons/warning.png")
    ImageResource notificationWarning();
    
    @Source("icons/hdpi/warning.png")
    ImageResource notificationWarningHdpi();
    
    @Source("icons/xhdpi/warning.png")
    ImageResource notificationWarningXhdpi();
    
    @Source("icons/payments.png")
    ImageResource payments();
    
    @Source("icons/hdpi/payments.png")
    ImageResource paymentsHdpi();
    
    @Source("icons/xhdpi/payments.png")
    ImageResource paymentsXhdpi();
    
    @Source("icons/process.png")
    ImageResource process();
    
    @Source("icons/hdpi/process.png")
    ImageResource processHdpi();
    
    @Source("icons/xhdpi/process.png")
    ImageResource processXhdpi();
    
    @Source("icons/settings.png")
    ImageResource settings();
    
    @Source("icons/hdpi/settings.png")
    ImageResource settingsHdpi();
    
    @Source("icons/xhdpi/settings.png")
    ImageResource settingsXhdpi();
    
    @Source("icons/system.png")
    ImageResource system();
    
    @Source("icons/hdpi/system.png")
    ImageResource systemHdpi();
    
    @Source("icons/xhdpi/system.png")
    ImageResource systemXhdpi();
    
    @Source("icons/up.png")
    ImageResource up();
    
    @Source("icons/user.png")
    ImageResource user();
    
    @Source("icons/hdpi/user.png")
    ImageResource userHdpi();
    
    @Source("icons/xhdpi/user.png")
    ImageResource userXhdpi();
    
    @Source("icons/user_search.png")
    ImageResource userSearch();
    
    @Source("icons/hdpi/user_search.png")
    ImageResource userSearchHdpi();
    
    @Source("icons/xhdpi/user_search.png")
    ImageResource userSearchXhdpi();
      
}

