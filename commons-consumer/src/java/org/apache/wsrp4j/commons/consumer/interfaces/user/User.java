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
package org.apache.wsrp4j.commons.consumer.interfaces.user;

import oasis.names.tc.wsrp.v1.types.UserContext;

/**
 * This interface defines a user with a certain
 * user id and a user context.
 * 
 * @version $Id: User.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface User {

    /**
     * Returns the userID
     * 
     * @return _userID
     */
    String getUserID();

    /**
     * Sets the userID
     * 
     * @param userID as String
     */
    void setUserID(String userID);

    /**
     * Returns the UserContext for this userid
     * 
     * @return _userContext 
     */
    UserContext getUserContext();

    /**
     * Sets the UserContext for this userID
     *
     * @param userContext 
     */
    void setUserContext(UserContext userContext);
}
