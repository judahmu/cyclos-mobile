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
package nl.strohalm.cyclos.mobile.client;

import nl.strohalm.cyclos.mobile.client.ui.widgets.NotificationDisplay;
import nl.strohalm.cyclos.mobile.client.ui.widgets.NotificationDisplay.Type;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.gwtphonegap.client.notification.AlertCallback;

/**
 * Display different notification types to the user.
 */
public class Notification {
    
    public enum NotificationLayout { NORMAL, FIXED }
    
    private static Notification INSTANCE;
            
    private Messages messages;
    private SimplePanel container;
    private boolean isVisible;
    private NotificationLayout layout = NotificationLayout.NORMAL; 
    
    public static Notification get() {
        if (INSTANCE == null) {
            INSTANCE = new Notification();
        }
        return INSTANCE;        
    }     
    
    private Notification() {
        messages = Messages.Accessor.get();
        container = new SimplePanel(DOM.getElementById("notificationContainer")) {
            @Override
            public void add(IsWidget child) {               
                super.add(child);
                onAttach();
            }            
        };
        container.addAttachHandler(new AttachEvent.Handler() {            
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                CyclosMobile.get().getMainLayout().adjust();
            }
        });
    }  
    
    /**
     * Changes the notification layout until {@link #resetLayout()} is called again
     */
    public void setLayout(NotificationLayout layout) {
        this.layout = layout;
    }
    
    /**
     * Returns the current notification layout
     */
    public NotificationLayout getLayout() {
        return layout;
    }
    
    /**
     * Resets the layout to the normal one
     */
    public void resetLayout() {     
        layout = NotificationLayout.NORMAL;
    }
    
    /**
     * Returns if notification is visible     
     */
    public boolean isVisible() {
        return isVisible;
    }
    
    /**
     * Hides any visible notification
     */
    public void hide() {
        isVisible = false;
        container.clear();
        container.setVisible(false);
    }
    
    /**
     * Shows an error message  
     */
    public void error(String message) {
        show(Type.ERROR, message);
    }
    
    /**
     * Shows an information message   
     */
    public void information(String message) {
        show(Type.INFORMATION, message);
    }
    
    /**
     * Shows a warning message     
     */
    public void warning(String message) {
        show(Type.WARNING, message);
    }
    
    /**
     * Shows a successful message     
     */
    public void success(String message) {
        show(Type.SUCCESS, message);
    }
    
    /**
     * Creates a NotificationDisplay and configures the notification     
     */
    private void show(Type type, String message) {
        isVisible = true;
        NotificationDisplay display = new NotificationDisplay(type, message);
        if(layout == NotificationLayout.FIXED) {
            display.addStyleDependentName("fixed");
            CyclosMobile.get().getMainLayout().addNotificationContainerStyle("fixed");
            CyclosMobile.get().getMainLayout().addPageContainerStyle("fixed");
        }
        configureNotification(display);
    }
    
    /**
     * Displays notification widget     
     */
    private void configureNotification(NotificationDisplay display) {
        container.setWidget(display);
        display.onAttach();
        container.setVisible(true);                        
    }  
    
    /**
     * Displays a browser's alert
     */
    public void alert(String message) {
        alert(message, messages.error(), messages.close(), new AlertCallback() {
            @Override
            public void onOkButtonClicked() {
            }            
        });
    }
    
    public void alert(String message, String title, String button, AlertCallback callback) {
        CyclosMobile.get().getPhoneGap().getNotification().alert(message, callback, title, button);
    }

}
