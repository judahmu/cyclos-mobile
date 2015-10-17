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
package nl.strohalm.cyclos.mobile.client.ui.widgets;

import nl.strohalm.cyclos.mobile.client.ui.Icon;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A component used to display a notification.
 */
public class NotificationDisplay extends Composite {

    public static enum Type {
        INFORMATION, WARNING, ERROR, SUCCESS
    }

    private Image icon;
    private HTML  message;

    @Override
    public void onAttach() {
        super.onAttach();
    }
    
    public NotificationDisplay(Type type, String messageText) {
        init(type, messageText);
    }
       
    /**
     * Returns message text or html   
     */
    public String getMessage() {
        return message.getHTML();
    }

    /**
     * Sets the displayed message text
     */
    public void setMessage(String text) {
        message.setHTML(text);
    }

    /**
     * Initializes notification display widget     
     */
    private void init(Type type, String messageText) {
        icon = Icon.valueOf("NOTIFICATION_" + type.name()).image();
        message = new HTML();

        FlowPanel container = new FlowPanel();
        container.setStylePrimaryName("notification");
        container.addStyleDependentName(type.name().toLowerCase());
        
        SimplePanel iconContainer = new SimplePanel();
        iconContainer.setStyleName("icon-container");        
        iconContainer.setWidget(icon);
        
        SimplePanel textContainer = new SimplePanel();
        textContainer.setStyleName("text-container");   
        textContainer.setWidget(message);
                  
        container.add(iconContainer);
        container.add(textContainer);
        
        initWidget(container);

        setMessage(messageText);
    }

}