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
package nl.strohalm.cyclos.mobile.client.utils;

/**
 * Base64 encoding and decoding.
 * @see http://peyotest.blogspot.com.br/2010/06/gwt-native-base64.html
 * 
 * @author luis
 */
public class Base64 {

    public static native String decode(String data) /*-{
        function decode_base64(data) {
            var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
            var out = "", c1, c2, c3, e1, e2, e3, e4;
            for ( var i = 0; i < data.length;) {
                e1 = tab.indexOf(data.charAt(i++));
                e2 = tab.indexOf(data.charAt(i++));
                e3 = tab.indexOf(data.charAt(i++));
                e4 = tab.indexOf(data.charAt(i++));
                c1 = (e1 << 2) + (e2 >> 4);
                c2 = ((e2 & 15) << 4) + (e3 >> 2);
                c3 = ((e3 & 3) << 6) + e4;
                out += String.fromCharCode(c1);
                if (e3 != 64) {
                    out += String.fromCharCode(c2);
                }
                if (e4 != 64) {
                    out += String.fromCharCode(c3);
                }
            }
            return out;
        }
        return decode_base64(data);
    }-*/;

    public static native String encode(String data) /*-{
        function encode_base64(data) {
            var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
            var out = "", c1, c2, c3, e1, e2, e3, e4;
            for ( var i = 0; i < data.length;) {
                c1 = data.charCodeAt(i++);
                c2 = data.charCodeAt(i++);
                c3 = data.charCodeAt(i++);
                e1 = c1 >> 2;
                e2 = ((c1 & 3) << 4) + (c2 >> 4);
                e3 = ((c2 & 15) << 2) + (c3 >> 6);
                e4 = c3 & 63;
                if (isNaN(c2)) {
                    e3 = e4 = 64;
                } else if (isNaN(c3)) {
                    e4 = 64;
                }
                out += tab.charAt(e1) + tab.charAt(e2) + tab.charAt(e3) + tab.charAt(e4);
            }
            return out;
        }
        return encode_base64(data);
    }-*/;
}
