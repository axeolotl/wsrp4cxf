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
package org.apache.wsrp4j.commons.consumer.interfaces.portlet;

/**
 * Defines the information which uniquely identifies an portlet provided by a 
 * producer.
 * The portlet key does not identify a use of an portlet
 * 
 * @version $Id: PortletKey.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface PortletKey {

    /**
     * Get the portlet handle which identifies an portlet in the scope of one 
     * producer
     * 
     * @return The portlet handle
     **/
    String getPortletHandle();

    /**
     * Set the portlet handle which identifies an portlet in the scope of 
     * one producer
     * 
     * @param portletHandle The portlet handle
     **/
    void setPortletHandle(String portletHandle);

    /**
     * Get the ID of the producer providing the portlet
     * 
     * @return The ID of the producer
     **/
    String getProducerId();

    /**
     * Set the ID of the producer providing the portlet
     * 
     * @param id The ID of the producer
     **/
    void setProducerId(String id);
}
