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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.util.Utility;

import org.apache.wsrp4j.commons.exception.ErrorCodes;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.exception.WSRPXHelper;

import org.apache.wsrp4j.commons.persistence.ConsumerPersistentFactory;
import org.apache.wsrp4j.commons.persistence.PersistentDataObject;
import org.apache.wsrp4j.commons.persistence.PersistentHandler;
import org.apache.wsrp4j.commons.persistence.PersistentInformationProvider;

import org.apache.wsrp4j.persistence.xml.ConsumerPersistentInformationProvider;


/**
 * This class is the client factory implementation for the persistence support.
 *
 * @version $Id: ConsumerPersistentFactoryImpl.java 405801 2006-05-10 17:27:23Z dlouzan $
 */
public class ConsumerPersistentFactoryImpl implements ConsumerPersistentFactory {
    
    private static final Log log =
            LogFactory.getLog(ConsumerPersistentFactoryImpl.class);
    
    // holds the PersistentHandler
    private static PersistentHandler persistentHandler;
    
    // holds the ConsumerPersistentInformationProvider
    protected static ConsumerPersistentInformationProvider consumerInfoProvider;
    
    
    /**
     * Returns a PersistentHandler
     *
     * @return persistentHandler
     */
    public PersistentHandler getPersistentHandler() {
        
        String MN = "getConsumerPersistentHandler";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        if (persistentHandler == null) {
            persistentHandler = PersistentHandlerImpl.create();
            
            if (log.isDebugEnabled()) {
                log.debug("PersistentHandler successfully created.");
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return persistentHandler;
        
    }
    
    /**
     * Returns a ServerPersistentInformationrovider
     *
     * @return ProducerPersistentInformationProvider
     */
    public PersistentInformationProvider getPersistentInformationProvider() {
        
        String MN = "getConsumerPersistentInformationProvider";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        if(consumerInfoProvider == null) {
            consumerInfoProvider =
                    ConsumerPersistentInformationProviderImpl.create();
        }
        
        if (log.isDebugEnabled()) {
            log.debug("ConsumerPersistentInformationProvider successfully " +
                    "created.");
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return consumerInfoProvider;
        
    }
    
    
    
    /**
     * Returns the PortletList
     *
     * @return PersistentDataObject
     * @throws WSRPException
     */
    public PersistentDataObject getPortletList() throws WSRPException {
        
        String MN = "getPortletList";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        PortletList pdo = null;
        
        try {
            
            pdo = (PortletList) Class.forName("org.apache.wsrp4j." +
                    "persistence.xml.driver.PortletList").newInstance();
            
            ((ConsumerPersistentInformationProvider) 
            getPersistentInformationProvider()).getPersistentInformation(pdo);
            
        } catch (Exception e) {
            // could not find class
            WSRPXHelper.throwX(log, ErrorCodes.UNMARSHAL_ERROR, e);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return pdo;
        
    }
    
    /**
     * Returns the ConsumerPortletContextList
     *
     * @return PersistentDataObject
     * @throws WSRPException
     */
    public PersistentDataObject getConsumerPortletContextList() 
    throws WSRPException {
        
        String MN = "getConsumerPortletContextList";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        ConsumerPortletContextList pdo = null;
        
        try {
            
            pdo = (ConsumerPortletContextList) Class.forName(
                    "org.apache.wsrp4j.persistence.xml.driver." +
                    "ConsumerPortletContextList").newInstance();
            
            ((ConsumerPersistentInformationProvider) 
            getPersistentInformationProvider()).getPersistentInformation(pdo);
            
        } catch (Exception e) {
            // could not find class
            WSRPXHelper.throwX(log, ErrorCodes.UNMARSHAL_ERROR, e);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return pdo;
        
    }
    
    /**
     * Returns the ProducerList
     *
     * @return PersistentDataObject
     * @throws WSRPException
     */
    public PersistentDataObject getProducerList() throws WSRPException {
        
        String MN = "getProducerList";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        ProducerList pdo = null;
        
        try {
            
            pdo = (ProducerList) Class.forName(
                    "org.apache.wsrp4j.persistence.xml.driver." +
                    "ProducerList").newInstance();
            
            ((ConsumerPersistentInformationProvider) 
            getPersistentInformationProvider()).getPersistentInformation(pdo);
            
        } catch (Exception e) {
            // could not find class
            WSRPXHelper.throwX(log, ErrorCodes.UNMARSHAL_ERROR, e);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return pdo;
        
    }
    
    /**
     * Returns the PageList
     *
     * @return PersistentDataObject
     * @throws WSRPException
     */
    public PersistentDataObject getPageList() throws WSRPException {
        
        String MN = "getPageList";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        PageList pdo = null;
        
        try {
            
            pdo = (PageList) Class.forName(
                    "org.apache.wsrp4j.persistence.xml.driver." +
                    "PageList").newInstance();
            
            ((ConsumerPersistentInformationProvider) 
            getPersistentInformationProvider()).getPersistentInformation(pdo);
            
        } catch (Exception e) {
            // could not find class
            WSRPXHelper.throwX(log, ErrorCodes.UNMARSHAL_ERROR, e);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return pdo;
        
    }
    
    
    /**
     * Returns the UserList
     *
     * @return PersistentDataObject
     * @throws WSRPException
     */
    public PersistentDataObject getUserList() throws WSRPException {
        
        String MN = "getUserList";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        UserList pdo = null;
        
        try {
            pdo = (UserList) Class.forName(
                    "org.apache.wsrp4j.persistence.xml.driver." +
                    "UserList").newInstance();
            
            ((ConsumerPersistentInformationProvider) 
            getPersistentInformationProvider()).getPersistentInformation(pdo);
            
            
        } catch (Exception e) {
            // could not find class
            WSRPXHelper.throwX(log, ErrorCodes.UNMARSHAL_ERROR, e);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return pdo;
        
    }
    
}
