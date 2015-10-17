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

import nl.strohalm.cyclos.mobile.client.Messages;
import nl.strohalm.cyclos.mobile.client.ui.Icon;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * A modal popup with a loading progress bar.
 */
public class LoadingPopup extends PopupPanel {
    
    public LoadingPopup() {        
       
        setStyleName("popup");
        setGlassStyleName("mask");
        setGlassEnabled(true);  

        FlowPanel container = new FlowPanel();
        
        Spinner spinner = new Spinner(Icon.PROCESS.image(), "loading-process");
        spinner.setCustomRotation(12, 30, 80);
        spinner.startSpinner();
        
        Label loadingText = new Label(Messages.Accessor.get().loadingConfiguration());
        loadingText.addStyleName("loading-text");              
        
        container.add(spinner);
        container.add(loadingText);   
        
        // Adjust prompt dialog
        Window.addResizeHandler(new ResizeHandler() {                   
            Timer resizeTimer = new Timer() {  
                @Override
                public void run() {
                    if(isShowing()) {
                        center();
                    }
                }
            };
            @Override
            public void onResize(ResizeEvent event) {
                resizeTimer.schedule(100);
            }
        });
        
        add(container);                       
    }
    
    /**
     * Displays the popup centered
     */
    public void display() {
        center();
    }
        
}
