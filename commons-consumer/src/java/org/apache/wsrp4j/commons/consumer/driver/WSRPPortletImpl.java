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
import org.apache.wsrp4j.commons.consumer.interfaces.portlet.WSRPPortlet;
import org.apache.wsrp4j.commons.util.impl.StateChangedServiceImpl;

/**
 * Implements the portlet interface
 * @version $Id: WSRPPortletImpl.java 374672 2006-02-03 14:10:58Z cziegeler $
 **/
public class WSRPPortletImpl extends StateChangedServiceImpl 
        implements WSRPPortlet {
    
    private PortletKey portletKey;
    private PortletContext portletContext;
    private String parentHandle;
    
    // for castor persistence
    public WSRPPortletImpl() {
    }
    
    public WSRPPortletImpl(PortletKey portletKey) {
        this.portletKey = portletKey;
        this.parentHandle = portletKey.getPortletHandle();
    }
    
    public PortletKey getPortletKey() {
        return this.portletKey;
    }
    
    public void setPortletKey(PortletKey portletKey) {
        if (portletKey != null) {
            this.portletKey = portletKey;
            
            if (this.portletContext != null) {
                this.portletContext.setPortletHandle(
                        portletKey.getPortletHandle());
            }
            
            if (parentHandle == null) {
                parentHandle = portletKey.getPortletHandle();
            }
            
            stateChanged();
        }
    }
    
    public void setPortletContext(PortletContext portletContext) {
        if (portletContext != null) {
            this.portletContext = portletContext;
            this.portletKey.setPortletHandle(portletContext.getPortletHandle());
            
            stateChanged();
        }
    }
    
    public PortletContext getPortletContext() {
        return this.portletContext;
    }
    
    public String getParent() {
        return this.parentHandle;
    }
    
    public void setParent(String portletHandle) {
        this.parentHandle = portletHandle;
        
        stateChanged();
    }
    
    public boolean isConsumerConfigured() {
        
        return !getParent().equals(portletKey.getPortletHandle());
    }
}
