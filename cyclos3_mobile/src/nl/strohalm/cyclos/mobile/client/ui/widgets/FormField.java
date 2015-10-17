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

import java.util.Map;

import nl.strohalm.cyclos.mobile.client.utils.StringHelper;
import nl.strohalm.cyclos.mobile.client.utils.UIValueFormatter;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

/**
 * A table based form which display labels and values as label fields.
 */
public class FormField extends Composite {
    
    private Grid grid;
    private String labelStyle;
    private String valueStyle;
    
    private boolean hideParentOnNoData = false;
    
    public FormField() {
        grid = new Grid(0, 2); 
        grid.setStyleName("form-field");
        
        labelStyle = "form-label";
        valueStyle = "form-value";
        
        initWidget(grid);
    }   
    
    /**
     * Adds form field style
     */
    public void addFormFieldStyle(String style) {
        grid.addStyleName(style);
    }
    
    /**
     * Sets the labels style
     */
    public void setLabelStyle(String style) {
        this.labelStyle = style;
    }
    
    /**
     * Sets the values style 
     */
    public void setValueStyle(String style) {
        this.valueStyle = style;
    }      
    
    /**
     * Hides the form's parent when no data is available to display
     */
    public void setHideParentOnNoData(boolean hide) {
        this.hideParentOnNoData = hide;
    }
    
    /**
     * Sets the form widgets and labels
     */
    public void setWidgets(Map<String, Widget> widgets) {
        int row = 0;
        for(String label : widgets.keySet()) {
            Widget widget = widgets.get(label);
            
            // Add style to the widget to fit in form
            widget.addStyleName(valueStyle);
            
            // Resize grid row per new value added
            grid.resizeRows(row + 1);
                
            // Add label and value
            LabelField labelField = new LabelField(label, labelStyle);            
            grid.setWidget(row, 0, labelField);
            grid.setWidget(row, 1, widget);  
              
            // Increment rows size
            row++;                            
        }
        // No data available
        if(row == 0) {
            if(hideParentOnNoData && getParent() != null) {
                getParent().setVisible(false);
            }
        }
    }
    
    /**
     * Sets the form data, empty values will not be rendered
     */
    public void setData(Map<String, String> data) {
        int row = 0;
        for(String label : data.keySet()) {
            String value = data.get(label);
            if(StringHelper.isNotEmpty(value) && value.trim().length() > 0) {  
                
                // Get formatted value
                value = UIValueFormatter.get().format(value);
                
                // Resize grid row per new value added
                grid.resizeRows(row + 1);
                
                // Add label and value
                LabelField labelField = new LabelField(label, labelStyle);
                HTMLField valueField = new HTMLField(value, valueStyle);
                grid.setWidget(row, 0, labelField);
                grid.setWidget(row, 1, valueField);  
                
                // Increment rows size
                row++;                
            }
        }
        // No data available
        if(row == 0) {
            if(hideParentOnNoData && getParent() != null) {
                getParent().setVisible(false);
            }
        }
    }

}
