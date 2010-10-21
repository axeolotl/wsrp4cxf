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

import org.apache.wsrp4j.commons.consumer.interfaces.portlet.PortletKey;

/**
 * @version $Id: PortletKeyImpl.java 374679 2006-02-03 14:33:03Z cziegeler $
 */
public class PortletKeyImpl implements PortletKey {

    private String portletHandle;

    private String producerId;

    // constructor for castor persistence
    public PortletKeyImpl() {
    }

    public PortletKeyImpl(String portletHandle, String producerId) {
        this.portletHandle = portletHandle;
        this.producerId = producerId;
    }

    /**
     * Get the ID of the producer providing the portlet
     * 
     * @return The ID of the producer
     */
    public String getProducerId() {
        return producerId;
    }

    /**
     * Set the ID of the producer providing the portlet
     * 
     * @param id
     *            The ID of the producer
     */
    public void setProducerId(String id) {
        producerId = id;
    }

    /**
     * Get the portlet handle which identifies an portlet in the scope of one
     * producer
     * 
     * @return The portlet handle
     */
    public String getPortletHandle() {
        return this.portletHandle;
    }

    /**
     * Set the portlet handle which identifies an portlet in the scope of one
     * producer
     * 
     * @param portletHandle
     *            The portlet handle
     */
    public void setPortletHandle(String portletHandle) {
        if (portletHandle != null) {
            this.portletHandle = portletHandle;
        }
    }

    /**
     * 
     * 
     */
    public String toString() {

        String retVal = producerId + "_" + portletHandle;
        return retVal;
    }

}
