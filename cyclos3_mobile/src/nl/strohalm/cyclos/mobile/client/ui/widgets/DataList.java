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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.mobile.client.CyclosMobile;
import nl.strohalm.cyclos.mobile.client.Messages;
import nl.strohalm.cyclos.mobile.client.ui.Icon;
import nl.strohalm.cyclos.mobile.client.utils.BaseAsyncCallback;
import nl.strohalm.cyclos.mobile.client.utils.ComponentEventHelper;
import nl.strohalm.cyclos.mobile.client.utils.IterableJsArray;
import nl.strohalm.cyclos.mobile.client.utils.ResultPage;
import nl.strohalm.cyclos.mobile.client.utils.ScreenHelper;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

/**
 * A single column list of cells which fetches large data asynchronously on scroll event.
 */
public abstract class DataList<T extends JavaScriptObject> extends Composite {
    
    protected FlowPanel            listContainer;
    protected FlowPanel            mainContainer;
    protected List<T>              values;
    protected CellList<T>          cellList;
    protected AbstractCell<T>      cell;
    protected ScrollingPager       pager;
    protected AsyncDataProvider<T> asyncDataProvider;
    protected SimplePanel          loadingPanel;
    protected FlowPanel            scrollPanel;
    protected boolean              loadingEnabled;
    protected Widget               emptyListWidget;
    protected static int           PAGE_SIZE = 25;     
        
    public DataList() {
        values = new ArrayList<T>(); 
        mainContainer = new FlowPanel();
        listContainer = new FlowPanel();
        loadingEnabled = true;
        createCell();
        createCellList();
        createDataProvider();
        createContainer();
        createPager();
        createLoadingPanel();
        createScrollPanel();
        
        initWidget(mainContainer);
    }
    
    /**
     * Creates the container widget
     * which will be scrollable
     */
    private void createContainer() {
        listContainer = new FlowPanel();
        Widget header = addHeader();
        if(header != null) {
            listContainer.add(header);
        }
        listContainer.add(cellList);
        Widget footer = addFooter();
        if(footer != null) {
            listContainer.add(footer);
        }
    }
    
    /**
     * May be override in order to add a header to the widget<br>
     * This widget will be scrolled within cells   
     */
    protected Widget addHeader() {
        return null;
    }
    
    /**
     * May be override in order to add a footer to the widget<br> 
     * This widget will be scrolled within cells 
     */
    protected Widget addFooter() {
        return null;
    }

    /**
     * Creates the pager which will be used for pagination purposes
     */
    private void createPager() {                
        pager = new ScrollingPager(); 
        pager.getElement().setId("data-list");
        pager.setWidget(listContainer);
        pager.setDisplay(cellList);  
        
        // Adjust pager to fit in screen and generate scroll if needed
        Window.addResizeHandler(new ResizeHandler() {                   
            @Override
            public void onResize(ResizeEvent event) {
                adjustPager(pager);
            }
        });
        pager.addAttachHandler(new AttachEvent.Handler() {  
            @Override
            public void onAttachOrDetach(AttachEvent event) {               
                adjustPager(pager);
            }
        });
        
        // Initialize widget
        mainContainer.add(pager);
    }
    
    /**
     * Creates a loading data panel
     */
    private void createLoadingPanel() {
        loadingPanel = new SimplePanel();
        loadingPanel.setStyleName("loading-data");
        loadingPanel.setVisible(false);

        LabelField text = new LabelField(Messages.Accessor.get().loadingData());
        text.addStyleName("loading-data-text");
   
        loadingPanel.setWidget(text);
        listContainer.add(loadingPanel);
    }
    
    /**
     * Creates a scrolling button panel only if browser 
     * does not support native scrolling for this widget
     */
    private void createScrollPanel() {
        if(!ScreenHelper.supportsTouch()) {
            scrollPanel = new FlowPanel();
            scrollPanel.setStyleName("scroll-data");
            
            SimplePanel up = new SimplePanel();
            up.setStyleName("scroll-data-image");
            up.setWidget(Icon.UP.image());       
            
            SimplePanel down = new SimplePanel();
            down.setStyleName("scroll-data-image");
            down.setWidget(Icon.DOWN.image());
            
            // Add scrolling events
            ComponentEventHelper.addScrollEvents(up, down, pager.getElement().getId());
            
            scrollPanel.add(up);
            scrollPanel.add(down);
    
            mainContainer.add(scrollPanel);
        }
    }
    
    /**
     * Displays the loading panel if available
     */
    private void displayLoading() {
        if(loadingEnabled) {
            loadingPanel.setVisible(true);
        }
    }
    
    /**
     * Hides the scroll panel if available
     */
    protected void hideScroll() {
        if(scrollPanel != null) {
           scrollPanel.setVisible(false);
        }              
    }
    
    /**
     * Hides the loading panel if available
     */
    private void hideLoading() {
        if(loadingEnabled) {
            // If exists data hide the panel using delay
            // to let the user see the message
            if(values != null && values.size() > 0) {            
                Timer t = new Timer() {
                    @Override
                    public void run() {
                        // Hide loading panel
                        loadingPanel.setVisible(false);
                    }
                };
                t.schedule(2000);
            } else {
                // Otherwise hide the panel
                loadingPanel.setVisible(false);
            }
        }              
    }
    
    
    
    /**
     * Adjust pager sizes to fit in the menu container.<br>
     * May be override in order to use custom sizes
     */
    protected void adjustPager(AbstractPager pager) {
        // Adjust the height, to generate scroll
        int height =  CyclosMobile.get().getMainLayout().getMainHeight();
        if(height != 0) {
            pager.setSize("100%", Math.abs(height) + "px");
        }       
    }
    
    /**
     * Creates a {#CellList} used to display the data
     */
    private void createCellList() {
        cellList = new CellList<T>(cell);
        cellList.setPageSize(PAGE_SIZE);
        cellList.setRowCount(PAGE_SIZE);      
        cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
        cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);  
        
        // Sets widget when no data available
        cellList.setEmptyListWidget(getEmptyListWidget());
        // Sets loading widget
        cellList.setLoadingIndicator(getLoadingWidget());
        
        // Add a selection model for cell's selection        
        final NoSelectionModel<T> selectionModel = new NoSelectionModel<T>(); 
        cellList.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new Handler() {            
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                onRowSelected(selectionModel.getLastSelectedObject());                 
            }
        });
    }   
    
    /**
     * Creates a label by default as empty list widget.<br>
     * May be override in order to display a custom widget     
     */
    protected Widget getEmptyListWidget() {
        emptyListWidget = new Label(Messages.Accessor.get().notAvailableData());
        emptyListWidget.setStyleName("no-data");
        // This message should not be shown until the request has been made
        emptyListWidget.setVisible(false);
        return emptyListWidget;
    }
    
    /**
     * Creates a label by default as loading indicator widget.<br>
     * May be override in order to display a custom widget     
     */
    protected Widget getLoadingWidget() {
        Label label = new Label(Messages.Accessor.get().loadingData());
        label.setStyleName("no-data");
        return label;
    }
    
    /**
     * Creates the render representation of the model object
     */
    private void createCell() {
        cell = new AbstractCell<T>() {
            @Override
            public void render(Context context, T value, SafeHtmlBuilder sb) {                                                                                  
                Widget widget = onRender(context, value);
                if(widget != null) {             
                    sb.appendHtmlConstant(widget.toString());
                }
            }                      
        };
    }
    
    /**
     * Creates the provider which fetches and updates the data
     */
    protected void createDataProvider() {
        asyncDataProvider = new AsyncDataProvider<T>() {                       
                       
            @Override
            protected void onRangeChanged(final HasData<T> display) {                                
                
                // Get the new range.
                final Range range = display.getVisibleRange();               
                final int start = range.getStart();                              
                
                // Calculate total pages count
                int diff = display.getRowCount() % PAGE_SIZE != 0 ? 1 : 0;
                int pages = (display.getRowCount() / PAGE_SIZE) + diff;
                
                // Current page
                int page = (int) Math.ceil(range.getLength() / (double)PAGE_SIZE);
                
                // No more data
                if(page > pages) {
                    
                    // Hide loading message
                    hideLoading();
                    
                    return;
                }                                                        
                
                // First page start counting from zero
                page = page -1;
                
                BaseAsyncCallback<ResultPage<T>> callback = new BaseAsyncCallback<ResultPage<T>>() {  
                    
                    @Override
                    public void onFailure(Throwable caught) {
                        onDataError();
                        super.onFailure(caught);
                    }
                    
                    @Override
                    public void onSuccess(ResultPage<T> result) {
                        // Invoke custom logic before render data
                        onDataLoaded(result);
                        
                        // Update components and render data
                        List<T> newValues = convertToList(result.getElements());
                        values.addAll(newValues);
                        
                        // Once the request has been made we are allowed
                        // to display the empty list widget if necessary
                        emptyListWidget.setVisible(true);
                        
                        updateRowCount(result.getTotalCount(), true);
                        updateRowData(start, values);                           
                        
                        // Hide loading message
                        hideLoading();
                        
                        // Hide scroll if result is empty
                        if(result.getTotalCount() == 0) {
                            hideScroll();
                        }
                    }                    
                };                
                onSearchData(page, PAGE_SIZE, callback);                                                         
            }
        };
        // Handle the cellList
        asyncDataProvider.addDataDisplay(cellList);
    }
    
    /**
     * May be override in order to execute custom logic on data loaded and before render it     
     */
    protected void onDataLoaded(ResultPage<T> result) {
        
    }
    
    /**
     * Is invoked when a problem occur retrieving data
     */
    private void onDataError() {
        // Hides any visible message
        hideLoading();
    }
    
    /**
     * Converts an JsArray to List which is necessary in GWT methods     
     */
    protected List<T> convertToList(JsArray<T> elements) {
        List<T> list = new ArrayList<T>();
        if(elements != null && elements.length() > 0) {
            IterableJsArray<T> iterable = new IterableJsArray<T>(elements);        
            for (Iterator<T> it = iterable.iterator(); it.hasNext();) {
                T value = it.next();
                if(value != null) {
                    list.add(value);
                }
            }      
        }
        return list;
    }
    
    /**
     * Returns the widget which will be rendered in the list.
     * @param value The model value, may be null so according checks should be done
     */
    protected abstract Widget onRender(Context context, T value);
    
    /**
     * Should invoke service methods to fetch the data     
     */
    protected abstract void onSearchData(int page, int length, AsyncCallback<ResultPage<T>> callback);
    
    /**
     * Called when a row is selected
     */
    protected void onRowSelected(T value) {
        
    }
    
    /**
     * Scrolling pager to fetch the data when scrolls down    
     */
    public class ScrollingPager extends AbstractPager { 

        private int incrementSize = PAGE_SIZE;
        private int lastScrollPos = 0;
        private int lastPageSize = -1;
        private final ScrollPanel scrollPanel;        
    
        public ScrollingPager() {
            // Create scroll widget
            scrollPanel = new ScrollPanel();
            initWidget(scrollPanel);
    
            // Do not let the scrollable take tab focus
            scrollPanel.getElement().setTabIndex(-1);
    
            // Add scroll events.
            scrollPanel.addScrollHandler(new ScrollHandler() {                  
                public void onScroll(ScrollEvent event) {
                    // If scrolling up, ignore the event
                    int oldScrollPos = lastScrollPos;
                    lastScrollPos = scrollPanel.getVerticalScrollPosition();
                    
                    if (oldScrollPos >= lastScrollPos) {
                        return;
                    }
    
                    // Update display data
                    HasRows display = getDisplay();                
                    if(display != null) {
                        int maxScrollTop = scrollPanel.getWidget().getOffsetHeight() - scrollPanel.getOffsetHeight();
                        if (lastScrollPos >= maxScrollTop) {
                                                     
                            // We are near the end, so increase the page size
                            int newPageSize = Math.min(display.getVisibleRange().getLength() + incrementSize, display.getRowCount());
                            
                            if(lastPageSize != newPageSize) {
                                lastPageSize = newPageSize;
                                // Meanwhile display loading message
                                displayLoading();
                            }
                            display.setVisibleRange(0, newPageSize);                                                      
                        }                       
                    }
                }
            });
          }    
         
          /**
           * Sets the widget to be scrolled           
           */
          public void setWidget(Widget widget) {
              scrollPanel.setWidget(widget);
          }
    
          /**
           * Set the number of rows by which the range is increased when the scrollbar
           * reaches the bottom.
           *
           * @param incrementSize the incremental number of rows
           */
          public void setIncrementSize(int incrementSize) {
            this.incrementSize = incrementSize;
          }
    
          @Override
          protected void onRangeOrRowCountChanged() {
              // Does nothing
          }
    }     

}
