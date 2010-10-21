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
package org.apache.wsrp4j.commons.consumer.interfaces.portletdriver;

import oasis.names.tc.wsrp.v1.types.BlockingInteractionResponse;
import oasis.names.tc.wsrp.v1.types.DestroyPortletsResponse;
import oasis.names.tc.wsrp.v1.types.MarkupResponse;
import oasis.names.tc.wsrp.v1.types.PortletContext;
import oasis.names.tc.wsrp.v1.types.PortletDescriptionResponse;
import oasis.names.tc.wsrp.v1.types.PortletPropertyDescriptionResponse;
import oasis.names.tc.wsrp.v1.types.PropertyList;
import oasis.names.tc.wsrp.v1.types.ReturnAny;

import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.consumer.interfaces.request.InteractionRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.request.MarkupRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.portlet.WSRPPortlet;

/**
 * The portlet driver is a wrapper for all action which can be performed on an
 * portlet. There is one portlet driver for all instances of an portlet.
 *
 * @version $Id: PortletDriver.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface PortletDriver {

    /**
     * Get the portlet this driver is bound to.
     * 
     * @return The enity
     **/
    WSRPPortlet getPortlet();

    /**
     * This method is used to retrieve the markup generated by the portlet 
     * instance.
     * 
     * @param markupRequest The markup request 
     * @return The markup response generated by portlet
     **/
    MarkupResponse getMarkup(MarkupRequest markupRequest, String userID) 
    throws WSRPException;

    /**
     * This method is used to perform a blocking interaction on the portlet 
     * instance.
     * 
     * @param actionRequest The interaction request 
     **/
    BlockingInteractionResponse performBlockingInteraction(
        InteractionRequest actionRequest,
        String userID)
        throws WSRPException;

    /**
     * Clone the portlet
     * 
     * @return The new portlet context
     **/
    PortletContext clonePortlet(String userID) throws WSRPException;

    /**
     * 
     **/
    void initCookie() throws WSRPException;

    /**
     * Destroy the producer portlets specified in the entiyHandles array.    
     **/
    DestroyPortletsResponse destroyPortlets(String[] portletHandles, 
            String userID) throws WSRPException;

    /**
     * Inform the producer that the sessions specified in the sessionIDs array
     * will no longer be used by the consumer and can therefor be released.     
     **/
    ReturnAny releaseSessions(String[] sessionIDs, String userID) 
    throws WSRPException;

    /**
     * Fetches information about the portlet from the producer. 
     * 
     * @param userID is used to get the user context of the user from the user 
     * registry
     * @param desiredLocales Array of locales the description should be 
     * provided
     * @return The response to the getPortletDescription call.
     **/
    PortletDescriptionResponse getPortletDescription(String userID, 
            String[] desiredLocales) throws WSRPException;

    /**
     * Fetches all published properties of an remote portlet.
     * 
     * @param userID The ID of the user this request is done for
     * 
     * @return The portlet property description response from the producer
     **/
    PortletPropertyDescriptionResponse getPortletPropertyDescription(
            String userID) throws WSRPException;

    /**
     * Get the current values of the properties with the given names.
     * 
     * @param names The names of the properties
     * @param userID The ID of the user is used to get the user context
     * 
     * @return A list of properties containing the values and names of the 
     * properties.
     **/
    PropertyList getPortletProperties(String[] names, String userID) 
    throws WSRPException;

    /**
     * Set the portlet properties specified in the property list
     * 
     * @param properties List of properties to be set.
     * @param userID The ID of the user is used to get the user context
     **/
    PortletContext setPortletProperties(PropertyList properties, String userID) 
    throws WSRPException;
}
