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

import oasis.names.tc.wsrp.v1.types.PortletContext;
import org.apache.wsrp4j.commons.consumer.interfaces.portlet.PortletKey;

/**
 * @version $Id: ConsumerPortletContext.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public class ConsumerPortletContext {
    
    private PortletKey portletKey;
    
    private PortletContext portletContext;
    
    // for castor persistence
    public ConsumerPortletContext() {
        
    }
    
    public PortletKey getPortletKey() {
        return this.portletKey;
    }
    
    public void setPortletKey(PortletKey portletKey) {
        
        if (portletKey != null) {
            this.portletKey = portletKey;
            
        }
    }
    
    public void setPortletContext(PortletContext portletContext) {
        if (portletContext != null) {
            this.portletContext = portletContext;
        }
    }
    
    public PortletContext getPortletContext() {
        return this.portletContext;
    }
    
}
