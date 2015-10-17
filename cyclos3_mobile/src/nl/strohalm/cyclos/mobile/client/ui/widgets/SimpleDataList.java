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

import nl.strohalm.cyclos.mobile.client.utils.BaseAsyncCallback;
import nl.strohalm.cyclos.mobile.client.utils.ResultPage;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

/**
 * A single column list of cells which renders pre loaded data.
 * This component can search remote data as well, but does not support server side pagination.
 */
public abstract class SimpleDataList<T extends JavaScriptObject> extends DataList<T> {

    private ListDataProvider<T> listDataProvider;
    
    public SimpleDataList() {
        super();
        
        // Disable loading messages as this component
        // does not make several requests        
        loadingEnabled = false;

        // Fetch data
        onSearchData(new BaseAsyncCallback<JsArray<T>>() {
            @Override
            public void onSuccess(JsArray<T> results) {
                // Invoke custom logic before render data
                onDataLoaded(results);
                
                // Once the request has been made we are allowed
                // to display the empty list widget if necessary
                emptyListWidget.setVisible(true);                               
                
                // Update components and render data
                update(results);
            }            
        });
    }
    
    public SimpleDataList(JsArray<T> elements) {
        super();
        // Existing data, display it
        update(elements);
    }
  
    @Override
    protected void onDataLoaded(ResultPage<T> result) {
        // Does nothing as data is not retrieved with async provider and this component does not support server side pagination
    }
    
    /**
     * May be override in order to execute custom logic on data loaded and before render it     
     */
    protected void onDataLoaded(JsArray<T> results) {
        
    }
    
    /**
     * Update component with loaded elements
     */
    private void update(JsArray<T> elements) {
        values = convertToList(elements);
        
        // Hide scroll if value is empty
        if(values == null || values.size() == 0) {           
            hideScroll();            
        }
        
        listDataProvider.setList(values);
        listDataProvider.refresh();
    }
    
    @Override
    protected void createDataProvider() {
        listDataProvider = new ListDataProvider<T>();
        listDataProvider.addDataDisplay(cellList);        
    }
    
    /**
     * Fetches all the data to be displayed.<br>
     * Should be override when the component was initialized with an empty constructor.
     */
    protected void onSearchData(AsyncCallback<JsArray<T>> values) {        
    
    }
    
    @Override
    protected void onSearchData(int page, int length, AsyncCallback<ResultPage<T>> callback) {
        // Does nothing as data may be received in constructor and this component does not support server side pagination
    }
}
