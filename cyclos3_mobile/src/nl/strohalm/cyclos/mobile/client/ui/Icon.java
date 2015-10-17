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

import nl.strohalm.cyclos.mobile.client.utils.ScreenHelper;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

/**
 * Contains the possible icons in Cyclos Mobile.
 */
public enum Icon {
    
    ACCOUNTS {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.accountsHdpi();
                case XHDPI:
                    return Resources.INSTANCE.accountsXhdpi();
            }
            return Resources.INSTANCE.accounts();
        }
    },
    
    CONTACT {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.contactHdpi();
                case XHDPI:
                    return Resources.INSTANCE.contactXhdpi();
            }
            return Resources.INSTANCE.contact();
        }
    },
    
    CONTACTS {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.contactsHdpi();
                case XHDPI:
                    return Resources.INSTANCE.contactsXhdpi();
            }
            return Resources.INSTANCE.contacts();
        }
    },
    
    CONTACT_SELECT {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.contactSelectHdpi();
                case XHDPI:
                    return Resources.INSTANCE.contactSelectXhdpi();
            }
            return Resources.INSTANCE.contactSelect();
        }
    },
    
    DATE {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.dateHdpi();
                case XHDPI:
                    return Resources.INSTANCE.dateXhdpi();
            }
            return Resources.INSTANCE.date();
        }
    },
    
    DOWN {
        @Override
        protected ImageResource imageResource() {
            return Resources.INSTANCE.down();
        }
    },       
    
    HELP {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.helpHdpi();
                case XHDPI:
                    return Resources.INSTANCE.helpXhdpi();
            }
            return Resources.INSTANCE.help();
        }
    },
   
    LOAD {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.loadHdpi();
                case XHDPI:
                    return Resources.INSTANCE.loadXhdpi();
            }
            return Resources.INSTANCE.load();
        }
    },
    
    LOGIN {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.loginHdpi();
                case XHDPI:
                    return Resources.INSTANCE.loginXhpdi();
            }
            return Resources.INSTANCE.login();
        }
    },
    
    LOGOUT {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.logoutHdpi();
                case XHDPI:
                    return Resources.INSTANCE.logoutXhdpi();                
            }
            return Resources.INSTANCE.logout();
        }
    },
    
    NOTIFICATION_ERROR {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.notificationErrorHdpi();
                case XHDPI:
                    return Resources.INSTANCE.notificationErrorXhdpi();
            }
            return Resources.INSTANCE.notificationError();
        }
    },
    
    NOTIFICATION_INFORMATION {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.notificationInformationHdpi();
                case XHDPI:
                    return Resources.INSTANCE.notificationInformationXdhpi();
            }
            return Resources.INSTANCE.notificationInformation();
        }
    },
    
    NOTIFICATION_SUCCESS {
        @Override
        protected ImageResource imageResource( ) {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.notificationSuccessHdpi();
                case XHDPI:
                    return Resources.INSTANCE.notificationSuccessXhdpi();
            }
            return Resources.INSTANCE.notificationSuccess();
        }
    },
    
    NOTIFICATION_WARNING {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.notificationWarningHdpi();
                case XHDPI:
                    return Resources.INSTANCE.notificationWarningXhdpi();
            }
            return Resources.INSTANCE.notificationWarning();
        }
    },
    
    PAYMENTS {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.paymentsHdpi();
                case XHDPI:
                    return Resources.INSTANCE.paymentsXhdpi();
            }
            return Resources.INSTANCE.payments();
        }
    },
    
    PROCESS {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.processHdpi();
                case XHDPI:
                    return Resources.INSTANCE.processXhdpi();
            }
            return Resources.INSTANCE.process();
        }
    },
  
    SETTINGS {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.settingsHdpi();
                case XHDPI:
                    return Resources.INSTANCE.settingsXhdpi();
            }
            return Resources.INSTANCE.settings();
        }      
    },
    
    SYSTEM {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.systemHdpi();
                case XHDPI:
                    return Resources.INSTANCE.systemXhdpi();
            }
            return Resources.INSTANCE.system();
        }
    },
    
    UP {
        @Override
        protected ImageResource imageResource() {
            return Resources.INSTANCE.up();
        }
    },
    
    USER {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.userHdpi();
                case XHDPI:
                    return Resources.INSTANCE.userXhdpi();
            }
            return Resources.INSTANCE.user();
        }
    },
    
    USER_SEARCH {
        @Override
        protected ImageResource imageResource() {
            switch(ScreenHelper.getScreenDensity()) {
                case HDPI:
                    return Resources.INSTANCE.userSearchHdpi();
                case XHDPI:
                    return Resources.INSTANCE.userSearchXhdpi();
            }
            return Resources.INSTANCE.userSearch();
        }
    };
    
    public Image image() {
        return new Image(imageResource());
    }

    protected abstract ImageResource imageResource();
}
