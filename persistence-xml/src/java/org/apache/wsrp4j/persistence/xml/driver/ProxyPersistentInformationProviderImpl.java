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

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.util.Utility;

import org.apache.wsrp4j.persistence.xml.ProxyPersistentInformationProvider;


public class ProxyPersistentInformationProviderImpl  
        extends ConsumerPersistentInformationProviderImpl 
        implements ProxyPersistentInformationProvider {
    
    private final static Log log =
            LogFactory.getLog(ProxyPersistentInformationProviderImpl.class);
    
    public static ProxyPersistentInformationProviderImpl create(String path) {
        
        ProxyPersistentInformationProviderImpl cpip = 
                new ProxyPersistentInformationProviderImpl();
        cpip.init(path);
        
        return cpip;
    }
    
    public void init(String path) {
        
        String MN = "init";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        File file = null;
        setRoot(path);
        setRoot(getRoot() + File.separator + PERSISTENT_DIR);
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
    }
    
}
