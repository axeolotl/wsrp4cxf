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

import java.util.Iterator;
import org.apache.wsrp4j.commons.exception.WSRPException;

/**
 * This interface defines a registry which holds portlet objects.
 *
 * @version $Id: PortletRegistry.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface PortletRegistry {

    /**
     * Add a portlet to the registry
     *
     * @param portlet The portlet to add
     */
    void addPortlet(WSRPPortlet portlet) throws WSRPException;

    /**
     * Get the portlet for the given producer and portlet handle
     *
     * @param portletKey The portlet key identifying the portlet
     *
     * @return The portlet with the given portlet key
     **/
    WSRPPortlet getPortlet(PortletKey portletKey);

    /**
     * Remove the portlet with the given portlet key
     *
     * @param portletKey The portlet key identifying the portlet
     * @return The portlet which has been removed or null
     **/
    WSRPPortlet removePortlet(PortletKey portletKey);

    /**
     * Tests if a portlet with the given portlet key
     * 
     * @param portletKey The portlet key identifying the portlet
     * 
     * @return True if portlet exists with this portlet key
     **/
    boolean existsPortlet(PortletKey portletKey);

    /**
     * Get all the portlets in the register
     * 
     * @return Iterator with all portlets in the registry
     **/
    Iterator getAllPortlets();

    /**
     * Remove all portlets from the registry
     **/
    void removeAllPortlets();

}
