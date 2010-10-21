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
package org.apache.wsrp4j.consumer.proxyportlet.impl;

import org.apache.wsrp4j.commons.consumer.driver.GenericPersistentProducerRegistryImpl;
import org.apache.wsrp4j.commons.consumer.interfaces.producer.ProducerRegistry;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.persistence.ProxyPersistentFactory;
import org.apache.wsrp4j.commons.persistence.driver.PersistentAccess;
import org.apache.wsrp4j.persistence.xml.driver.ProxyPersistentFactoryImpl;

/**
 * @version $Id: ProducerRegistryImpl.java 381851 2006-03-01 00:42:29Z jmacna $
 */
public class ProducerRegistryImpl extends GenericPersistentProducerRegistryImpl {

    private static ProducerRegistry instance;

    private ProducerRegistryImpl(String path) {

        super();

        // restore producers from persistent file store
        try {

            ProxyPersistentFactory persistentFactory = PersistentAccess.getProxyPersistentFactory();
            ((ProxyPersistentFactoryImpl)persistentFactory).setPath(path);
            persistentHandler = persistentFactory.getPersistentHandler();
            persistentDataObject = persistentFactory.getProducerList();

            restore();

        } catch (Throwable t) {

            t.printStackTrace();

            if (t instanceof WSRPException) {

                // TODO: throw exception
            } else {

                // TODO: throw exception
            }
        }
    }

    /**
     * Get an instance of the singleton producer registry object
     */
    public static ProducerRegistry getInstance(String path) {
        if (instance == null) {
            instance = new ProducerRegistryImpl(path);
        }

        return instance;
    }
}
