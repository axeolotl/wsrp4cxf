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

package org.apache.wsrp4j.persistence.xml.driver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.util.Utility;

import org.apache.wsrp4j.commons.persistence.PersistentInformationProvider;
import org.apache.wsrp4j.commons.persistence.ProxyPersistentFactory;

import org.apache.wsrp4j.persistence.xml.ProxyPersistentInformationProvider;


public class ProxyPersistentFactoryImpl 
        extends ConsumerPersistentFactoryImpl 
        implements ProxyPersistentFactory {
    
    private final static Log log = 
            LogFactory.getLog(ProxyPersistentFactoryImpl.class);
    
    private String path = null;
    
    
    public PersistentInformationProvider getPersistentInformationProvider() {
        
        String MN = "getProxyPersistentInformationProvider";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        if(consumerInfoProvider == null) {
            consumerInfoProvider =
                    ProxyPersistentInformationProviderImpl.create(path);
        }
        
        if (log.isDebugEnabled()) {
            log.debug("ProxyPersistentInformationProvider successfully " +
                    "created.");
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return (ProxyPersistentInformationProvider) consumerInfoProvider;
        
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
}
