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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.util.Utility;

import org.apache.wsrp4j.commons.consumer.interfaces.request.WSRPBaseRequest;

import oasis.names.tc.wsrp.v1.types.ClientData;

/**
 * @version $Id: GenericWSRPBaseRequestImpl.java 405796 2006-05-10 16:48:00Z dlouzan $
 */
public abstract class GenericWSRPBaseRequestImpl implements WSRPBaseRequest {
    
    protected final static Log log = 
            LogFactory.getLog(GenericWSRPBaseRequestImpl.class);
    
    public abstract String getSessionID();

    public abstract String getPortletInstanceKey();

    public abstract String getNavigationalState();
    
    public abstract String getWindowState();
    
    public abstract String getMode();
    
    public abstract ClientData getClientData();
    
    public abstract String[] getLocales();
    
    public abstract String[] getModes();
    
    public abstract String[] getWindowStates();
    
    public abstract String[] getMimeTypes();
    
    public abstract String[] getCharacterEncodingSet();
    
    public boolean isModeSupported(String wsrpMode) {
        final String MN = "isModeSupported(String)";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        if (wsrpMode == null)
            throw new IllegalArgumentException("mode must not be null");
        
        String[] mods = getModes();
        for (int i = 0; i < mods.length; i++) {
            if (wsrpMode.equalsIgnoreCase(mods[i])) {
                return true;
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return false;
    }
    
    public boolean isWindowStateSupported(String wsrpWindowState) {
        final String MN = "isWindowStateSupported(String)";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        if (wsrpWindowState == null)
            throw new IllegalArgumentException("window state must not be null");
        
        String[] stats = getWindowStates();
        for (int i = 0; i < stats.length; i++) {
            if (wsrpWindowState.equalsIgnoreCase(stats[i])) {
                return true;
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return false;
    }
    
    public abstract String getUserAuthentication();
    
}
