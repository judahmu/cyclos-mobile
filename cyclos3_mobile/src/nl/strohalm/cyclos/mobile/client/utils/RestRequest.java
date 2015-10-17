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
package nl.strohalm.cyclos.mobile.client.utils;

import nl.strohalm.cyclos.mobile.client.Configuration;
import nl.strohalm.cyclos.mobile.client.CyclosMobile;
import nl.strohalm.cyclos.mobile.client.LoggedUser;
import nl.strohalm.cyclos.mobile.client.model.Parameters;
import nl.strohalm.cyclos.mobile.client.model.ServerError;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;

/**
 * Wraps a {@link RequestBuilder} for REST requests, handling headers and result types by convention.
 * 
 * @author luis
 */
public class RestRequest<T> {

    private class RequestCallbackAdapter implements RequestCallback {

        private AsyncCallback<T> callback;

        public RequestCallbackAdapter(AsyncCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onError(Request request, Throwable exception) {                     
            
            // Stop loading progress
            CyclosMobile.get().getMainLayout().stopLoading();
            
            callback.onFailure(exception);
        }

        @Override
        public void onResponseReceived(Request request, Response response) {
                        
            // Stop loading progress
            CyclosMobile.get().getMainLayout().stopLoading();
            
            // Parse the JSON value according to the content type         
            JSONValue jsonValue = null;
            boolean isJson = ConnectionHelper.isJSON(response);
            String contentType = response.getHeader("Content-Type");
            if (isJson) {
                jsonValue = JSONParser.parseStrict(response.getText());
            }
            
            // Successful request
            if (response.getStatusCode() == Response.SC_OK) {
                
                // If server should not give a response value, call onSuccess
                if(httpMethod == RequestBuilder.DELETE) {
                    callback.onSuccess(null);
                    return;
                }                   
           
                // If is JSON extract value and call onSuccess
                if (isJson) {
                    callback.onSuccess(extractResult(jsonValue));
                    return;
                } 
                             
                // Otherwise throw response format exception
                callback.onFailure(new UnexpectedRestResponseFormatException(contentType));
            } else {
                if (isJson) {
                    try {                      
                        // Check for server exception
                        ServerError serverError = jsonValue.isObject().getJavaScriptObject().cast();
                        callback.onFailure(serverError.resolveException());
                    } catch(Exception e) {
                        // Otherwise throw response format exception
                        callback.onFailure(new UnexpectedRestResponseFormatException(contentType));
                    }                    
                } else {
                    callback.onFailure(new StatusCodeException(response.getStatusCode(), response.getStatusText()));
                }
            }                     
        }

        @SuppressWarnings("unchecked")
        protected T extractResult(JSONValue value) {
            if (value == null || value.isNull() != null) {
                return null;
            }
            if (value.isObject() != null) {
                return (T) value.isObject().getJavaScriptObject();
            } else if (value.isArray() != null) {
                return (T) value.isArray().getJavaScriptObject();
            } else if (value.isString() != null) {
                return (T) value.isString().stringValue();
            } else if (value.isNumber() != null) {
                return (T) Double.valueOf(value.isNumber().doubleValue());
            } else if (value.isBoolean() != null) {
                return (T) Boolean.valueOf(value.isBoolean().booleanValue());
            }
            return null;
        }
    }

    private Method           httpMethod;
    private String           path;
    private Parameters       parameters;
    private JavaScriptObject postObject;
    private String           username;
    private String           password;

    public RestRequest() {
    }

    public RestRequest(Method httpMethod, String path) {
        this();
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public RestRequest(Method httpMethod, String path, JavaScriptObject postObject) {
        this(httpMethod, path);
        this.postObject = postObject;
    }

    public RestRequest(Method httpMethod, String path, Parameters parameters) {
        this(httpMethod, path);
        this.parameters = parameters;
    }

    public Method getHttpMethod() {
        return httpMethod;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public String getPassword() {
        return password;
    }

    public String getPath() {
        return path;
    }

    public JavaScriptObject getPostObject() {
        return postObject;
    }

    public String getUsername() {
        return username;
    }
    
    /**
     * Sends a request using the given callback to notify the results. 
     * This method prepares an authentication to be send within. 
     * SessionExpiredException may be raised if user session has expired. 
     */
    public Request sendAuthenticated(AsyncCallback<T> callback) {
        LoggedUser.get().prepare(this);
        return send(callback);
    }
    
    /**
     * Sends a request using the given callback to notify the results. 
     * This method does not uses authentication, to perform authenticated 
     * requests see {@link #sendAuthenticated(AsyncCallback)}
     */
    public Request send(AsyncCallback<T> callback) {   
                
        // Start loading progress
        CyclosMobile.get().getMainLayout().startLoading();
        
        String url = "";
        
        // Append parameters as GET
        if(httpMethod == RequestBuilder.GET) {
            url = Configuration.get().getServiceUrl(this.path, parameters);
        } else {
            url = Configuration.get().getServiceUrl(this.path);
        }

        RequestBuilder request = new RequestBuilder(httpMethod, url);
        request.setTimeoutMillis(20*1000); // 20 seconds
        request.setHeader("Accept", "application/json");
        request.setHeader("Cache-Control","no-cache, no-store, must-revalidate");                
        
        if(httpMethod == RequestBuilder.POST) {
            request.setHeader("Content-Type", "application/json");
            
            // Send post body parameters
            if(parameters != null) {
                String json = parameters.toJSON();
                request.setRequestData(json);
            } else {
                // Send post without data
                request.setRequestData("");
            }
        }
        // Send a JSON post object
        if (postObject != null) { 
            request.setHeader("Content-Type", "application/json");
            request.setRequestData(new JSONObject(postObject).toString());
        }
        if (username != null && !username.isEmpty()) {
            request.setHeader("Authorization", "Basic " + Base64.encode(username + ":" + password));
        }
        request.setCallback(new RequestCallbackAdapter(callback));
        try {            
            // Send request
            return request.send();         
        } catch (RequestException e) {
            callback.onFailure(e);
            
            // Stop loading progress
            CyclosMobile.get().getMainLayout().stopLoading();

            // Returns an emulated request, which does nothing
            return new Request() {
                @Override
                public void cancel() {
                }

                @Override
                public boolean isPending() {
                    return false;
                }
            };
        }
    }

    public void setHttpMethod(Method httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPostObject(JavaScriptObject postObject) {
        this.postObject = postObject;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
