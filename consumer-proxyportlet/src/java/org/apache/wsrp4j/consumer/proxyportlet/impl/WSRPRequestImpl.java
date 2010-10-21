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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oasis.names.tc.wsrp.v1.types.ClientData;
import oasis.names.tc.wsrp.v1.types.MarkupContext;
import oasis.names.tc.wsrp.v1.types.NamedString;
import oasis.names.tc.wsrp.v1.types.SessionContext;

//import org.apache.pluto.core.CoreUtils;
//import org.apache.pluto.om.portlet.ContentType;
//import org.apache.pluto.om.portlet.ContentTypeSet;
//import org.apache.pluto.om.window.PortletWindow;
import org.apache.wsrp4j.commons.consumer.interfaces.request.InteractionRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.request.MarkupRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.session.
        PortletWindowSession;
import org.apache.wsrp4j.commons.consumer.driver.GenericWSRPBaseRequestImpl;

import org.apache.wsrp4j.commons.util.AuthenticationInfoHelper;
import org.apache.wsrp4j.commons.util.Constants;
import org.apache.wsrp4j.commons.util.LocaleHelper;
import org.apache.wsrp4j.commons.util.Modes;
import org.apache.wsrp4j.commons.util.WindowStates;
import org.apache.wsrp4j.commons.util.Utility;


/**
 * @version $Id: WSRPRequestImpl.java 408858 2006-05-23 09:34:31Z dlouzan $
 */
public class WSRPRequestImpl extends GenericWSRPBaseRequestImpl
        implements InteractionRequest, MarkupRequest {
    
    protected final static Log log = LogFactory.getLog(WSRPRequestImpl.class);
    
    private final PortletRequest portletRequest;
    private final PortletWindowSession windowSession;
    // private final PortletWindow portletWindow;
    //private final String nameSpace;
    private final String userAuth;
    
    private NamedString[] formParameters;
    private String interactionState;
    private String currentMode;
    private String currentState;
    private String naviState;
    
    //  just for performance reasons we cache this info
    private String[] modes;
    private String[] locales;
    
    
    public WSRPRequestImpl(PortletWindowSession windowSession, 
            javax.portlet.PortletRequest portletRequest) {
        
        this.windowSession = windowSession;
        this.portletRequest = portletRequest;
        
//        this.portletWindow =
//                CoreUtils.getInternalRequest(portletRequest).
//                getInternalPortletWindow();
//        //this.nameSpace =
        //        NamespaceMapperAccess.getNamespaceMapper().
        //        encode(portletWindow.getId(), "");
        this.userAuth = 
                AuthenticationInfoHelper.
                getWsrpFromPortlet(portletRequest.getAuthType());
        
        integrateParameter();
    }
    
    public String getInteractionState() {
        return interactionState;
    }
    
    public NamedString[] getFormParameters() {
        return formParameters;
    }
    
    public MarkupContext getCachedMarkup() {
        if (windowSession == null)
            return null;
        
        return windowSession.getCachedMarkup();
    }
    
    public String getSessionID() {
        if (this.windowSession != null) {
            SessionContext sessionCtx = 
                    this.windowSession.getPortletSession().getSessionContext();
            if (sessionCtx != null) {
                return sessionCtx.getSessionID();
                
            }
        }
        
        return null;
    }
    
    public String getPortletInstanceKey() {
        return IdHelper.getPortletEntityID(portletRequest);
    }
    
    public String getNavigationalState() {
        return naviState;
    }
    
    public String getWindowState() {
        if (currentState == null) {
            //map portlet window states to wsrp:window states
            javax.portlet.WindowState portletWindowState = 
                    portletRequest.getWindowState();
            currentState = 
                    WindowStates.
                    getWsrpStateFromJsrPortletState(portletWindowState).
                    toString();
        }
        
        return currentState;
    }
    
    public String getMode() {
        if (currentMode == null) {
            //map jsr-168 modes to wsrp:modes
            PortletMode portletMode = portletRequest.getPortletMode();
            currentMode = 
                    Modes.getWsrpModeFromJsrPortletMode(portletMode).toString();
        }
        
        return currentMode;
    }
    
    public ClientData getClientData() {
        // TODO: need to find out the client data here
        return null;
    }
    
    public String[] getLocales() {
        if (this.locales == null) {
            Enumeration eLocales = portletRequest.getLocales();
            List wsrpLocales = new ArrayList();
            while (eLocales.hasMoreElements()) {
                Locale locale = (Locale)eLocales.nextElement();
                wsrpLocales.add(LocaleHelper.getWsrpLocale(locale));
            }
            
            locales = (String[])wsrpLocales.toArray(new String[0]);
        }
        
        return locales;
    }
    
    public String[] getModes() {
        
        final String MN = "getModes()";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        if (this.modes != null) {
            if (log.isDebugEnabled()) {
                log.debug(Utility.strExit(MN));
            }
            
            return this.modes;
        }
        
        Set result = new HashSet();
        String[] mimeTypes = getMimeTypes();
        
/*
        if (mimeTypes != null) {
            ContentTypeSet contentTypes =
                    this.portletWindow.getPortletEntity().
                    getPortletDefinition().getContentTypeSet();
            for (int i = 0; i < mimeTypes.length; i++) {
                if (mimeTypes[i].equals("*")) {
                    Iterator it = contentTypes.iterator();
                    while (it.hasNext()) {
                        ContentType type = (ContentType)it.next();
                        Iterator modesIt = type.getPortletModes();
                        while (modesIt.hasNext()) {
                            PortletMode mode = (PortletMode)modesIt.next();
                            result.add(
                                    Modes.getWsrpModeFromJsrPortletMode(mode).
                                    toString());
                        }
                    }
                    
                    break;
                }
                
                ContentType type = contentTypes.get(mimeTypes[i]);
                if (type != null) {
                    Iterator modesIt = type.getPortletModes();
                    while (modesIt.hasNext()) {
                        PortletMode mode = (PortletMode)modesIt.next();
                        result.add(
                                Modes.getWsrpModeFromJsrPortletMode(mode).
                                toString());
                    }
                }
            }
        }
        */
        
        if (!result.isEmpty()) {
            this.modes = (String[])result.toArray(new String[0]);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return this.modes;
    }
    
    public String[] getWindowStates() {
        // TODO: for now we simply return what we know
        //       we should return what our environment supports
        return WindowStates.getWindowStatesAsStringArray();
    }
    
    public String[] getMimeTypes() {
        // TODO: return whatever our environment supports
        return null;
    }
    
    public String[] getCharacterEncodingSet() {
        // TODO: return whatever our environment supports
        return null;
    }
    
    public boolean isModeSupported(String wsrpMode) {
        if (wsrpMode == null)
            throw new IllegalArgumentException("A mode must not be null");
        
        return portletRequest.isPortletModeAllowed(
                Modes.getJsrPortletModeFromWsrpMode(
                Modes.fromString(wsrpMode)));
    }
    
    public boolean isWindowStateSupported(String wsrpWindowState) {
        if (wsrpWindowState == null)
            throw new IllegalArgumentException("A window state must not be " +
                    "null");
        
        return portletRequest.isWindowStateAllowed(
                WindowStates.getJsrPortletStateFromWsrpState(
                WindowStates.fromString(wsrpWindowState)));
    }
    
    public String getUserAuthentication() {
        return this.userAuth;
    }
    
    private void integrateParameter() {
        
        final String MN = "integrateParameter";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        ArrayList formParams = new ArrayList();
        Set paramKeysSet = portletRequest.getParameterMap().keySet();
        
        // interaction state
        this.interactionState = portletRequest.getParameter(
                Constants.INTERACTION_STATE);
        
        // check for navistate
        // if navistate is stored as url parameter take this
        // otherwise look for render param
        this.naviState = portletRequest.getParameter(
                Constants.NAVIGATIONAL_STATE);
        if (this.naviState == null) {
            this.naviState = portletRequest.getParameter(
                    ProxyPortlet.NAVIGATIONAL_STATE);
        }
        
        Iterator paramKeys = paramKeysSet.iterator();
        while (paramKeys.hasNext()) {
            String key = (String)paramKeys.next();
            
            if (!Constants.isWsrpURLParam(key) && !key.equals(
                    ProxyPortlet.NAVIGATIONAL_STATE)) {
                // the rest are form parameter
                String[] values = portletRequest.getParameterValues(key);
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        NamedString paramPair = new NamedString();
                        paramPair.setName(key);
                        paramPair.setValue(values[i]);
                        
                        formParams.add(paramPair);
                    }
                }
            }
        }
        
        if (!formParams.isEmpty()) {
            formParameters = new NamedString[formParams.size()];
            formParams.toArray(formParameters);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
    }
    
}
