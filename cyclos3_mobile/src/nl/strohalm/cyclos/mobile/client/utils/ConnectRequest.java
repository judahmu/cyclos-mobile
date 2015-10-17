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
package nl.strohalm.cyclos.mobile.client.utils;

import java.util.List;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

/**
 * Handles multiple requests to find a valid url.
 */
public abstract class ConnectRequest {

    private List<ConnectAction> requests;
    
    public ConnectRequest(List<ConnectAction> requests) {
        this.requests = requests;
        execute();
    }
    
    /**
     * This method is invoked when a valid url has been found or
     * all requests has been done
     * @param url The valid application url, or null if none found 
     */
    protected abstract void onResponseFinished(String url);
    
    /**
     * Executes the requests and invokes {@link #onResponseFinished(String)}
     */
    private void execute() {
        if(!requests.isEmpty()) {
            final ConnectAction action = requests.remove(0);
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(action.getServiceUrl()));
            builder.setTimeoutMillis(15*1000); // 15 secs
            try {
               builder.sendRequest(null, new RequestCallback() {
                  public void onError(Request request, Throwable exception) {                      
                      // Couldn't connect to server (could be timeout, SOP violation, etc.)
                      // Execute next request if available
                      execute();
                  }

                  public void onResponseReceived(Request request, Response response) {                      
                      if (200 == response.getStatusCode()) {                        
                          if(action.isAttachment()) {
                              // Return the url in the attachment
                              onResponseFinished(response.getText());
                          } else if(ConnectionHelper.isJSON(response)) {
                              // Return the given valid url
                              onResponseFinished(action.getAppUrl());
                          }  else {
                              // Not valid JSON, execute next request if available
                              execute();
                          }
                      } else {
                          // Server error, execute next request if available
                          execute();
                      }
                    }       
                });
              } catch (RequestException e) {
                // Couldn't connect to server, execute next request if available 
                execute();
              }
        } else {
            // End of all requests, no valid url has been found
            onResponseFinished(null);
        }
    }
}
