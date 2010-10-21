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

import java.util.Hashtable;
import java.util.Map;

import javax.portlet.PortletSession;

import oasis.names.tc.wsrp.v1.intf.WSRPV1MarkupPortType;
import org.apache.wsrp4j.commons.consumer.interfaces.session.GroupSessionMgr;
import org.apache.wsrp4j.commons.consumer.driver.GenericUserSessionImpl;
import org.apache.wsrp4j.commons.consumer.util.ConsumerConstants;
import org.apache.wsrp4j.commons.exception.WSRPException;

/**
 * @version $Id: UserSessionImpl.java 374677 2006-02-03 14:28:31Z cziegeler $
 */
public class UserSessionImpl extends GenericUserSessionImpl {

    private PortletSession portletSession;

    private Map userSession;

    public UserSessionImpl(String producerID, String userID, String portletServicesURL,
            PortletSession portletSession) throws WSRPException {

        super(producerID, userID, portletServicesURL);

        this.portletSession = portletSession;
        userSession = getUserSessionMap();
        setGroupSessionTable(getGroupSessionTable());
    }

    private Hashtable getGroupSessionTable() {

        if ((groupSessions = (Hashtable) userSession.get(ConsumerConstants.WSRP_GROUPSESSIONS)) == null) {
            groupSessions = new Hashtable();
            userSession.put(ConsumerConstants.WSRP_GROUPSESSIONS, groupSessions);
        }

        return groupSessions;
    }

    /**
     * Get the group session for this group ID
     * 
     * @param groupID
     *            ID of the portlet application
     * @return The group session for the provided group ID
     */
    public GroupSessionMgr getGroupSession(String groupID) throws WSRPException {
        if (groupID != null) {
            GroupSessionMgr groupSession = (GroupSessionMgr) this.groupSessions.get(groupID);
            if (groupSession == null) {

                groupSession = new GroupSessionImpl(groupID, this.getMarkupInterfaceURL());
                addGroupSession(groupSession);
            }

            return groupSession;
        }
        return null;
    }

    public WSRPV1MarkupPortType getWSRPBaseService() {
        WSRPV1MarkupPortType markupPort = null;
        if ((markupPort = (WSRPV1MarkupPortType) userSession
                .get(ConsumerConstants.WSRP_MARKUP_PORT)) == null) {
            markupPort = super.getWSRPBaseService();
            userSession.put(ConsumerConstants.WSRP_MARKUP_PORT, markupPort);
        }

        this.setWSRPBaseService(markupPort);
        return markupPort;
    }

    public boolean isInitCookieRequired() {
        Boolean initCookieReq = null;
        if ((initCookieReq = (Boolean) userSession.get(ConsumerConstants.WSRP_INIT_COOKIE_REQ)) == null) {
            initCookieReq = new Boolean(super.isInitCookieRequired());
            setInitCookieRequired(initCookieReq.booleanValue());
        }

        return initCookieReq.booleanValue();
    }

    public void setInitCookieRequired(boolean initCookieRequired) {
        userSession.put(ConsumerConstants.WSRP_INIT_COOKIE_REQ, new Boolean(initCookieRequired));
        super.setInitCookieRequired(initCookieRequired);
    }

    public boolean isInitCookieDone() {
        Boolean initCookieDone = null;
        if ((initCookieDone = (Boolean) userSession.get(ConsumerConstants.WSRP_INIT_COOKIE_DONE)) == null) {
            initCookieDone = new Boolean(super.isInitCookieDone());
            setInitCookieDone(initCookieDone.booleanValue());
        }

        return initCookieDone.booleanValue();
    }

    public void setInitCookieDone(boolean initCookieDone) {
        userSession.put(ConsumerConstants.WSRP_INIT_COOKIE_DONE, new Boolean(initCookieDone));
        super.setInitCookieRequired(initCookieDone);
    }

    private Map getUserSessionMap() {
        String key = createKey();
        Map myMap = (Map) this.portletSession.getAttribute(key, PortletSession.APPLICATION_SCOPE);

        if (myMap == null) {
            myMap = new Hashtable();
            this.portletSession.setAttribute(key, myMap, PortletSession.APPLICATION_SCOPE);
        }

        return myMap;
    }

    private String createKey() {
        return "user :" + this.getUserID() + " producer:" + this.getProducerID();
    }
}
