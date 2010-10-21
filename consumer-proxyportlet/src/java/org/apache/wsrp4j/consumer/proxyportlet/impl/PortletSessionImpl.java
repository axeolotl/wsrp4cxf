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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.util.Utility;

import org.apache.wsrp4j.commons.consumer.interfaces.session.PortletWindowSession;
import org.apache.wsrp4j.commons.consumer.driver.GenericPortletSessionImpl;
import org.apache.wsrp4j.commons.consumer.driver.GenericPortletWindowSessionImpl;


/**
 * @version $Id: PortletSessionImpl.java 408858 2006-05-23 09:34:31Z dlouzan $
 */
public class PortletSessionImpl extends GenericPortletSessionImpl {

    // logger
    private static final Log log =
            LogFactory.getLog(PortletSessionImpl.class);

    public PortletSessionImpl(String handle) {
        super(handle);
    }

    /**
     * Get the <code>PortletWindowSession</code> of the portlet window with
     * the given ID.
     * 
     * @param windowID
     *            The ID of the portlet window
     * @return The <code>PorletWindowSession</code> with the given ID.
     */
    public PortletWindowSession getPortletWindowSession(String windowID) {
        
        final String MN = "getPortletWindowSession";

        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }

        PortletWindowSession session = (PortletWindowSession) this.windowSessions.get(windowID);
        if (session == null) {
            session = new GenericPortletWindowSessionImpl(windowID, this);
            this.windowSessions.put(windowID, session);

            if (log.isDebugEnabled()) {
                log.debug("Created windowSession with ID: " + windowID);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }

        return session;
        
    }
    
}
