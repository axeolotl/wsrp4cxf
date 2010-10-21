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

import oasis.names.tc.wsrp.v1.types.PortletContext;

/**
 * Defines a consumer-side representation of a remote portlet.
 * A portlet is uniquely identified by its portlet key.
 * Consumer configured portlets are the result of clone operations on
 * existing portlets (parents).
 * 
 * @version $Id: WSRPPortlet.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface WSRPPortlet {

    /**
     * Get the portlet key of the portlet. The key can be used to reference
     * to the portlet.
     * 
     * @return a portlet key object
     **/
    PortletKey getPortletKey();

    /**
     * Set the portlet key of the portlet.
     * 
     * @param portletKey The portlet key of the portlet
     **/
    void setPortletKey(PortletKey portletKey);

    /**
     * Get the portlet context object which contains information about
     * the portlet state.
     * 
     * @return the portlet context object of the portlet.
     **/
    PortletContext getPortletContext();

    /**
     * Set the portlet context of the portlet.
     * 
     * @param portletContext The portlet context of the portlet
     **/
    void setPortletContext(PortletContext portletContext);

    /**
     * Checks if a portlet is consumer configured portlet.
     * 
     * @return True if the result <code>getParent()</code> is not equal 
     *         to the portlet handle of the portlet key.
     **/
    boolean isConsumerConfigured();

    /**
     * Get the portlet handle of the parent portlet. If the portlet
     * is not a consumer configured portlet the handle returned by this method
     * should be the same as the handle in the portlet key returned 
     * by <code>getPortletKey</code>.
     * 
     * @return the portlet handle of the parent portlet.
     **/
    String getParent();

    /**
     * Set the portlet handle of the parent portlet. If the supplied 
     * handle is not equal to the handle in the portlet key returned by 
     * <code>getPortletKey</code> this method makes the portlet a 
     * consumer configured portlet.
     * 
     * @param portletHandle the portlet handle of the parent portlet
     **/
    void setParent(String portletHandle);
}
