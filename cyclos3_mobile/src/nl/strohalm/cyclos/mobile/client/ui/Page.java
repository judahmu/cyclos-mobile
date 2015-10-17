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
package nl.strohalm.cyclos.mobile.client.ui;

import java.util.List;

import nl.strohalm.cyclos.mobile.client.Messages;
import nl.strohalm.cyclos.mobile.client.model.AppState;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.utils.PageAction;

import com.google.gwt.user.client.ui.Widget;

/**
 * Contains the representation for a page within the application.
 * 
 * @author luis
 */
public abstract class Page {
    
    private PageAnchor pageAnchor;

    /**
     * Easy reference for the {@link Messages} implementation
     */
    protected Messages   messages = Messages.Accessor.get();
    protected Parameters parameters;

    /**
     * Returns the page heading
     */
    public abstract String getHeading();

    /**
     * Returns the minimum status this page requires to run
     */
    public AppState getMinimumAppState() {
        return AppState.READY;
    }
    
    /**
     * Returns if the menu button bar should be displayed for this page
     */
    public boolean isDisplayMenu() {
        return true;
    }
    
    /**
     * Returns if the page has custom scrolling. 
     * Otherwise it will be handled by main layout
     */
    public boolean hasCustomScroll() {
        return false;
    }

    /**
     * Returns a list of actions to be displayed on the bottom panel. If no actions are returned, the bottom panel is hidden.
     */
    public List<PageAction> getPageActions() {
        return null;
    }

    /**
     * Returns the page parameters
     */
    public Parameters getParameters() {
        return parameters;
    }

    /**
     * Initializes the page, returning the content widget
     */
    public abstract Widget initialize();

    /**
     * Sets the page parameters 
     */
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns the page anchor
     */
    public PageAnchor getPageAnchor() {
        return pageAnchor;
    }

    /**
     * Sets the page anchor
     */
    public void setPageAnchor(PageAnchor pageAnchor) {
        this.pageAnchor = pageAnchor;
    }

}
