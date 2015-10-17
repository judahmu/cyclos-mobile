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

import java.math.BigDecimal;

import nl.strohalm.cyclos.mobile.client.exceptions.PositiveNumberException;
import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

/**
 * A number field which allows decimal precision
 */
public class NumberField extends Composite {

    private FlowPanel container;
    
    private HTML  numberWrapper;
    private HTML  decimalWrapper;
    private Label decimalSeparator;       
    
    private BigDecimal value;
    private int decimals;
    private String separator;
    
    private boolean onlyPositive = false;
    
    public String getValue() {
        String numberStr = getWrapperValue(numberWrapper.getElement().getChild(0));
        String value = numberStr;
        
        if(StringHelper.isEmpty(value)) {
            return "";
        }
        
        if(decimals > 0) {
            String decimalStr = getWrapperValue(decimalWrapper.getElement().getChild(0));        
            if(StringHelper.isEmpty(decimalStr)) {                
                decimalStr = buildDecimals(0);
            }
            value = numberStr + "." + decimalStr;
            // Parse float result checking for non numbers input
            float val = Float.parseFloat(value);
            checkPositive(val);
        } else {
            // Parse int result checking for non numbers input
            int val = Integer.parseInt(value);
            checkPositive(val);
        }                       
        
        return value;
    }
    
    public NumberField() {
        this(0, null, "");
    }
    
    public NumberField(int decimals, String separator) {
        this(decimals, separator, "");
    }
      
    public NumberField(int decimals, String separator, String placeholder) {
        this.decimals = decimals;
        this.separator = separator;
        
        container = new FlowPanel();
        container.setStyleName("number-field");
        
        numberWrapper = new HTML("<input class=\"form-input-field\" type=\"number\" onblur=\"sanitizeNumber(this)\" min=\"0\" placeholder=\""+placeholder+"\" />");
        numberWrapper.setStyleName("number-field-number-wrapper");
        
        container.add(numberWrapper);
        
        if(this.decimals > 0) {
            decimalSeparator = new Label(this.separator);
            decimalSeparator.setStyleName("number-field-decimal-separator");
    
            String max = buildDecimals(9);
            String zeros = buildDecimals(0);
            
            decimalWrapper = new HTML("<input class=\"form-input-field\" type=\"number\"/ min=\"0\" max=\"" + max.toString() + "\" placeholder=\""+zeros+"\" onblur=\"truncate(this,"+decimals+")\"/>");
            decimalWrapper.setStyleName("number-field-decimal-wrapper");
            
            container.add(decimalSeparator);
            container.add(decimalWrapper);
        }
        
        initWidget(container);
    }
    
    /**
     * Sets if the the number is only positive
     */
    public void setOnlyPositive(boolean onlyPositive) {
        this.onlyPositive = onlyPositive;
    }
    
    /**
     * Sets the field place holder property
     */
    public void setPlaceHolder(String text) {
        Element el = (Element)numberWrapper.getElement().getChild(0);
        el.setAttribute("placeholder", text);
    }
    
    /**
     * Returns a string with the given number as character repeated by the numbers of decimals set.<br>
     */
    private String buildDecimals(int number) {
        StringBuilder max = new StringBuilder();
        for(int i = 0; i < this.decimals; i++) {
            max.append(number);
        }
        return max.toString();
    }
    
    /**
     * Checks if the given value muste be positive
     */
    private void checkPositive(float value) {
        if(onlyPositive) {
            if(value <= 0) {
                throw new PositiveNumberException();
            }
        }
    }
    
    /**
     * Returns the element's value
     */
    private final native String getWrapperValue(JavaScriptObject elem) /*-{
        return elem.value;
    }-*/;

    /**
     * Sets the element's value 
     */
    private final native String setWrapperValue(JavaScriptObject elem, String value) /*-{
        elem.value = value;
    }-*/;

}
