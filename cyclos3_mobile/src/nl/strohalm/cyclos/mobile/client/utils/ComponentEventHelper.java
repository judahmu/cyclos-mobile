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

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * Helper for add predefined events to components
 */
public class ComponentEventHelper {
    
    /**
     * Adds a set of event listeners which will add or remove the given style to the given component on click and touch events
     */
    public static void addPressEvents(final Widget container, final String style) {
        addClickDown(container, style);
        addClickUp(container, style);
        addTouchStart(container, style);
        addTouchEnd(container, style);
    }

    /**
     * Adds the given style to the component on click down event
     */
    public static void addClickDown(final Widget container, final String style) {
        container.addDomHandler(new MouseDownHandler() {           
            @Override
            public void onMouseDown(MouseDownEvent event) {
                container.addStyleName(style);
            }
        }, MouseDownEvent.getType());    
    }
    
    /**
     * Removes the given style to the component on click up event
     */
    public static void addClickUp(final Widget container, final String style) {
        container.addDomHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                container.removeStyleName(style);
            }            
        }, MouseUpEvent.getType());
    }
        
    /**
     * Adds the given style to the component on touch start event<br>
     * Used in touch devices
     */
    public static void addTouchStart(final Widget container, final String downStyle) {
        container.addDomHandler(new TouchStartHandler() {           
            @Override
            public void onTouchStart(TouchStartEvent event) {
               container.addStyleName(downStyle);
            }        
        }, TouchStartEvent.getType());
    }
    
    /**
     * Removes the given style to the component on touch end event<br>
     * Used in touch devices
     */
    public static void addTouchEnd(final Widget container, final String downStyle) {
        container.addDomHandler(new TouchEndHandler() {           
            @Override
            public void onTouchEnd(TouchEndEvent event) {
               container.removeStyleName(downStyle);
            }        
        }, TouchEndEvent.getType());
    }
    
    /**
     * Adds scroll events to widgets. 
     */
    public static void addScrollEvents(Widget scrollUp, Widget scrollDown, final String scrollableId) {
        scrollUp.addDomHandler(new MouseDownHandler() {           
            @Override
            public void onMouseDown(MouseDownEvent event) {
                startScrollUp(scrollableId);
                event.preventDefault();
            }
        }, MouseDownEvent.getType());
        scrollUp.addDomHandler(new MouseUpHandler() {            
            @Override
            public void onMouseUp(MouseUpEvent event) {
                stopScroll();
                event.preventDefault();
            }
        }, MouseUpEvent.getType());
        scrollDown.addDomHandler(new MouseDownHandler() {
           @Override
            public void onMouseDown(MouseDownEvent event) {
               startScrollDown(scrollableId);
               event.preventDefault();
            } 
        }, MouseDownEvent.getType());
        scrollDown.addDomHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                stopScroll();
                event.preventDefault();
            }            
        }, MouseUpEvent.getType());
    }
    
    /**
     * Scrolls the given element up
     */
    private static native void startScrollUp(String elementId) /*-{
        $wnd.scrollUp(elementId);
    }-*/;
    
    /**
     * Stops the current scroll
     */
    private static native void stopScroll() /*-{
        $wnd.stopScroll();
    }-*/;
    
    /**
     * Scrolls the given element down
     */
    private static native void startScrollDown(String elementId) /*-{
        $wnd.scrollDown(elementId);
    }-*/;
     
}
