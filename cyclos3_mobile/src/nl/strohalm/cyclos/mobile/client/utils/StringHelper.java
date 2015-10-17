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

/**
 * Helper class for strings
 */
public class StringHelper {

    /**
     * Use the object's toString() method and checks whether it's empty or null
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        String string = object.toString();
        return string == null || string.trim().length() == 0;
    }

    /**
     * Use the object's toString() method and checks whether it's not empty or not null
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
    
    /**
     * Returns a string where the first letter is upper case and all others are lower case
     */
    public static String capitalize(String string) {
        if (string == null) {
            return null;
        }
        int length = string.length();
        if (length == 0) {
            return "";
        } else if (length == 1) {
            return string.toUpperCase();
        } else {
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        }
    }
    
    /**
     * Inserts characters to the given string into the given position
     */
    public static String insertCharsAt(int position, String characters, String text) {
        StringBuilder sb = new StringBuilder(text);
        return sb.insert(position, characters).toString();
    }
    
}
