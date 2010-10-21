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
package org.apache.wsrp4j.commons.persistence.driver;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.exception.ErrorCodes;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.exception.WSRPXHelper;

import org.apache.wsrp4j.commons.persistence.ConsumerPersistentFactory;
import org.apache.wsrp4j.commons.persistence.ProxyPersistentFactory;

/**
 * This class provides a static method to access the client and server
 * persistent factories.It reads in the file "WSRPServices.properties" for the
 * server and the file "SwingConsumer.properties" for a client.
 * 
 * @version $Id: PersistentAccess.java 405794 2006-05-10 16:46:24Z dlouzan $
 */
public class PersistentAccess {
    
    private static Log log = LogFactory.getLog(PersistentAccess.class);

    // the name of the .properties file for the server
    private static String PRODUCER_PROPERTIES = "WSRPServices.properties";

    // the name of the .properties file for the client
    private static String CONSUMER_PROPERTIES = "SwingConsumer.properties";
    
    private static String PROXY_PROPERTIES = "ProxyPortlet.properties";

    // property name of the producer factory
    private static String PRODUCER_PERSISTENT_FACTORY = "producer.persistent.factory";

    // property name of the consumer factory
    private static String CONSUMER_PERSISTENT_FACTORY = "consumer.persistent.factory";
    
    private static String PROXY_PERSISTENT_FACTORY = "proxy.persistent.factory";

    // the content of the properties file
    private static Properties pFactories;

    // holds the instance of the client factory after initializing
    private static ConsumerPersistentFactory consumerPersistentFactory;

    private static ProxyPersistentFactory proxyPersistentFactory;
    // log and trace support

    /**
     * Fetches a client factory-instance.
     * 
     * @return ConsumerPersistentFactory
     * @throws WSRPException
     */
    public static ConsumerPersistentFactory getConsumerPersistentFactory()
    throws WSRPException {

        if (consumerPersistentFactory == null) {
            consumerPersistentFactory = (ConsumerPersistentFactory) getFactory(
                    CONSUMER_PERSISTENT_FACTORY, CONSUMER_PROPERTIES);
        }

        if (log.isDebugEnabled()) {
            log.debug("Loaded ConsumerPersistentFactory");
        }

        return consumerPersistentFactory;
    }
    
    public static ProxyPersistentFactory getProxyPersistentFactory()
    throws WSRPException {

        if (proxyPersistentFactory == null) {
            proxyPersistentFactory = (ProxyPersistentFactory) getFactory(
                    PROXY_PERSISTENT_FACTORY, PROXY_PROPERTIES);
        }

        if (log.isDebugEnabled()) {
            log.debug("Loaded ProxyPersistentFactory");
        }
        
        return proxyPersistentFactory;
    }

    /**
     * Returns the factory loaded from the properties file.
     * 
     * @param type
     *            of the factory as string value
     * @param propertyFile
     *            name of the property file as string value
     * @throws WSRPException
     */
    private static Object getFactory(String type, String propertyFile) 
    throws WSRPException {

        Object obj = null;

        try {
            loadPropertyFile(propertyFile);

            String factoryName = (String) pFactories.get(type);

            Class cl = Class.forName(factoryName);

            if (log.isDebugEnabled()) {
                log.debug("Successfully loaded factory: " + factoryName);
            }

            obj = cl.newInstance();

        } catch (Exception e) {
            WSRPXHelper.throwX(log, ErrorCodes.PERSISTENT_FACTORY_NOT_FOUND, e);
        }

        return obj;
    }

    /**
     * Loads the content of a properties file into a private Properties object.
     * The properties file to load contains the factory information.
     * 
     * @param propertyFile
     *            name of the property file as string value
     * @throws WSRPException
     */
    private static void loadPropertyFile(String propertyFile) 
    throws WSRPException {

        try {
            // read in .properties-file
            InputStream in = getThisClass().getClassLoader().getResourceAsStream(propertyFile);
            pFactories = new Properties();
            pFactories.load(in);
            
            if (log.isDebugEnabled()) {
                log.debug("Successfully loaded property file: " + propertyFile);
            }

        } catch (Exception e) {
            WSRPXHelper.throwX(log, ErrorCodes.PROPERTY_FILE_NOT_FOUND, e);
        }
    }

    /**
     * Returns the class object of PersistentrAccess
     * 
     * @return java.lang.Class
     */
    private static Class getThisClass() {

        return PersistentAccess.class;

    }
}
