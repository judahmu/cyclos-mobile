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

import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A simple selection field which can handle any object type
 */
public abstract class SimpleSelectionField<T> extends Composite {

    private SimplePanel wrapper;
    private ListBox selection;
    private List<T> items;
    private T selectedItem;

    public SimpleSelectionField() {
        this(null);
    }
    
    public SimpleSelectionField(List<T> options) {
        wrapper = new SimplePanel();
        wrapper.setStyleName("form-select-field");
        selection = new ListBox();        
        selection.addChangeHandler(new ChangeHandler() {            
            @Override
            public void onChange(ChangeEvent event) {
                int selected = selection.getSelectedIndex();
                if(items != null && items.size() > 0) {
                    selectedItem = items.get(selected);
                    onItemSelected(selectedItem);
                }             
            }
        });
        wrapper.setWidget(selection);        
        initWidget(wrapper);
        if(options != null) {
            setItems(options);
        }
    }
    
    /**
     * Called when an item has been selected
     */
    protected void onItemSelected(T item) {        
    }
    
    /**
     * Returns the selected item or null if none
     */
    public T getSelectedItem() {
        return selectedItem;
    }
    
    @Override
    public void setVisible(boolean visible) {
        wrapper.setVisible(visible);
    }
    
    /**
     * Sets the items rendering them using item's toString()
     */
    public void setItems(List<T> items) {
        this.items = items;
        render();
    }
    
    /**
     * Selects the first element in the store
     */
    public void selectFirst() {
        if(items != null && items.size() > 0) {
            selection.setSelectedIndex(0);
            NativeEvent event = Document.get().createChangeEvent();
            DomEvent.fireNativeEvent(event, selection);
        }
    }
    
    /**
     * Selects the given element if it was found in the store
     */
    public void selectItem(T itemToSelect) {
        if(items != null && items.size() > 0) {
            for(int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                if(itemToSelect.equals(item)) {
                    selection.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    /**
     * Renders items into selection component
     */
    private void render() {
        // Clear previous added items
        selection.clear();
        
        // Add new items
        if(items != null) {
           for(int i = 0; i < items.size(); i++) {
               T item = items.get(i);               
               selection.addItem(getDisplayName(item), getValue(item));
           }
        }
    }
    
    /**
     * Returns the item display name to be used in it's render    
     */
    protected abstract String getDisplayName(T item);
    
    /**
     * Returns the item value
     */
    protected abstract String getValue(T item);
}
