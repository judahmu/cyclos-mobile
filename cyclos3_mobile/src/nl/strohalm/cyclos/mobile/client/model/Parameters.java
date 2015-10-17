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
package nl.strohalm.cyclos.mobile.client.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.mobile.client.utils.StringHelper;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * Represents parameters passed from one page to other. It is also used to read / build history tokens (methods {@link #parse(String)} and
 * {@link #toToken()}). <b>Caution:</b>: Arbitrary objects may be passed, when object parameters are set, they are not used on history tokens.
 * 
 * @author luis
 */
public class Parameters {

    public static final String PAIR_SEPARATOR  = "&";
    public static final String VALUE_SEPARATOR = "=";
    public static final String JSON_SEPARATOR  = ":";

    public static native String decodeURIComponent(String string)
    /*-{
        return decodeURIComponent(string);
    }-*/;

    public static native String encodeURIComponent(String string)
    /*-{
        return encodeURIComponent(string);
    }-*/;

    /**
     * Parse the given string as a Parameters object
     */
    public static Parameters parse(String string) {
        Parameters parameters = new Parameters();
        if (string == null || string.length() == 0) {
            return parameters;
        }
        string = string + PAIR_SEPARATOR;
        int lastIndex = 0;
        int currentIndex = string.indexOf(PAIR_SEPARATOR);
        while (currentIndex >= 0) {
            String pairString = string.substring(lastIndex, currentIndex);
            int valueSeparatorIndex = pairString.indexOf(VALUE_SEPARATOR);
            if (valueSeparatorIndex > 0) {
                String name = pairString.substring(0, valueSeparatorIndex);
                String value = pairString.substring(valueSeparatorIndex + 1);
                parameters.add(name, decodeURIComponent(value));
            }
            lastIndex = currentIndex + 1;
            currentIndex = string.indexOf(PAIR_SEPARATOR, currentIndex + 1);
        }
        return parameters;
    }

    private Map<String, List<String>> values;
    private Map<String, Object>       objects;
    private Map<String, JSONArray>    objectList;

    /**
     * Constructs with empty parameters
     */
    public Parameters() {
        values = new HashMap<String, List<String>>();
    }

    /**
     * Constructs with the given parameters parameters
     */
    public Parameters(Map<String, List<Object>> values) {
        this();
        if (values != null) {
            for (Map.Entry<String, List<Object>> entry : values.entrySet()) {
                add(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Constructs with a single parameters, with possible multiple values
     */
    public Parameters(String name, Object... values) {
        this(Collections.singletonMap(name, Arrays.asList(values)));
    }

    /**
     * Adds the given values for an existing name, or sets the name if not found yet
     */
    public List<String> add(String name, List<Object> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        // Convert the objects into strings
        List<String> valuesList = new ArrayList<String>();
        for (Object value : values) {
            if (value != null) {
                valuesList.add(value.toString());
            }
        }
        // Add the list in the current values
        List<String> current = this.values.get(name);
        if (current == null) {
            current = new ArrayList<String>();
            this.values.put(name, current);
        }
        current.addAll(valuesList);
        return current;
    }

    /**
     * Adds the given values for an existing name, or sets the name if not found yet
     */
    public List<String> add(String name, Object... values) {
        return add(name, Arrays.asList(values));
    }

    /**
     * Returns a value as boolean
     */
    public boolean getBoolean(String name) {
        String string = getString(name);
        return string == null ? false : Boolean.parseBoolean(string);
    }

    /**
     * Returns a value as an enumeration item
     */
    public <T extends Enum<T>> T getEnum(String name, Class<T> enumClass) {
        String string = getString(name);
        return string == null ? null : Enum.valueOf(enumClass, string);
    }

    /**
     * Returns the given value as Long
     */
    public Long getLong(String name) {
        String string = getString(name);
        return string == null ? null : new Long(string);
    }

    /**
     * Returns the given value as a List of Strings
     */
    public List<Long> getLongList(String name) {
        List<String> stringList = getStringList(name);
        if (stringList == null) {
            return null;
        }
        List<Long> list = new ArrayList<Long>();
        for (String string : stringList) {
            list.add(new Long(string));
        }
        return list;
    }

    /**
     * Returns a value as an enumeration item, throwing an error when value is null
     */
    public <T extends Enum<T>> T getRequiredEnum(String name, Class<T> enumClass) {
        return requiredParameter(name, getEnum(name, enumClass));
    }

    /**
     * Returns the given value as Long, throwing an error when value is null
     */
    public Long getRequiredLong(String name) {
        return requiredParameter(name, getLong(name));
    }

    /**
     * Returns an object parameter
     */
    public Object getObject(String name) {
        return objects == null ? null : objects.get(name);
    }   
    
    /**
     * Returns an object list from parameters
     */
    public JSONArray getObjectList(String name) {
        return objectList == null ? null : objectList.get(name); 
    }
    
    /**
     * Returns the given value as String, throwing an error when value is null
     */
    public String getRequiredString(String name) {
        return requiredParameter(name, getString(name));
    }

    /**
     * Returns the given value as String
     */
    public String getString(String name) {
        List<String> list = values.get(name);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    /**
     * Returns the given value as a List of Strings
     */
    public List<String> getStringList(String name) {
        return values.get(name);
    }

    /**
     * Removes the values for the given name, returning the current value, if any
     */
    public List<String> remove(String name) {
        return values.remove(name);
    }

    /**
     * Sets a value as boolean
     */
    public void set(String name, boolean value) {
        values.put(name, Arrays.asList(String.valueOf(value)));
    }

    /**
     * Sets the value (or multiple values) for the given name as enums, returning the current values
     */
    public List<String> set(String name, Enum<?>... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        for (Enum<?> enm : values) {
            if (enm != null) {
                list.add(enm.name());
            }
        }
        this.values.put(name, list);
        return list;
    }

    /**
     * Sets the value (or multiple values) for the given name as longs, returning the current values
     */
    public List<String> set(String name, Long... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        for (Long lng : values) {
            if (lng != null) {
                list.add(lng.toString());
            }
        }
        this.values.put(name, list);
        return list;
    }
    
    /**
     * Sets an object parameter.<br>
     * <b>WARNING</b>:<br>Object parameters are not added to URLs, hence,
     * cannot be used on standalone pages.<br> 
     * Also only {@link JSONObject} types will be send 
     * as parameters in POST requests
     */
    public void setObject(String name, Object object) {
        if (objects == null) {
            objects = new HashMap<String, Object>();
        }
        objects.put(name, object);
    }
    
    
    /**
     * Sets an object list parameter.<br>
     * <b>WARNING</b>:<br>Object list parameters are not added to URLs, hence,
     * cannot be used on standalone pages.<br> 
     */
    public void setObjectList(String name, JSONArray list) {
        if (objectList == null) {
            objectList = new HashMap<String, JSONArray>();
        }
        objectList.put(name, list);
    }
    
    /**
     * Sets the value (or multiple values) for the given name, returning the current values
     */
    public List<String> set(String name, String... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<String>(Arrays.asList(values));
        this.values.put(name, list);
        return list;
    }
    
    /**
     * Returns the parameter values
     */
    public Map<String, List<String>> getValues() {
        return values;
    }
    
    /**
     * Returns the parameter object list
     */
    public Map<String, JSONArray> getObjectList() {
        return objectList;
    }
    
    /**
     * Returns the parameter objects
     */
    public Map<String, Object> getObjects() {
        return objects;
    }        
    
    /**
     * Sets the parameter values
     */
    public void setValues(Map<String, List<String>> values) {
        this.values = values;
    }
    
    /**
     * Sets the parameter object list
     */
    public void setObjectList(Map<String, JSONArray> objectList) {
        this.objectList = objectList;
    }
    
    /**
     * Sets the parameter objects
     */
    public void setObjects(Map<String, Object> objects) {
        this.objects = objects;
    }
    
    /**
     * Same as {@link #toToken(boolean)}<br>
     * This method always encode uri values
     */
    public String toToken() {
        return toToken(true);
    }
    
    /**
     * Transforms the current parameters into a string that may be used on the history token.<br>
     * If flag is true this method will encode uri values
     */
    public String toToken(boolean encode) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : values.entrySet()) {
            String paramName = entry.getKey();
            List<String> paramValues = entry.getValue();
            if (paramValues != null && !paramValues.isEmpty()) {
                for (String currentValue : paramValues) {
                    if (StringHelper.isEmpty(currentValue)) {
                        continue;
                    }
                    currentValue = currentValue.replace(PAIR_SEPARATOR, "").replace(VALUE_SEPARATOR, "");
                    if(StringHelper.isEmpty(currentValue)) {
                        continue;
                    }
                    if (first) {
                        first = !first;
                    } else {
                        sb.append(PAIR_SEPARATOR);
                    }                                
                    sb.append(paramName);
                    sb.append(VALUE_SEPARATOR);
                    sb.append(encode ? encodeURIComponent(currentValue) : currentValue);
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * Transforms the current parameters into a json string that may be used on post requests
     */
    public String toJSON() {        
        StringBuilder sb = new StringBuilder();
        sb.append("{");     
        
        // Parse query string
        String query = parseQueryString();                               
        
        // Parse JSON objects
        String object = parseJSONObject();
        
        // Parse JSON object list
        String list = parseJSONObjectList();
        
        // Append JSON
        String prefix = "";
        if(StringHelper.isNotEmpty(query)) {
            sb.append(query);
            prefix = ",";
            sb.append(prefix);
        }        
        if(StringHelper.isNotEmpty(object)) {
            sb.append(object);
            prefix = ",";
            sb.append(prefix);
        }        
        if(StringHelper.isNotEmpty(list)) {
            sb.append(list);
            prefix = ",";
            sb.append(prefix);
        }      
        // Remove last prefix if needed
        if(StringHelper.isNotEmpty(prefix)) {
            sb.setLength(sb.length() - 1);
        }
        
        sb.append("}");
        return sb.toString();
    };
    
    /**
     * Parses current query string and set outputs in the given StringBuilder
     */
    private String parseQueryString() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        String token = toToken(false);        
        if(StringHelper.isNotEmpty(token)) {          
            String[] params = token.split(PAIR_SEPARATOR);
            for(String param : params) {
                sb.append(prefix);
                prefix = ",";
                String[] pair = param.split(VALUE_SEPARATOR);
                sb.append(pairToJSON(pair[0], pair[1], true));                
            }            
        }
        return sb.toString();
    }

    /**
     * Parses current JSONObjects and set the outputs in the given StringBuilder 
     */
    private String parseJSONObject() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        if(objects != null) {            
            for(String key : objects.keySet()) {
                try {
                    JSONObject jso = (JSONObject) objects.get(key);
                    sb.append(prefix);
                    prefix = ",";
                    sb.append(pairToJSON(key, jso.toString(), false));
                } catch(ClassCastException e) {
                    //Ignore
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * Parses current JSONObject list and set the outputs in the given StringBuilder 
     */
    private String parseJSONObjectList() {
        StringBuilder sb = new StringBuilder();
        String keyPrefix = "";
        if(objectList != null) {            
            for(String key : objectList.keySet()) {
                String objectPrefix = "";
                StringBuilder sb2 = new StringBuilder();
                sb.append(keyPrefix);                
                sb2.append("[");
                JSONArray objects = objectList.get(key);
                for(int i = 0; i < objects.size(); i++) {
                    try {
                        JSONValue value = objects.get(i);
                        sb2.append(objectPrefix);
                        objectPrefix = ",";
                        sb2.append(value.toString());
                    } catch(ClassCastException e) {
                        //Ignore
                    }
                } 
                sb2.append("]"); 
                sb.append(pairToJSON(key, sb2.toString(), false));                               
            }
        }
        return sb.toString();
    }
    
    /**
     * Converts a key:value pair to JSON valid format
     */
    private String pairToJSON(String key, String value, boolean escapeValue) {
        StringBuilder sb = new StringBuilder();
        sb.append(JsonUtils.escapeValue(key));
        sb.append(JSON_SEPARATOR);
        sb.append(escapeValue ? JsonUtils.escapeValue(value) : value);
        return sb.toString();
    }

    /**
     * Returns a required parameter set. If none found raises an {@link IllegalArgumentException}     
     */
    private <T> T requiredParameter(String name, T value) {
        if (value == null) {
            throw new IllegalArgumentException(name);
        }
        return value;
    }
}
