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

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * An {@link Iterable} implementation which wraps a {@link JsArray}, allowing using foreach loops.
 * 
 * @param <T>
 * @author luis
 */
public class IterableJsArray<T extends JavaScriptObject> implements Iterable<T> {

    private JsArray<T> array;
    
    public IterableJsArray(JsArray<T> array) {
        this.array = array;
    }

    /**
     * Returns an iterator for this array
     */
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i <= array.length();
            }

            @Override
            public T next() {
                if (i > array.length()) {
                    throw new NoSuchElementException();
                }
                return array.get(i++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}
