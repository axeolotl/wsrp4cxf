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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.consumer.interfaces.session.GroupSessionMgr;
import org.apache.wsrp4j.commons.consumer.interfaces.session.PortletSession;

import org.apache.wsrp4j.commons.util.Utility;
import org.apache.wsrp4j.commons.exception.WSRPException;


/**
 * Class implements a consumer based group session
 * 
 * @version $Id: GenericGroupSessionImpl.java 374672 2006-02-03 14:10:58Z
 *          cziegeler $
 */
public abstract class GenericGroupSessionImpl extends InitCookieInfoImpl implements GroupSessionMgr {

    private static final Log log = LogFactory.getLog(GenericGroupSessionImpl.class);
    
    // ID of the group this session is bind to
    private String groupID;

    // holding all the portlet session objects for this group
    protected Hashtable portletSessions;
    

    public GenericGroupSessionImpl(String groupID, String markupInterfaceURL)
    throws WSRPException {
        
        super(markupInterfaceURL);

        final String MN = "GenericGroupSessionImpl";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }

        this.groupID = groupID;
        this.portletSessions = new Hashtable();

        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
    }

    public String getGroupID() {
        return this.groupID;
    }

    public void setGroupID(String groupID) {
        if (groupID != null) {
            this.groupID = groupID;
        }
    }

    public void addPortletSession(PortletSession portletSession) {
        final String MN = "addPortletSession";

        if (portletSession != null) {
            this.portletSessions.put(portletSession.getPortletHandle(), portletSession);
            if (log.isDebugEnabled()) {
                log.debug("Added PortletSession with handle:" +
                        portletSession.getPortletHandle() +
                        " to GroupSession wth ID: " +
                        groupID);
            }
        }
    }

    public abstract PortletSession getPortletSession(String portletHandle);

    public Iterator getAllPortletSessions() {
        return this.portletSessions.values().iterator();
    }

    public void removePortletSession(String portletHandle) {
        final String MN = "removePortletSession";
        if (portletHandle == null) {
            this.portletSessions.remove(portletHandle);
            if (log.isDebugEnabled()) {
                log.debug("Deleted PortletSession with handle: " +
                        portletHandle +
                        "from GroupSession with ID: " +
                        groupID);
            }
        }
    }

    public void removeAllPortletSessions() {
        this.portletSessions.clear();
    }

    public boolean existsPortletSession(String portletHandle) {
        if (portletHandle == null)
            return false;
        return this.portletSessions.containsKey(portletHandle);
    }
    
}