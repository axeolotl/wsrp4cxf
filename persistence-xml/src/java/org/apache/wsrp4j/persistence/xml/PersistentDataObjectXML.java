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
package org.apache.wsrp4j.persistence.xml;

import java.io.FileReader;
import java.io.FileWriter;

import org.apache.wsrp4j.commons.persistence.PersistentDataObject;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;


/**
 * This class is the generic interface for the PersistentDataObject
 * implementations with XML support.
 *
 * @version $Id: PersistentDataObjectXML.java 376683 2006-02-10 13:51:22Z cziegeler $
 */
public interface PersistentDataObjectXML extends PersistentDataObject {
    
    /**
     * Unmarshal of persistent objects.
     *
     * @param fileReader of the input XML file
     * @param unmarshaller Unmarshaller, optional
     *
     * @throws WSRPException
     */
    public void unMarshalFile(FileReader fileReader, Unmarshaller unmarshaller) 
    throws WSRPException;
    
    /**
     * Marshal of persistent objects
     *
     * @param fileWriter to the output XML file
     * @param marshaller optional
     *
     * @throws WSRPException
     */
    public void marshalFile(FileWriter fileWriter, Marshaller marshaller) 
    throws WSRPException;
    
    
}
