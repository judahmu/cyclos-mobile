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

import java.util.Arrays;

import nl.strohalm.cyclos.mobile.client.Messages;
import nl.strohalm.cyclos.mobile.client.ui.panels.BottomPanel;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * A Dialog box which prompts custom messages and displays yes/no actions
 */
public class PromptDialog extends DialogBox {
    
    protected static Messages messages;
    
    static {
        messages = Messages.Accessor.get();
    }
    
    public PromptDialog(String text) {
        this(null, text);
    }
    
    public PromptDialog(String title, String text) {        
        super();                 
        
        setStyleName("popup");
        setGlassStyleName("mask");
        setGlassEnabled(true);                    
        
        if(StringHelper.isNotEmpty(title)) {
            setText(title);
        }
        
        FlowPanel container = new FlowPanel();
        
        Label confirmationText = new Label(text);
        confirmationText.setStyleName("prompt-text");

        BottomPanel bottom = new BottomPanel();
        bottom.setActionButtons(Arrays.asList(getNoAction(), getYesAction()));
        
        container.add(confirmationText);
        container.add(bottom);
        
        add(container);  
        
        // Adjust prompt dialog
        Window.addResizeHandler(new ResizeHandler() {                   
            @Override
            public void onResize(ResizeEvent event) {
                if(isShowing()) {
                    center();
                }
            }
        });
    }
    
    /**
     * Returns 'yes' button action     
     */
    private PageAction getYesAction() {
        return new PageAction() {        
            @Override
            public void run() {              
                // Execute custom logic
                onYesActionPressed();                               
               
                // Hide dialog
                hide();
            }            
            @Override
            public String getLabel() {                
                return messages.yes();
            }
        };
    }
    
    /**
     * Returns 'no' button action
     */
    private PageAction getNoAction() {
        return new PageAction() {
            @Override
            public void run() {           
                // Execute custom logic
                onNoActionPressed();
                
                // Hides confirmation dialog
                hide();
            }
            @Override
            public String getLabel() {               
                return messages.no();
            }           
        };
    }
    
    /**
     * Displays the dialog centered
     */
    public void display() {
        center();
        show();
    }
    
    
    /**
     * Override to execute custom logic when user clicks on 'no' action button
     */
    protected void onNoActionPressed() {        
    }
    
    /**
     * Override to execute custom logic when user clicks on 'yes' action button
     */
    protected void onYesActionPressed() {        
    }
          
}

