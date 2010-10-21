/*
 * Copyright 2003-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wsrp4j.commons.persistence;

import java.util.Iterator;

/**
 * This class is the generic interface for the PersistentDataObject 
 * implementations used to store and retrieve objects from a persistent
 * store. 
 * 
 * @version $Id: PersistentDataObject.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface PersistentDataObject {

    /**
     * Sets the PersistentInformation object for this PersistentDataObject
     *
     * @param persistentInfo 
     * @see PersistentInformation
     */
    void setPersistentInformation(PersistentInformation persistentInfo);

    /**
     * Get the PersistentInformation for this PersistentDataObject
     *
     * @return PersistentInformation
     * @see PersistentInformation
     */
    PersistentInformation getPersistentInformation();

    /**
     * Clear the internal object collection 
     */
    void clear();

    /**
     * Returns all objects in the object map as an iterator
     * 
     * @return Iterator
     */
    Iterator getObjects();

    /**
     * Add an element to the internal map
     * 
     * @param object Object as element
     * 
     */
    void addObject(Object object);

    /**
     * Returns the last element added
     * 
     * @return Object as last element
     */
    Object getLastElement();
}
