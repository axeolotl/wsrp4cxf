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


import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import oasis.names.tc.wsrp.v1.types.BlockingInteractionResponse;
import oasis.names.tc.wsrp.v1.types.MarkupContext;
import oasis.names.tc.wsrp.v1.types.MarkupResponse;
import oasis.names.tc.wsrp.v1.types.PersonName;
import oasis.names.tc.wsrp.v1.types.PortletDescription;
import oasis.names.tc.wsrp.v1.types.SessionContext;
import oasis.names.tc.wsrp.v1.types.UpdateResponse;
import oasis.names.tc.wsrp.v1.types.UserContext;
import oasis.names.tc.wsrp.v1.types.UserProfile;
import oasis.names.tc.wsrp.v1.intf.MissingParameters;


import org.apache.wsrp4j.commons.consumer.interfaces.consumer.
        ConsumerEnvironment;
import org.apache.wsrp4j.commons.consumer.interfaces.request.InteractionRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.request.MarkupRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.portletdriver.
        PortletDriver;
import org.apache.wsrp4j.commons.consumer.interfaces.session.GroupSession;
import org.apache.wsrp4j.commons.consumer.interfaces.session.PortletSession;
import org.apache.wsrp4j.commons.consumer.interfaces.session.
        PortletWindowSession;
import org.apache.wsrp4j.commons.consumer.interfaces.session.UserSession;
import org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer;

import org.apache.wsrp4j.commons.consumer.interfaces.user.User;
import org.apache.wsrp4j.commons.consumer.interfaces.portlet.PortletKey;
import org.apache.wsrp4j.commons.consumer.interfaces.portlet.WSRPPortlet;
import org.apache.wsrp4j.commons.consumer.driver.PortletKeyImpl;
import org.apache.wsrp4j.commons.consumer.driver.UserImpl;
import org.apache.wsrp4j.commons.consumer.driver.WSRPPortletImpl;
import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.URLGenerator;
import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.
        URLTemplateComposer;
import org.apache.wsrp4j.commons.consumer.util.ConsumerConstants;

import org.apache.wsrp4j.consumer.proxyportlet.SessionHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.exception.ErrorCodes;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.exception.WSRPXHelper;

import org.apache.wsrp4j.commons.util.Modes;
import org.apache.wsrp4j.commons.util.ParameterChecker;
import org.apache.wsrp4j.commons.util.Utility;
import org.apache.wsrp4j.commons.util.WindowStates;

/**
 * JSR 168 portlet which acts as proxy (consumer) to WSRP portlets.
 *
 * @version $Id: ProxyPortlet.java 408858 2006-05-23 09:34:31Z dlouzan $
 **/
public class ProxyPortlet extends GenericPortlet {
    
    // stores consumer specific information
    private ConsumerEnvironment consumerEnv;
    
    // used to validate producer responses
    private static ParameterChecker checker;
    
    // logger
    private static final Log log = LogFactory.getLog(ProxyPortlet.class);
    
    // lock object for thread synchronization while setting the urlgenerator
    private static final Object urlGenLock = new Object();
    
    //  lock object for thread synchronization while updating session handler
    private static final Object sessionHdlrLock = new Object();
    
    // used as key in render params
    public static final String NAVIGATIONAL_STATE =
            "proxyportlet-updateResponse-navState";
    
    /**
     * Called by the container to initialize the portlet. This implementation
     * calls init from the super class and instantiates the
     * <code>ParameterChecker</code> and a <code>Logger</code>.
     *
     * @param config a <code>PortletConfig</code> object containing the portlet
     * configuration and initialization parameters
     **/
    public void init(PortletConfig config) throws PortletException {
        
        final String MN = "init";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        super.init(config);
        
        checker = new ParameterChecker();
        
        try {
            initConsumerEnvironment(config);
        } catch (WSRPException e) {
            throw new PortletException(e);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
    }
    
    /**
     * @param config
     */
    private void initConsumerEnvironment(PortletConfig config)
    throws WSRPException {
        
        try {
            String className = config.getInitParameter(
                    ConsumerConstants.CONSUMER_ENV_CLASS);
            
            if (className != null) {
                Class cl = Class.forName(className);
                consumerEnv = (ConsumerEnvironment)cl.newInstance();
                
                String path =
                        config.getPortletContext().getRealPath("/WEB-INF");

                if (log.isDebugEnabled()) {
                    log.debug("Path to persistent directory: " + path);
                }
                
                ((ConsumerEnvironmentImpl)consumerEnv).setRegistryPath(path);
            } else {
                throw new IllegalStateException("Consumer Environment class " +
                        "not found");
            }
            
        } catch (Exception e) {
            
            WSRPXHelper.throwX(
                    log, 
                    ErrorCodes.INSTANTIATION_OF_CONSUMER_ENV_FAILED,
                    e);
        }
        
    }
    
    /**
     * This method is called whenever a <code>performBlockingInteraction</code>
     * request is being made on a remote portlet.
     *
     * The implementation performs the actual wsrp performBlockingInteraction
     * call and processes the response from the producer. As result of this
     * call the navigational and window state, portlet mode can be changed.
     * Optionally the producer can send a redirect which is then passed to the
     * portlet container. Changing the state of the remote portlet can lead to
     * cloning the portlet at the producer side. As the result of such a
     * <i>clone on write</i> behaviour the proxy portlet will be reconfigured
     * to serve the clone.
     *
     * @param request the portlet request
     * @param actionResponse the action response
     **/
    public void processAction(ActionRequest request,
            ActionResponse actionResponse) throws PortletException {
        
        String MN = "processAction";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        try {
            // get the user on which request this call is being done
            User user = getUser(request);
            String userID = null;
            if (user != null) {
                userID = user.getUserID();
            }
            
            // get all information and objects which are needed to perform the 
            // interaction
            Map preferences = getPreferences(request);
            PortletKey portletKey = getPortletKey(preferences);
            WSRPPortlet portlet = getPortlet(portletKey, preferences);
            PortletWindowSession windowSession = 
                    getWindowSession(userID, portlet, request);
            PortletDriver portletDriver = 
                    consumerEnv.getPortletDriverRegistry().
                    getPortletDriver(portlet);
            InteractionRequest actionRequest = 
                    new WSRPRequestImpl(windowSession, request);
            
            if (log.isDebugEnabled()) {
              String windowID = IdHelper.getWindowID(request);

                log.debug("proxyportlet(" +
                        windowID +
                        ") invokes action on portlet " +
                        portlet.getPortletKey().getPortletHandle());
            }
            
            // do the actual call and check the response from the producer
            BlockingInteractionResponse response = null;
            try {
                response = portletDriver.performBlockingInteraction(
                        actionRequest, userID);
                checker.check(response);
                
            } catch (MissingParameters missingParameters) {
              WSRPXHelper.handleWSRPFault(log, missingParameters);
            }

          // process the reponse
            if (response != null) {
                // the producer can either send a update response or a redirect
                UpdateResponse updateResponse = response.getUpdateResponse();
                String redirectURL = response.getRedirectURL();
                
                if (updateResponse != null) {
                    // process the update response
                    if (windowSession != null) {
                        updateSessionContext(updateResponse.getSessionContext(), 
                                windowSession.getPortletSession());
                        windowSession.updateMarkupCache(
                                updateResponse.getMarkupContext());
                    }
                    updatePortletContext(request, 
                            updateResponse.getPortletContext(), portlet);
                    
                    // pass navState to next getMarkup by using the render 
                    // params
                    String navState = updateResponse.getNavigationalState();
                    if (navState != null) {
                        actionResponse.setRenderParameter(NAVIGATIONAL_STATE, 
                                navState);
                    }
                    
                    // if the remote portlet requested to change the portlet 
                    // mode we try to solve this request.
                    String newMode = updateResponse.getNewMode();
                    if (newMode != null) {
                        try {
                            if (newMode.equalsIgnoreCase(Modes._view)) {
                                actionResponse.setPortletMode(PortletMode.VIEW);
                                
                            } else if (newMode.equalsIgnoreCase(Modes._edit)) {
                                actionResponse.setPortletMode(PortletMode.EDIT);
                                
                            } else if (newMode.equalsIgnoreCase(Modes._help)) {
                                actionResponse.setPortletMode(PortletMode.HELP);
                            }
                        } catch (PortletModeException e) {
                            // means portlet does not support this mode
                            // means portlet does not support the window state
                            if (log.isInfoEnabled()) {
                                log.info("The portlet: '" +
                                        portlet.getPortletKey().getPortletHandle() +
                                        "' does not support the mode: " +
                                        e.getMode());
                            }
                        }
                    }
                    
                    // if the remote portlet requested to change the window 
                    // state we try to solve this request. If the window state
                    String newWindowState = updateResponse.getNewWindowState();
                    if (newWindowState != null) {
                        try {
                            if (newWindowState.equalsIgnoreCase
                                    (WindowStates._maximized)) {
                                actionResponse.setWindowState(
                                        WindowState.MAXIMIZED);
                                
                            } else if (newWindowState.equalsIgnoreCase(
                                    WindowStates._minimized)) {
                                actionResponse.setWindowState(
                                        WindowState.MINIMIZED);
                                
                            } else if (newWindowState.equalsIgnoreCase
                                    (WindowStates._normal)) {
                                actionResponse.setWindowState(
                                        WindowState.NORMAL);
                            }
                        } catch (WindowStateException e) {
                            // means portlet does not support the window state
                            if (log.isInfoEnabled()) {
                                log.info("The portlet: '" +
                                        portlet.getPortletKey().getPortletHandle() +
                                        "' does not support the windowState: " +
                                        e.getState());
                            }
                        }
                    }
                } else if (redirectURL != null) {
                    // if we got a redirect forward this redirect to the 
                    // container
                    try {
                        actionResponse.sendRedirect(redirectURL);
                    } catch (IOException ioEx) {
                        WSRPXHelper.throwX(
                                log, 
                                ErrorCodes.COULD_NOT_FOLLOW_REDIRECT);
                    }
                }
            }
            
        } catch (WSRPException e) {
            throw new PortletException(e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug(Utility.strExit(MN));
            }
        }
        
    }

  /**
     * This method is called whenever a <code>GetMarkup</code> or 
     * <code>PerformInteraction</code>
     * request is being made on the remote portlet.
     *
     * The implementation performs the actual wsrp getMarkup or 
     * performInteraction call
     * depending on the value of the request parameter <i>wsrp-urlType</i>. 
     * In case of a
     * performInteraction a getMarkup call is being done afterwards to fetch 
     * the markup.
     *
     * @param request the portlet request
     * @param renderResponse the render response
     **/
    public void render(RenderRequest request, RenderResponse renderResponse)
    throws PortletException, IOException {
        
        String MN = "render";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        try {
            // set content type in response
            renderResponse.setContentType(request.getResponseContentType());
            
            // get the user on which request this call is being done
            User user = getUser(request);
            String userID = null;
            if (user != null) {
                userID = user.getUserID();
            }
            
            // get all information and objects which are needed to perform the 
            // interaction
            Map preferences = getPreferences(request);
            PortletKey portletKey = getPortletKey(preferences);
            WSRPPortlet portlet = getPortlet(portletKey, preferences);
            PortletWindowSession windowSession = 
                    getWindowSession(userID, portlet, request);
            PortletDriver portletDriver = 
                    consumerEnv.getPortletDriverRegistry().
                    getPortletDriver(portlet);
            MarkupRequest markupRequest = 
                    new WSRPRequestImpl(windowSession, request);
            
            if (log.isDebugEnabled()) {
                log.debug("proxyportlet(" +
                        IdHelper.getWindowID(request) +
                        ") renders portlet " +
                        portlet.getPortletKey().getPortletHandle());
            }
            // feed the url generator with the current response
            synchronized (urlGenLock) {
                // update url generator
                URLGenerator urlGenerator = 
                        URLGeneratorImpl.getInstance(renderResponse, 
                        getPortletConfig());
                URLTemplateComposer templateComposer = 
                        consumerEnv.getTemplateComposer();
                if(templateComposer != null) {
                    templateComposer.setURLGenerator(urlGenerator);
                }
                
                consumerEnv.getURLRewriter().setURLGenerator(urlGenerator);
            }
            
            // do a getMarkup call and check the response
            MarkupResponse response = null;
            try {
                response = portletDriver.getMarkup(markupRequest, userID);
                checker.check(response);
                
            } catch (MissingParameters missingParameters) {
              WSRPXHelper.handleWSRPFault(log, missingParameters);
            }

          // process the markup response
            if (response != null) {
                if (windowSession != null) {
                    updateSessionContext(response.getSessionContext(), 
                            windowSession.getPortletSession());
                }
                processMarkupContext(response.getMarkupContext(), 
                        renderResponse);
            }
            
            // delete any chached markup
            if (windowSession != null) {
                windowSession.updateMarkupCache(null);
            }
            
        } catch (WSRPException e) {
            throw new PortletException(
                    "Error occured while retrieving markup", e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug(Utility.strExit(MN));
            }
        }
        
    }
    
    
    private String processMarkupContext(
            MarkupContext markupContext,
            RenderResponse renderResponse)
            throws WSRPException {
        
        final String MN = "processMarkupContext";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        String markup = null;
        
        if (markupContext != null && renderResponse != null) {
            // set prefered title if found
            String title = markupContext.getPreferredTitle();
            if (title != null) {
                renderResponse.setTitle(title);
            }
            
            markup = markupContext.getMarkupString();
            if (markup != null) {
                try {
                    renderResponse.getWriter().write(markup);
                    
                } catch (IOException e) {
                    WSRPXHelper.throwX(log, ErrorCodes.INTERNAL_ERROR, e);
                }
            }
            
            // TODO: need to handle markup binary
        }
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strExit(MN));
        }
        
        return markup;
    }
    
    private PortletWindowSession getWindowSession(String userID,
            WSRPPortlet portlet,
            PortletRequest request)
    throws WSRPException {
        
        PortletKey portletKey = portlet.getPortletKey();
        
        // to ensure that producer is added to the producer registry
        // throws exception which we pass
        getProducer(portletKey.getProducerId());
        
        // now we can get our sessions
        UserSession userSession = null;
        synchronized(sessionHdlrLock) {
            SessionHandler sessionHandler = 
                    (SessionHandler)consumerEnv.getSessionHandler();
            sessionHandler.setPortletSession(request.getPortletSession());
            // get the user session
            userSession = sessionHandler.getUserSession(
                    portletKey.getProducerId(), userID);
        }
        
        if (userSession != null) {
            // get the group session
            String groupID = getPortletDescription(portlet).getGroupID();
            groupID = groupID == null ? "default" : groupID;
            
            GroupSession groupSession = userSession.getGroupSession(groupID);
            if (groupSession != null) {
                // get the portlet session
                String handle = IdHelper.getPortletEntityID(request);
                PortletSession portletSession = 
                        groupSession.getPortletSession(handle);
                
                if (portletSession != null) {
                    // get the window session
                    String windowID = IdHelper.getWindowID(request);
                    PortletWindowSession windowSession = 
                            portletSession.getPortletWindowSession(windowID);
                    
                    return windowSession;
                } else {
                    WSRPXHelper.throwX(ErrorCodes.PORTLET_SESSION_NOT_FOUND);
                }
            } else {
                WSRPXHelper.throwX(ErrorCodes.GROUP_SESSION_NOT_FOUND);
            }
        } else {
            WSRPXHelper.throwX(ErrorCodes.USER_SESSION_NOT_FOUND);
        }
        // we will never reach this
        return null;
    }
    
    private void updateSessionContext(SessionContext sessionContext, 
            PortletSession portletSession) {
        
        if (sessionContext != null) {
            if (portletSession != null) {
                portletSession.setSessionContext(sessionContext);
            }
            
        }
    }
    
    private void updatePortletContext(
            PortletRequest request,
            oasis.names.tc.wsrp.v1.types.PortletContext portletContext,
            WSRPPortlet portlet)
            throws WSRPException {
        
        if (portletContext != null && portlet != null) {
            
            String newPortletHandle = portletContext.getPortletHandle();
            PortletKey portletKey = portlet.getPortletKey();
            
            if (newPortletHandle != null && 
                    !newPortletHandle.equals(portletKey.getPortletHandle())) {
                
                // seems like the producer made a clone
                String producerID = portletKey.getProducerId();
                PortletKey newPortletKey = 
                        new PortletKeyImpl(newPortletHandle, producerID);
                portlet = createPortlet(newPortletKey, portlet.getParent());
                consumerEnv.getPortletRegistry().addPortlet(portlet);
                
                // set new portlet key in portlet preferences
                PortletPreferences preferences = request.getPreferences();
                try {
                    preferences.setValue(ConsumerConstants.WSRP_PORTLET_HANDLE, 
                            newPortletHandle);
                    preferences.setValue(ConsumerConstants.WSRP_PARENT_HANDLE, 
                            portlet.getParent());
                    preferences.store();
                } catch (Exception e) {
                    // ups
                    WSRPXHelper.throwX(log, ErrorCodes.INTERNAL_ERROR, e);
                }
                
            }
            
            portlet.setPortletContext(portletContext);
        }
    }
    
    private User getUser(PortletRequest request) {
        
        User user = null;
        
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            String userKey = userPrincipal.getName();
            
            user = consumerEnv.getUserRegistry().getUser(userKey);
            if (user == null) {
                user = new UserImpl(userKey);
                UserContext userContext = new UserContext();
                userContext.setProfile(fillUserProfile(request));
                
                userContext.setUserContextKey(userKey);
                user.setUserContext(userContext);
                consumerEnv.getUserRegistry().addUser(user);
            }
        }
        
        return user;
    }
    
    private UserProfile fillUserProfile(PortletRequest request) {
        
        UserProfile userProfile = null;
        
        Map userInfo = (Map)request.getAttribute(PortletRequest.USER_INFO);
        if (userInfo != null) {
            userProfile = new UserProfile();
            
            PersonName personName = new PersonName();
            personName.setPrefix((String)userInfo.get("user.name.prefix"));
            personName.setGiven((String)userInfo.get("user.name.given"));
            personName.setFamily((String)userInfo.get("user.name.family"));
            personName.setMiddle((String)userInfo.get("user.name.middle"));
            personName.setSuffix((String)userInfo.get("user.name.suffix"));
            personName.setNickname((String)userInfo.get("user.name.nickName"));
            
            userProfile.setName(personName);
        }
        
        return userProfile;
    }
    
    private Map getPreferences(PortletRequest request) {
        
        Map preferences = new HashMap();
        Enumeration keys = request.getPreferences().getNames();
        
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = request.getPreferences().getValue(key, null);
            
            preferences.put(key, value);
        }
        
        return preferences;
    }
    
    protected PortletKey getPortletKey(Map preferences) {
        PortletKey portletKey = null;
        
        String portletHandle = 
                (String)preferences.get(ConsumerConstants.WSRP_PORTLET_HANDLE);
        
        if (portletHandle != null) {
            String producerID = 
                    (String)preferences.get(ConsumerConstants.WSRP_PRODUCER_ID);
            if (producerID != null) {
                portletKey = new PortletKeyImpl(portletHandle, producerID);
            } else {
                // no producer id specified
            }
        } else {
            // no portlet handle specified for portlet
        }
        
        return portletKey;
    }
    
    private WSRPPortlet getPortlet(PortletKey portletKey, Map preferences) 
    throws WSRPException {
        
        WSRPPortlet portlet = null;
        
        if (portletKey != null) {
            portlet = consumerEnv.getPortletRegistry().getPortlet(portletKey);
            if (portlet == null) {
                // not yet in registry, create new one
                String parentHandle = 
                        (String) preferences.get(
                        ConsumerConstants.WSRP_PARENT_HANDLE);
                
                portlet = createPortlet(portletKey, parentHandle);
                consumerEnv.getPortletRegistry().addPortlet(portlet);
            }
        }
        
        return portlet;
    }
    
    private WSRPPortlet createPortlet(PortletKey portletKey, 
            String parentHandle) {
        
        WSRPPortlet portlet = new WSRPPortletImpl(portletKey);
        
        oasis.names.tc.wsrp.v1.types.PortletContext portletContext = 
                new oasis.names.tc.wsrp.v1.types.PortletContext();
        portletContext.setPortletHandle(portletKey.getPortletHandle());
        portletContext.setPortletState(null);
        // portletContext.getExtensions().clear();
        portlet.setPortletContext(portletContext);
        
        if (parentHandle != null) {
            portlet.setParent(parentHandle);
        } else {
            // asume a POP -> parentHandle = portletHandle
            portlet.setParent(portletKey.getPortletHandle());
        }
        
        return portlet;
    }
    
    private PortletDescription getPortletDescription(WSRPPortlet portlet) 
    throws WSRPException {
        String producerID = portlet.getPortletKey().getProducerId();
        Producer producer = getProducer(producerID);
        
        PortletDescription portletDesc = 
                producer.getPortletDescription(portlet.getParent());
        if (portletDesc == null) {
            WSRPXHelper.throwX(ErrorCodes.PORTLET_DESC_NOT_FOUND);
        }
        
        return portletDesc;
    }
    
    protected Producer getProducer(String producerID) throws WSRPException {
        
        final String MN = "getProducer";
        
        if (log.isDebugEnabled()) {
            log.debug(Utility.strEnter(MN));
        }
        
        if (log.isDebugEnabled()) {
            log.debug("Trying to load producer with ID: " + producerID);
        }
        
        Producer producer = 
                consumerEnv.getProducerRegistry().getProducer(producerID);
        
        if (producer == null) {
            WSRPXHelper.throwX(log, ErrorCodes.PRODUCER_DOES_NOT_EXIST);
        }
        
        return producer;
    }
    protected ConsumerEnvironment getConsumerEnvironment() {
        return consumerEnv;
    }
    
}
