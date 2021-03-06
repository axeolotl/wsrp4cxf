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

import java.io.FileReader;
import java.io.FileWriter;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.util.Utility;

import org.apache.wsrp4j.commons.exception.ErrorCodes;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.exception.WSRPXHelper;

import org.apache.wsrp4j.commons.consumer.driver.WSRPPortletImpl;


/**
 * This class collects Portlets and implements the
 * marshal / unmarshal methods for CASTOR XML file support. Detailed
 * information on persistent organizational data has to be requested
 * with this class from the corresponding PersistentInformationProvider.
 *
 * @version $Id: PortletList.java 408857 2006-05-23 09:32:14Z dlouzan $
 */
public class PortletList extends PersistentDataObjectImpl {
    
    // log and trace support
    private static final Log log = LogFactory.getLog(PortletList.class);
    
    /**
     * Default Constructor
     */
    public PortletList() {
        
    }
    
    /**
     * Constructs a Portlet object from a CASTOR persistent
     * XML file.
     *
     * @param fileReader to the input data file
     *
     * @param unmarshaller optional, part of the CASTOR package. In case of
     *                     a null value as input parameter, the static methods
     *                     of the CASTOR unmarshaller are used.
     *
     * @throws WSRPException
     */
    public void unMarshalFile(FileReader fileReader, Unmarshaller unmarshaller) 
    throws WSRPException {
        
        String MN = "unMarshalFile";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        try {
            if (unmarshaller == null) {
                addObject(Unmarshaller.unmarshal(
                        WSRPPortletImpl.class, fileReader));
            } else {
                addObject(unmarshaller.unmarshal(fileReader));
            }
            
        } catch (Exception e) {
            WSRPXHelper.throwX(log, ErrorCodes.UNMARSHAL_ERROR, e);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
    }
    
    /**
     * Writes a Portlet, which must be the first element
     * in the PersistentDataObject map, to a persistent XML file. In case
     * of a null value as input parameter, the static methods of the CASTOR
     * Marshaller are used.
     *
     * @param fileWriter to the output XML file
     * @param marshaller optional
     *
     * @throws WSRPException
     */
    public void marshalFile(FileWriter fileWriter, Marshaller marshaller) 
    throws WSRPException {
        
        String MN = "marshalFile";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        try {
            if (marshaller == null) {
                Marshaller.marshal(_objects.get(0), fileWriter);
                
            } else {
                marshaller.marshal(_objects.get(0));
            }
        } catch (Exception e) {
            WSRPXHelper.throwX(log, ErrorCodes.MARSHAL_ERROR, e);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
    }
    
}
