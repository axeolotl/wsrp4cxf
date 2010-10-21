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
package org.apache.wsrp4j.commons.consumer.interfaces.session;

import java.util.Iterator;

import oasis.names.tc.wsrp.v1.types.SessionContext;

/**
 * Defines a session object at the consumer-side
 * to store remote portlet related information that
 * are needed to interact with the portlet. 
 * Since a session context is not mandatory in WSRP
 * a consumer portlet instance key is taken to
 * uniquely identify a portlet session.
 * 
 * @version $Id: PortletSession.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface PortletSession {
    
    /**
     * Get the portlet handle which uniquely identifies the
     * portlet session at the consumer side.
     * 
     * @return The portlet handle
     **/
    String getPortletHandle();

    /**
     * Set the portlet handle of the portlet
     * this session belongs to.
     * 
     * @param portletHandle The portlet handle 
     **/
    void setPortletHandle(String portletHandle);

    /**
     * Get the WSRP session context of
     * the portlet instance. If no session context was set
     * from the producer this method returns null.
     * 
     * @return The the session context if set from the producer 
     *         or null otherwise.
     **/
    SessionContext getSessionContext();

    /**
     * Set the session context of the portlet instance.
     * 
     * @param sessionContext The session context.
     **/
    void setSessionContext(SessionContext sessionContext);

    /**
     * Get the <code>SimplePortletWindowSession</code> of the portlet window 
     * with the given ID.
     * 
     * @param windowID The ID of the portlet window
     * @return The <code>PorletWindowSession</code> with the given ID.    
     **/
    PortletWindowSession getPortletWindowSession(String windowID);

    /**
     * Get all window session which belong to the portlet session
     * 
     * @return An Iterator of <code>SimplePortletWindowSession</code> objects.     
     **/
    Iterator getAllPorletWindowSessions();

    /**
     * Remove the porlet window session with the given window id.
     * 
     * @param windowID The ObjectID of the portlet window whichs session should 
     * be removed
     * @return The portlet window session which has been removed or null if the 
     * session did not exist.    
     **/
    PortletWindowSession removePortletWindowSession(String windowID);

    /**
     * Remove all portlet window sessions which  belong to this portlet session.
     **/
    void removeAllPortletWindowSessions();
}
