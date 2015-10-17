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

import java.util.Set;

import nl.strohalm.cyclos.mobile.client.Navigation;
import nl.strohalm.cyclos.mobile.client.Notification;
import nl.strohalm.cyclos.mobile.client.exceptions.DateFormatException;
import nl.strohalm.cyclos.mobile.client.exceptions.PositiveNumberException;
import nl.strohalm.cyclos.mobile.client.model.CustomValueException;
import nl.strohalm.cyclos.mobile.client.services.exceptions.RestServiceException;
import nl.strohalm.cyclos.mobile.client.services.exceptions.SessionExpiredException;
import nl.strohalm.cyclos.mobile.client.ui.PageAnchor;

import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.rpc.StatusCodeException;

/**
 * Handles exceptions and displays it accordingly
 */
public class ErrorHandler {

    /**
     * Handles the given exception and displays the according error type
     */
    public static void handle(Throwable e) {   

        if(e instanceof UmbrellaException) {
            Set<Throwable> causes = ((UmbrellaException)e).getCauses();
            if (!causes.isEmpty()) {               
                handle(causes.iterator().next());
            }
        } else if(e instanceof SessionExpiredException) { 
            Navigation.get().go(PageAnchor.LOGIN);
        } else if(e instanceof CustomValueException) {
            CustomValueException ex = (CustomValueException) e;
            Notification.get().error(ExceptionHelper.resolveCustomValueMessage(ex.getCustomField(), ex.getValidationType()));
        } else if(e instanceof StatusCodeException) {
            StatusCodeException ex = (StatusCodeException)e;
            String message = ExceptionHelper.resolveStatusCodeMessage(ex.getStatusCode());
            if(ex.getStatusCode() == 0 || ex.getStatusCode() == 404) {
                Notification.get().alert(message);
            } else {
                Notification.get().error(message);
            }            
        } else if(e instanceof RequestTimeoutException) {
            Notification.get().error(ExceptionHelper.resolveRequestTimeoutMessage());
        } else if(e instanceof RestServiceException) {
            RestServiceException ex = (RestServiceException) e;
            Notification.get().error(ExceptionHelper.resolveRestServiceMessage(ex.getType()));
        } else if(e instanceof NumberFormatException) {
            Notification.get().error(ExceptionHelper.resolveNumberFormatMessage());
        } else if(e instanceof IllegalArgumentException) {
            Notification.get().error(ExceptionHelper.resolveIllegalArgumentMessage(e.getMessage()));
        } else if(e instanceof PositiveNumberException) {
            Notification.get().error(ExceptionHelper.resolvePositiveNumberMessage());
        } else if(e instanceof DateFormatException) {
            String pattern = ((DateFormatException) e).getDatePattern();
            Notification.get().error(ExceptionHelper.resolveDateFormatMessage(pattern));    
        } else {              
            Notification.get().error(ExceptionHelper.resolveGeneralMessage(e.getLocalizedMessage()));
        }               
    }
    
    /**
     * Debugs the given message into the console
     */
    public static native void debug(String message) /*-{
        $wnd.debug(message);
    }-*/;
}
