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

import org.apache.wsrp4j.commons.exception.WSRPException;

/**
 * Interface of a session handler on the consumer side.
 * 
 * @version $Id: SessionHandler.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface SessionHandler {

    /**
     * Get the session manager of the user session with the given user ID and 
     * producer ID.
     * 
     * @return The user session object representing a session between an 
     * end-user and a producer.
     **/
    UserSessionMgr getUserSession(String producerID, String userID) 
    throws WSRPException;
}
