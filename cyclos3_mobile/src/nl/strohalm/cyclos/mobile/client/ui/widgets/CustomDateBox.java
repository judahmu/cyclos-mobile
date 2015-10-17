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

import java.util.Date;

import nl.strohalm.cyclos.mobile.client.exceptions.DateFormatException;
import nl.strohalm.cyclos.mobile.client.utils.DateFormat;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;
import nl.strohalm.cyclos.mobile.client.utils.UIHelper;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * A text box that allows to type a date in it, based on {@link DateBox}.<br> 
 * Also can display a {@link DatePicker} 
 */
public class CustomDateBox extends DateBox {
    
    public CustomDateBox() {
        super(new DatePicker(), null, DateFormat.get());
        setStyleName("dateBox-input-field");
        
        // Center date picker if it is visible
        Window.addResizeHandler(new ResizeHandler() {                   
            @Override
            public void onResize(ResizeEvent event) {
                PopupPanel popup = (PopupPanel) getDatePicker().getParent();
                if(popup.isShowing()) {
                    popup.center();
                }
            }
        });  
        
        // Set date format as place holder
        UIHelper.setPlaceHolder(DateFormat.get().getDateTimeFormat().getPattern(), getTextBox().getElement());            
    }
    
    @Override
    public Date getValue() {
        // Validate date format given 
        String text = getTextBox().getText().trim();
        parse(text);
        return super.getValue();
    }

    /**
     * Parses the given date and raise an 
     * exception if it is an invalid format
     */
    private void parse(String dateText) {
        if(StringHelper.isNotEmpty(dateText)) {
            try {
                ((DefaultFormat)getFormat()).getDateTimeFormat().parse(dateText);                
            } catch (Exception exception) {
                throw new DateFormatException(DateFormat.get().getDateTimeFormat().getPattern());
            }
        }        
    }
   
    /**
     * This method does nothing, to display the date picker  
     * call {@link #display()}
     */
    @Override
    public void showDatePicker() {       
    }  
    
    /**
     * Parses the current date box's value and shows that date in the date picker
     */
    public void display() {
        String text = getTextBox().getText().trim();
        Date current = getFormat().parse(this, text, false);
        if (current == null) {
          current = new Date();
        }
        getDatePicker().setCurrentMonth(current);
        PopupPanel popup = (PopupPanel) getDatePicker().getParent(); 
        popup.setGlassStyleName("mask");
        popup.setGlassEnabled(true);
        popup.center();
    }      
}
