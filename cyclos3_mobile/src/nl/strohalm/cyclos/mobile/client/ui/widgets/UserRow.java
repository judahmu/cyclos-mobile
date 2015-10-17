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

import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A user row widget used in data list. 
 */
public class UserRow extends Composite {

    protected FlowPanel container;
    protected FlowPanel rowContent;
    protected FlowPanel leftContainer;
    protected FlowPanel rightContainer;
    
    public UserRow() {
        container = new FlowPanel();
        container.setStyleName("row");
        
        rowContent = new FlowPanel();
        rowContent.setStyleName("row-content");
        
        leftContainer = new FlowPanel();
        leftContainer.addStyleName("row-left-column");
        
        rightContainer = new FlowPanel();
        rightContainer.addStyleName("row-left-column");
        
        rowContent.add(leftContainer);
        rowContent.add(rightContainer);
        
        container.add(rowContent);
        
        initWidget(container);
    }    
    
    /**
     * Sets left container style
     */
    public void setLeftStyle(String style) {
        if(StringHelper.isNotEmpty(style)) {
            leftContainer.addStyleName(style);
        }
    }    

    /**
     * Sets right container style
     */
    public void setRightStyle(String style) {
        if(StringHelper.isNotEmpty(style)) {
            rightContainer.addStyleName(style);
        }
    }
    
    /**
     * Sets the row heading
     */
    public void setHeading(String heading) {
        setHeading(heading, null);
    }
    
    /**
     * Sets the row heading adding a custom style
     */
    public void setHeading(String heading, String style) {
        Label headingLabel = new Label(heading);
        headingLabel.addStyleName("row-heading");
        if(StringHelper.isNotEmpty(style)) {
            headingLabel.addStyleName(style);
        }
        rightContainer.add(headingLabel);
    }
    
    /**
     * Sets the row sub-heading    
     */
    public void setSub(String description) {
        setSub(description, null);
    }
    
    /**
     * Sets the row sub-heading adding a custom style    
     */
    public void setSub(String description, String style) {
        Label descriptionLabel = new Label(description);
        descriptionLabel.addStyleName("row-sub");
        if(StringHelper.isNotEmpty(style)) {
            descriptionLabel.addStyleName(style);
        }
        rightContainer.add(descriptionLabel);
    }
  
    /**
     * Sets the row image on the left and sets a new  
     * container style to make the image fit correctly
     */
    public void setImage(Image image) {
        SimplePanel container = new SimplePanel();
        container.setStyleName("row-image");
        container.setWidget(image);
        leftContainer.add(container);   
        leftContainer.setStyleName("image-row-left-column");
    }
        
}
