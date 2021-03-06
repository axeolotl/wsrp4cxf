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

import org.apache.wsrp4j.commons.consumer.interfaces.portlet.PortletRegistry;
import org.apache.wsrp4j.commons.consumer.driver.GenericPortletRegistryImpl;

/**
 * @version $Id: PortletRegistryImpl.java 374677 2006-02-03 14:28:31Z cziegeler $
 */
public class PortletRegistryImpl extends GenericPortletRegistryImpl {

    private static PortletRegistry instance;

    private PortletRegistryImpl() {
        super();
    }

    public static PortletRegistry getInstance() {
        if (instance == null) {
            instance = new PortletRegistryImpl();
        }

        return instance;
    }
}
