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
package org.apache.wsrp4j.persistence.xml.driver;

import org.apache.wsrp4j.commons.persistence.PersistentInformation;
import org.apache.wsrp4j.persistence.xml.PersistentDataObjectXML;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wsrp4j.commons.exception.WSRPException;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;


/**
 * This class is the abstract implementation of the class 
 * PersistentDataObjectXML.
 *
 * @version $Id: PersistentDataObjectImpl.java 376683 2006-02-10 13:51:22Z cziegeler $
 */
public abstract class PersistentDataObjectImpl 
        implements PersistentDataObjectXML {
    
    // the persistent information
    private PersistentInformation _persistentInfo;
    
    // list of objects to be stored or as result of the restore
    protected ArrayList _objects = new ArrayList();
    
    // the last element added to the list
    protected Object _lastElement;
    
    /**
     * Set the persistent information object to this data object.
     *
     * @param persistentInfo
     */
    public void setPersistentInformation(PersistentInformation persistentInfo) {
        _persistentInfo = persistentInfo;
    }
    
    /**
     * Returns the persistent information object, contained in this data object.
     *
     * @return persistentInformation
     */
    public PersistentInformation getPersistentInformation() {
        return _persistentInfo;
    }
    
    /**
     * Clears the object array
     */
    public void clear() {
        _objects.clear();
    }
    
    /**
     * Returns the list of objects as an iterator.
     *
     * @return iterator of objects
     */
    public Iterator getObjects() {
        return _objects.iterator();
    }
    
    /**
     * Add an object to the internal list.
     *
     * @param object to be added
     */
    public void addObject(Object object) {
        _objects.add(object);
        _lastElement = object;
    }
    
    /**
     * Returns the last element (object) of the list
     *
     * @return object
     */
    public Object getLastElement() {
        return _lastElement;
    }
    
    /**
     * Unmarshal of persistent objects.
     *
     * @param fileReader of the input XML file
     * @param unmarshaller optional
     *
     * @throws WSRPException
     */
    public abstract void unMarshalFile(FileReader fileReader, 
            Unmarshaller unmarshaller)
    throws WSRPException;
    
    /**
     * Marshal of persistent objects
     *
     * @param fileWriter to the output XML file
     * @param marshaller optional
     *
     * @throws WSRPException
     */
    public abstract void marshalFile(
            FileWriter fileWriter, Marshaller marshaller)
    throws WSRPException;
    
}
