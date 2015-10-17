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

import nl.strohalm.cyclos.mobile.client.Messages;

/**
 * Formats values in order to be displayed in UI
 */
public class UIValueFormatter {

    private static UIValueFormatter instance;
    private static Messages messages;
    
    /**
     * Formats the given string value
     */
    public String format(String value) {
        if(StringHelper.isNotEmpty(value)) {
           value = checkBoolean(value);
        }
        return value;
    }
    
    /**
     * Checks if the value is boolean type and returns 
     * the according value translation 
     */
    private String checkBoolean(String value) {
        if(value.equalsIgnoreCase("true")) {
            return messages.yes();
        } else if(value.equalsIgnoreCase("false")) {
            return messages.no();
        }
        return value;
    }
    
    public static UIValueFormatter get() {
        if(instance == null) {
            instance = new UIValueFormatter();
            messages = Messages.Accessor.get();
        }
        return instance;
    }
}
