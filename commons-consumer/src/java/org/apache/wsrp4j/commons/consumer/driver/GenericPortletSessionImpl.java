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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.util.Utility;

import org.apache.wsrp4j.commons.consumer.interfaces.session.PortletWindowSession;
import org.apache.wsrp4j.commons.consumer.interfaces.session.PortletSession;

import oasis.names.tc.wsrp.v1.types.SessionContext;

/**
 * @version $Id: GenericPortletSessionImpl.java 374672 2006-02-03 14:10:58Z
 *          cziegeler $
 */
public abstract class GenericPortletSessionImpl implements PortletSession {

    private static final Log log = LogFactory.getLog(GenericPortletSessionImpl.class);
    
    // the session context passed from the producer to store
    private SessionContext sessionCtx;

    // the portlet handle identifying the where the session belogns to
    private String handle;

    // holds the varios window sessions for this portlet instance
    protected final Map windowSessions;


    public GenericPortletSessionImpl(String handle) {
        final String MN = "GenericPortletSessionImpl";

        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }

        this.windowSessions = new HashMap();
        this.handle = handle;

        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
    }

    public String getPortletHandle() {
        return handle;
    }

    public void setPortletHandle(String handle) {
        if (handle != null) {
            this.handle = handle;
        }
    }

    public SessionContext getSessionContext() {
        return sessionCtx;
    }

    public void setSessionContext(SessionContext ctx) {
        this.sessionCtx = ctx;
    }

    /**
     * Get the <code>PortletWindowSession</code> of the portlet window with
     * the given ID.
     * 
     * @param windowID
     *            The ID of the portlet window
     * @return The <code>PorletWindowSession</code> with the given ID.
     */
    public abstract PortletWindowSession getPortletWindowSession(String windowID);

    /**
     * Get all window session which belong to the portlet session
     * 
     * @return An Iterator of <code>SimplePortletWindowSession</code> objects.
     */
    public Iterator getAllPorletWindowSessions() {

        return this.windowSessions.entrySet().iterator();
    }

    /**
     * Remove the porlet window session with the given window id.
     * 
     * @param windowID
     *            The ID of the portlet window whichs session shoul dbe removed
     * @return The portlet window session which has been removed or null if the
     *         session did not exist.
     */
    public PortletWindowSession removePortletWindowSession(String windowID) {
        final String MN = "getPortletWindowSession";

        PortletWindowSession winSession =
                (PortletWindowSession) this.windowSessions.remove(windowID);

        if (log.isDebugEnabled() && winSession != null) {
            log.debug("Removed PortletWindowSession with ID: " + windowID);
        }

        return winSession;
    }

    /**
     * Remove all portlet window sessions which belong to this portlet session.
     */
    public void removeAllPortletWindowSessions() {
        this.windowSessions.clear();
    }
    
}
