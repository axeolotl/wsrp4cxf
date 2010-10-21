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
package org.apache.wsrp4j.commons.consumer.interfaces.request;

import oasis.names.tc.wsrp.v1.types.ClientData;

/**
 * The <code>WSRPRequest</code> is the base interface for all
 * requests to a consumer side invocation of a integrated remote portlet.
 * Specialized interfaces exist for markup and action calls. 
 *
 * @version $Id: WSRPBaseRequest.java 374672 2006-02-03 14:10:58Z cziegeler $ 
 *
 * @see MarkupRequest
 * @see InteractionRequest
 * 
 */
public interface WSRPBaseRequest {

    /**
     * Get the ID of the session context 
     *  
     * @return The session context
     **/
    String getSessionID();

    /**
     * Get an opaque string which corresponds to a unique reference to this 
     * use of the portlet.
     * 
     * @return The portlet instance key
     **/
    String getPortletInstanceKey();

    /**
     * Get the current navigational state of the portlet
     * 
     * @return The navigational state
     **/
    String getNavigationalState();

    /**
     * Get the current window state of the portlet
     * 
     * @return The window state
     **/
    String getWindowState();

    /**
     * Get the current mode of the portlet
     * 
     * @return The mode of the portlet
     **/
    String getMode();

    /**
     * Get the <code>ClientData</code> structure which carries
     * information about the end user agent.
     * 
     * @return The <code>ClientData</code> specifying the user agent.
     **/
    ClientData getClientData();

    /**
     * Get the locales which are supported by the portlet according to the 
     * client connecting to it.
     * The Locales returned are in the form of (ISO-639 + "-" + ISO-3166)
     * 
     * @return Array with string representations of the locales which are
     * supported by the consumer
     **/
    String[] getLocales();

    /**
     * Get the list of wsrp modes which are supported by the portlet.
     * This should returned the list of all actuall supported modes and
     * not necessarily the modes returned in the portlet description of the 
     * producer. 
     * 
     * @return Array with string representations of the portlet modes
     * supported by the portlet or null
     **/
    String[] getModes();

    /**
     * Get the list of wsrp window states which are supported by the portlet.
     * This should returned the list of all actuall supported window states and
     * not necessarily the window states returned in the portlet description of 
     * the producer. 
     * 
     * @return Array with string representations of the window states
     * supported by the portlet or null
     **/
    String[] getWindowStates();

    /**
     * Get an array of mime types which are supported by the end user device.
     * The order in the array defines the order of preference of the end user.    
     * 
     * @return An array of mimes types the consumer supports or null
     **/
    String[] getMimeTypes();

    /**
     * Get the character sets the consumer wants the remote portlet to use for 
     * encoding the markup.
     * Valid character sets are defined 
     * <a href='http://www.iana.org/assignments/character-sets'>here</a>
     *
     * @return Array of string representations of the character encoding.
     **/
    String[] getCharacterEncodingSet();

    /**
     * Checks wether a given wsrp mode is supported by the portlet.
     * 
     * @param wsrpMode The wsrp mode
     * @return True if the mode is supported by the portlet, false otherwise
     **/
    boolean isModeSupported(String wsrpMode);

    /**
     * Checks wether a given wsrp window state is supported by the portlet.
     * 
     * @param wsrpWindowState The wsrp window state
     * @return True if the window state is supported by the portlet, false 
     * otherwise
     **/
    boolean isWindowStateSupported(String wsrpWindowState);

    /**
     * Get the method which is used by the consumer to authenticate its users.
     *      
     * @return String indicating how end-users were authenticated by the 
     * consumer.
     **/
    String getUserAuthentication();
}
