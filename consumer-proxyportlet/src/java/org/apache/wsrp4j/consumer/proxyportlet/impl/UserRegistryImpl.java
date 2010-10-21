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

import org.apache.wsrp4j.commons.consumer.interfaces.user.UserRegistry;
import org.apache.wsrp4j.commons.consumer.driver.GenericUserRegistryImpl;

/**
 * @version $Id: UserRegistryImpl.java 374677 2006-02-03 14:28:31Z cziegeler $
 */
public class UserRegistryImpl extends GenericUserRegistryImpl {

    private static UserRegistry instance;

    private UserRegistryImpl() {
        super();
    }

    public static UserRegistry getInstance() {
        if (instance == null) {
            instance = new UserRegistryImpl();
        }

        return instance;
    }
}
