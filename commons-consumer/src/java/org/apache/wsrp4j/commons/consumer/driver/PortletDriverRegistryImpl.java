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
package org.apache.wsrp4j.commons.consumer.driver;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.wsrp4j.commons.consumer.interfaces.consumer.ConsumerEnvironment;
import org.apache.wsrp4j.commons.consumer.interfaces.portletdriver.PortletDriver;
import org.apache.wsrp4j.commons.consumer.interfaces.portletdriver.PortletDriverRegistry;
import org.apache.wsrp4j.commons.consumer.interfaces.portlet.WSRPPortlet;
import org.apache.wsrp4j.commons.exception.WSRPException;

/**
 * @version $Id: PortletDriverRegistryImpl.java 374672 2006-02-03 14:10:58Z
 *          cziegeler $
 */
public class PortletDriverRegistryImpl implements PortletDriverRegistry {

    private static PortletDriverRegistry instance;

    private Hashtable portletDrivers;

    private ConsumerEnvironment consumerEnv;

    private PortletDriverRegistryImpl(ConsumerEnvironment consumerEnv) {

        this.portletDrivers = new Hashtable();
        this.consumerEnv = consumerEnv;
    }

    public static PortletDriverRegistry getInstance(ConsumerEnvironment consumerEnv) {
        if (instance == null) {
            instance = new PortletDriverRegistryImpl(consumerEnv);
        }

        return instance;
    }

    /**
     * Get an portlet driver for the given portlet. If there is no portlet
     * driver object cached a new portlet driver will be created and returned.
     * 
     * @param portlet
     *            The portlet the returned portlet driver is bind to
     * 
     * @return The portlet driver for this portlet
     */
    public PortletDriver getPortletDriver(WSRPPortlet portlet) throws WSRPException {

        PortletDriver driver = null;

        if ((driver = (PortletDriver) portletDrivers.get(portlet.getPortletKey().toString())) == null) {
            driver = new PortletDriverImpl(portlet, consumerEnv);
            this.portletDrivers.put(portlet.getPortletKey().toString(), driver);
        }

        return driver;
    }

    /**
     * Get all cached portlet drivers.
     * 
     * @return Iterator with all portlet drivers in the registry
     */
    public Iterator getAllPortletDrivers() {
        return portletDrivers.values().iterator();
    }
}
