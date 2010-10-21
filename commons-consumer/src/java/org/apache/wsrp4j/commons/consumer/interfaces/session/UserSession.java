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

import org.apache.wsrp4j.commons.exception.WSRPException;


/**
 * A consumer based session which represents a user session with a certain 
 * producer.
 * This user session contains one or more group sessions.
 * 
 * @see GroupSession
 * 
 * @version $Id: UserSession.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface UserSession {

    /**
     * Get ID of the user this session is bind to
     * 
     * @return User ID
     **/
    String getUserID();

    /**
     * Get ID of the producer this session is bind to
     * 
     * @return ID of the producer
     **/
    String getProducerID();

    /**
     * Get the group session for this group ID
     *     
     * @param groupID ID of the portlet application
     * @return The a group session for the provided group ID or a new 
     * groupSession
     **/
    GroupSessionMgr getGroupSession(String groupID) throws WSRPException;

    /**
      * Get all group session
      *      
      * @return Iterator with all group sessions for the given producer 
      * access point
      **/
    Iterator getAllGroupSessions();

    /**
     * Set the ID of the user this session is bind to 
     * 
     * @param userID ID of the user
     **/
    void setUserID(String userID);

    /**
     * Set the ID of the producer this session is bind to.
     * 
     * @param producerID of the producer
     **/
    void setProducerID(String producerID);

    /**
     * Add a group session to the user session
     * 
     * @param groupSession A group session
     **/
    void addGroupSession(GroupSession groupSession);

    /**
     * Remove a group session from the user session
     *      
     * @param groupID ID of the portlet application
     **/
    void removeGroupSession(String groupID);

    /**
     * Remove all group sessions
     *      
     **/
    void removeAllGroupSessions();
}
