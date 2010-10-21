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

import java.rmi.RemoteException;
import java.util.Arrays;

import oasis.names.tc.wsrp.v1.intf.WSRPV1PortletManagementPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1MarkupPortType;
import oasis.names.tc.wsrp.v1.intf.*;
import oasis.names.tc.wsrp.v1.types.BlockingInteractionResponse;
import oasis.names.tc.wsrp.v1.types.ClientData;
import oasis.names.tc.wsrp.v1.types.CookieProtocol;
import oasis.names.tc.wsrp.v1.types.DestroyPortletsResponse;
import oasis.names.tc.wsrp.v1.types.InteractionParams;
import oasis.names.tc.wsrp.v1.types.InvalidCookieFault;
import oasis.names.tc.wsrp.v1.types.MarkupContext;
import oasis.names.tc.wsrp.v1.types.MarkupParams;
import oasis.names.tc.wsrp.v1.types.MarkupResponse;
import oasis.names.tc.wsrp.v1.types.PortletContext;
import oasis.names.tc.wsrp.v1.types.PortletDescription;
import oasis.names.tc.wsrp.v1.types.PortletDescriptionResponse;
import oasis.names.tc.wsrp.v1.types.PortletPropertyDescriptionResponse;
import oasis.names.tc.wsrp.v1.types.PropertyList;
import oasis.names.tc.wsrp.v1.types.RegistrationContext;
import oasis.names.tc.wsrp.v1.types.ReturnAny;
import oasis.names.tc.wsrp.v1.types.RuntimeContext;
import oasis.names.tc.wsrp.v1.types.ServiceDescription;
import oasis.names.tc.wsrp.v1.types.StateChange;
import oasis.names.tc.wsrp.v1.types.Templates;
import oasis.names.tc.wsrp.v1.types.UserContext;
import oasis.names.tc.wsrp.v1.types.ClonePortlet;
import oasis.names.tc.wsrp.v1.types.DestroyPortlets;
import oasis.names.tc.wsrp.v1.types.GetMarkup;
import oasis.names.tc.wsrp.v1.types.GetPortletDescription;
import oasis.names.tc.wsrp.v1.types.GetPortletProperties;
import oasis.names.tc.wsrp.v1.types.GetPortletPropertyDescription;
import oasis.names.tc.wsrp.v1.types.InitCookie;
import oasis.names.tc.wsrp.v1.types.PerformBlockingInteraction;
import oasis.names.tc.wsrp.v1.types.ReleaseSessions;
import oasis.names.tc.wsrp.v1.types.SetPortletProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.consumer.interfaces.consumer.ConsumerEnvironment;
import org.apache.wsrp4j.commons.consumer.interfaces.session.GroupSessionMgr;
import org.apache.wsrp4j.commons.consumer.interfaces.request.InteractionRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.request.MarkupRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.portletdriver.PortletDriver;
import org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer;
import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.URLRewriter;
import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.URLTemplateComposer;
import org.apache.wsrp4j.commons.consumer.interfaces.user.User;
import org.apache.wsrp4j.commons.consumer.interfaces.session.UserSessionMgr;
import org.apache.wsrp4j.commons.consumer.interfaces.request.WSRPBaseRequest;
import org.apache.wsrp4j.commons.consumer.interfaces.portlet.WSRPPortlet;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.exception.WSRPXHelper;
import org.apache.wsrp4j.commons.util.Constants;
import org.apache.wsrp4j.commons.util.ParameterChecker;

/**
 * This class implements a portlet driver
 * 
 * @version $Id: PortletDriverImpl.java 405796 2006-05-10 16:48:00Z dlouzan $
 */
public class PortletDriverImpl implements PortletDriver {
    
    private static final Log log = LogFactory.getLog(PortletDriverImpl.class);

    private WSRPPortlet portlet;

    private WSRPV1MarkupPortType markupPort;

    private WSRPV1PortletManagementPortType portletPort;

    private ConsumerEnvironment consumerEnv;

    private Producer producer;

    private ParameterChecker parameterChecker;

    private CookieProtocol initCookie = CookieProtocol.NONE;

    public PortletDriverImpl(WSRPPortlet portlet, ConsumerEnvironment consumerEnv)
            throws WSRPException {
        parameterChecker = new ParameterChecker();

        this.consumerEnv = consumerEnv;
        this.portlet = portlet;
        this.producer = consumerEnv.getProducerRegistry().getProducer(
                portlet.getPortletKey().getProducerId());

        portletPort = producer.getPortletManagementInterface();

        ServiceDescription serviceDescription = producer.getServiceDescription(false);
        if (serviceDescription != null) {
            this.initCookie = serviceDescription.getRequiresInitCookie();
            if (initCookie == null) {
                initCookie = CookieProtocol.NONE; // TODO - get from config
            }
        }
    }

    /**
     * Get the portlet this driver is bind to.
     * 
     * @return The enity
     */
    public WSRPPortlet getPortlet() {
        return this.portlet;
    }

    private void resetInitCookie(String userID) throws WSRPException {

        UserSessionMgr userSession = this.consumerEnv.getSessionHandler().getUserSession(
                getPortlet().getPortletKey().getProducerId(), userID);

        if (initCookie.equals(CookieProtocol.NONE)) {

            userSession.setInitCookieDone(false);

        } else if (initCookie.equals(CookieProtocol.PER_GROUP)) {

            PortletDescription portletDescription = null;
            try {

                portletDescription = producer.getPortletDescription(getPortlet().getParent());

            } catch (WSRPException e) {

                // do nothing since exception has been logged already

            }

            String groupID = null;
            if (portletDescription != null) {
                groupID = portletDescription.getGroupID();
            }

            if (groupID != null) {
                GroupSessionMgr groupSession = userSession.getGroupSession(groupID);
                groupSession.setInitCookieDone(false);
            }
        }
    }

    private void checkInitCookie(String userID) throws WSRPException {
        UserSessionMgr userSession = this.consumerEnv.getSessionHandler().getUserSession(
                getPortlet().getPortletKey().getProducerId(), userID);

        if (initCookie.equals(CookieProtocol.PER_USER)) {

            this.markupPort = userSession.getWSRPBaseService();

            if (!userSession.isInitCookieDone()) {

                userSession.setInitCookieRequired(true);
                initCookie();
                userSession.setInitCookieDone(true);
            }

        } else if (initCookie.equals(CookieProtocol.PER_GROUP)) {

            PortletDescription portletDescription = producer.getPortletDescription(getPortlet()
                    .getParent());
            String groupID = null;
            if (portletDescription != null) {
                groupID = portletDescription.getGroupID();
            }

            if (groupID != null) {
                GroupSessionMgr groupSession = userSession.getGroupSession(groupID);

                this.markupPort = groupSession.getWSRPBaseService();

                if (!groupSession.isInitCookieDone()) {
                    groupSession.setInitCookieRequired(true);
                    initCookie();
                    groupSession.setInitCookieDone(true);
                }

            } else {
                // means either we have no service description from the
                // producer containg the portlet
                // or the producer specified initCookieRequired perGroup
                // but didn't provide
                // a groupID in the portlet description
            }
        } else {
            this.markupPort = userSession.getWSRPBaseService();
        }

    }

    private MarkupParams getMarkupParams(WSRPBaseRequest request) {

        MarkupParams markupParams = new MarkupParams();
        ClientData clientData = null;

        // lets just set this to the consumer agent for now
        if (producer.getRegistrationData() != null) {
            clientData = new ClientData();
            clientData.setUserAgent(producer.getRegistrationData().getConsumerAgent());
        }
        markupParams.setClientData(clientData);
        markupParams.setSecureClientCommunication(false);
        markupParams.getLocales().addAll(Arrays.asList(consumerEnv.getSupportedLocales()));

        markupParams.getMimeTypes().addAll(Arrays.asList(consumerEnv.getMimeTypes()));

        markupParams.setMode(request.getMode());
        markupParams.setWindowState(request.getWindowState());
        markupParams.setNavigationalState(request.getNavigationalState());
        markupParams.getMarkupCharacterSets().addAll(Arrays.asList(consumerEnv.getCharacterEncodingSet()));
        markupParams.setValidateTag(null); // TODO ValidateTag

        // TODO: Set only modes and window states that are supported by the
        // portlet as
        // described in it's portlet description.
        markupParams.getValidNewModes().addAll(Arrays.asList(consumerEnv.getSupportedModes()));
        markupParams.getValidNewWindowStates().addAll(Arrays.asList(consumerEnv.getSupportedWindowStates()));

        // markupParams.setExtensions(null);

        return markupParams;
    }

    private RuntimeContext getRuntimeContext(WSRPBaseRequest request) {
        RuntimeContext runtimeContext = new RuntimeContext();
        runtimeContext.setUserAuthentication(consumerEnv.getUserAuthentication());
        runtimeContext.setPortletInstanceKey(request.getPortletInstanceKey());

        URLTemplateComposer templateComposer = consumerEnv.getTemplateComposer();
        if (templateComposer != null) {
            runtimeContext.setNamespacePrefix(templateComposer.getNamespacePrefix());
        }

        Boolean doesUrlTemplateProcess = null;
        try {

            PortletDescription desc = producer.getPortletDescription(getPortlet().getParent());

            if (desc != null) {

                doesUrlTemplateProcess = desc.isDoesUrlTemplateProcessing();
            }

        } catch (WSRPException e) {

            // do nothing since exception has been logged already
            // continue with assumption that portlet does not support template
            // processing
        }

        if (doesUrlTemplateProcess != null && templateComposer != null
                && doesUrlTemplateProcess.booleanValue()) {
            Templates templates = new Templates();
            templates.setBlockingActionTemplate(templateComposer.createBlockingActionTemplate(true,
                    true, true, true));
            templates.setRenderTemplate(templateComposer.createRenderTemplate(true, true, true,
                    true));
            templates.setDefaultTemplate(templateComposer.createDefaultTemplate(true, true, true,
                    true));
            templates.setResourceTemplate(templateComposer.createResourceTemplate(true, true, true,
                    true));
            templates.setSecureBlockingActionTemplate(templateComposer
                    .createSecureBlockingActionTemplate(true, true, true, true));
            templates.setSecureRenderTemplate(templateComposer.createSecureRenderTemplate(true,
                    true, true, true));
            templates.setSecureDefaultTemplate(templateComposer.createSecureDefaultTemplate(true,
                    true, true, true));
            templates.setSecureResourceTemplate(templateComposer.createSecureResourceTemplate(true,
                    true, true, true));
            runtimeContext.setTemplates(templates);
        }

        runtimeContext.setSessionID(request.getSessionID());
        runtimeContext.getExtensions().clear();

        return runtimeContext;
    }

    private UserContext getUserContext(String userID) {
        UserContext userContext = null;

        if (userID != null) {
            User user = consumerEnv.getUserRegistry().getUser(userID);

            if (user != null) {
                userContext = user.getUserContext();
            }
        }

        // workaround for Oracle bug, always send a userContext with dummy value
        // if none was provided

        if (userContext == null) {
            userContext = new UserContext();
            userContext.setUserContextKey("dummyUserContextKey");
        }

        return userContext;
    }

    private InteractionParams getInteractionParams(InteractionRequest actionRequest) {
        InteractionParams interactionParams = new InteractionParams();

        interactionParams.setPortletStateChange(consumerEnv.getPortletStateChange());

        // access POPs with cloneBeforeWrite
        // however keep the default behaviour from ConsEnv
        // this means that if readWrite is set and we access a POP then set to
        // cloneBeforeWrite
        if (!portlet.isConsumerConfigured()
                && interactionParams.getPortletStateChange().equals(StateChange.READ_WRITE)) {
            interactionParams.setPortletStateChange(StateChange.CLONE_BEFORE_WRITE);
        }

        interactionParams.setInteractionState(actionRequest.getInteractionState());
        interactionParams.getFormParameters().addAll(Arrays.asList(actionRequest.getFormParameters()));
        // interactionParams.setUploadContexts(null);
        // interactionParams.setExtensions(null);

        return interactionParams;
    }

    /**
     * This method is used to retrieve the markup generated by the portlet
     * instance.
     * 
     * @param markupRequest
     * @param userID
     * @return The markup response generated by the portlet
     */
    public MarkupResponse getMarkup(MarkupRequest markupRequest, String userID)
            throws WSRPException {
        checkInitCookie(userID);

        MarkupResponse response = null;

        try {

            MarkupContext markupContext = null;
            if ((markupContext = markupRequest.getCachedMarkup()) == null) {

                // getMarkup request
                GetMarkup request = new GetMarkup();

                request.setPortletContext(getPortlet().getPortletContext());
                request.setMarkupParams(getMarkupParams(markupRequest));
                request.setRuntimeContext(getRuntimeContext(markupRequest));

                RegistrationContext regCtx = producer.getRegistrationContext();
                if (regCtx != null)
                    request.setRegistrationContext(regCtx);

                UserContext userCtx = getUserContext(userID);
                if (userCtx != null)
                    request.setUserContext(getUserContext(userID));

                response = markupPort.getMarkup(request);

                parameterChecker.check(response);

            } else {

                response = new MarkupResponse();
                response.setMarkupContext(markupContext);
            }

            Boolean requiresRewriting = response.getMarkupContext().isRequiresUrlRewriting();
            requiresRewriting = requiresRewriting == null ? Boolean.FALSE : requiresRewriting;

            if (requiresRewriting.booleanValue()) {
                // rewrite url's

                URLRewriter urlRewriter = consumerEnv.getURLRewriter();
                String rewrittenMarkup = urlRewriter.rewriteURLs(response.getMarkupContext()
                        .getMarkupString());

                if (rewrittenMarkup != null) {
                    response.getMarkupContext().setMarkupString(rewrittenMarkup);
                }
            }

        } catch (InvalidCookie cookieFault) {

            // lets reset the init cookie settings
            resetInitCookie(userID);

            // and try it again
            response = getMarkup(markupRequest, userID);

        } catch (InvalidUserCategory fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InconsistentParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidSession fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (UnsupportedMimeType fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (UnsupportedMode fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (UnsupportedLocale fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidHandle fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (UnsupportedWindowState fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }

    /**
     * This method is used to perform a blocking interaction on the portlet
     * instance.
     * 
     * @param actionRequest
     */
    public BlockingInteractionResponse performBlockingInteraction(InteractionRequest actionRequest,
            String userID) throws WSRPException {

        checkInitCookie(userID);

        BlockingInteractionResponse response = null;

        try {
            PerformBlockingInteraction request = new PerformBlockingInteraction();

            request.setPortletContext(getPortlet().getPortletContext());
            request.setInteractionParams(getInteractionParams(actionRequest));
            request.setMarkupParams(getMarkupParams(actionRequest));
            request.setRuntimeContext(getRuntimeContext(actionRequest));

            RegistrationContext regCtx = producer.getRegistrationContext();
            if (regCtx != null)
                request.setRegistrationContext(regCtx);

            UserContext userCtx = getUserContext(userID);
            if (userCtx != null)
                request.setUserContext(userCtx);

            response = markupPort.performBlockingInteraction(request);

            parameterChecker.check(response);

        } catch (InvalidCookie cookieFault) {

            // lets reset the init cookie settings
            resetInitCookie(userID);

            // and try it again
            response = performBlockingInteraction(actionRequest, userID);

        } catch (InconsistentParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (UnsupportedMimeType fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (UnsupportedLocale fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (UnsupportedMode fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidUserCategory fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidSession fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (PortletStateChangeRequired fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidHandle fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (UnsupportedWindowState fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }

    /**
     * Clone the portlet
     * 
     * @return The new portlet context
     */
    public PortletContext clonePortlet(String userID) throws WSRPException {
        ClonePortlet request = new ClonePortlet();

        request.setPortletContext(getPortlet().getPortletContext());

        RegistrationContext regCtx = producer.getRegistrationContext();
        if (regCtx != null)
            request.setRegistrationContext(regCtx);

        UserContext userCtx = getUserContext(userID);
        if (userCtx != null)
            request.setUserContext(userCtx);

        PortletContext response = null;

        try {

            response = portletPort.clonePortlet(request);
            parameterChecker.check(response, Constants.NILLABLE_FALSE);

        } catch (InvalidUserCategory fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InconsistentParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidHandle fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }

    /**
     * Destroy the producer portlets specified in the entiyHandles array.
     */
    public DestroyPortletsResponse destroyPortlets(String[] portletHandles, String userID)
            throws WSRPException {
        DestroyPortlets request = new DestroyPortlets();

        RegistrationContext regCtx = producer.getRegistrationContext();
        if (regCtx != null)
            request.setRegistrationContext(regCtx);

        request.getPortletHandles().addAll(Arrays.asList(portletHandles));

        DestroyPortletsResponse response = null;
        try {

            response = portletPort.destroyPortlets(request);
            parameterChecker.check(response);

        } catch (InconsistentParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }

    /**
     * Inform the producer that the sessions specified in the sessionIDs array
     * will no longer be used by the consumer and can therefor be released.
     */
    public ReturnAny releaseSessions(String[] sessionIDs, String userID) throws WSRPException {

        checkInitCookie(userID);

        ReleaseSessions request = new ReleaseSessions();

        RegistrationContext regCtx = producer.getRegistrationContext();
        if (regCtx != null)
            request.setRegistrationContext(regCtx);

        request.getSessionIDs().addAll(Arrays.asList(sessionIDs));

        ReturnAny response = null;
        try {

            response = markupPort.releaseSessions(request);

        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }

    /**
     * Actual WSRP initCookie() call
     */
    public void initCookie() throws WSRPException {
        InitCookie request = new InitCookie();

        RegistrationContext regCtx = producer.getRegistrationContext();
        if (regCtx != null)
            request.setRegistrationContext(regCtx);

        try {

            markupPort.initCookie(request);

        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }
    }

    /**
     * Fetches information about the portlet from the producer.
     * 
     * @param userID
     *            is used to get the user context of the user from the user
     *            registry
     * @param desiredLocales
     *            Array of locales the description should be provided
     * 
     * @return The response to the getPortletDescription call.
     */
    public PortletDescriptionResponse getPortletDescription(String userID, String[] desiredLocales)
            throws WSRPException {
        GetPortletDescription request = new GetPortletDescription();

        RegistrationContext regCtx = producer.getRegistrationContext();
        if (regCtx != null)
            request.setRegistrationContext(regCtx);

        request.setPortletContext(getPortlet().getPortletContext());

        UserContext userCtx = getUserContext(userID);
        if (userCtx != null)
            request.setUserContext(userCtx);

        request.getDesiredLocales().addAll(Arrays.asList(desiredLocales));

        PortletDescriptionResponse response = null;

        try {

            response = portletPort.getPortletDescription(request);
            parameterChecker.check(response);

        } catch (InvalidUserCategory fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InconsistentParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidHandle fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }

    /**
     * Fetches all published properties of an remote portlet.
     * 
     * @param userID
     *            The ID of the user this request is done for
     * 
     * @return The portlet property description response from the producer
     */
    public PortletPropertyDescriptionResponse getPortletPropertyDescription(String userID)
            throws WSRPException {
        GetPortletPropertyDescription request = new GetPortletPropertyDescription();
        request.setPortletContext(getPortlet().getPortletContext());

        RegistrationContext regCtx = producer.getRegistrationContext();
        if (regCtx != null)
            request.setRegistrationContext(regCtx);

        UserContext userCtx = getUserContext(userID);
        if (userCtx != null)
            request.setUserContext(userCtx);

        request.getDesiredLocales().addAll(Arrays.asList(consumerEnv.getSupportedLocales()));

        PortletPropertyDescriptionResponse response = null;

        try {

            response = portletPort.getPortletPropertyDescription(request);
            parameterChecker.check(response);

        } catch (InvalidUserCategory fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InconsistentParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidHandle fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }

    /**
     * Get the current values of the properties with the given names.
     * 
     * @param names
     *            The names of the properties
     * @param userID
     *            The ID of the user is used to get the user context
     * 
     * @return A list of properties containing the values and names of the
     *         properties.
     */
    public PropertyList getPortletProperties(String[] names, String userID) throws WSRPException {
        GetPortletProperties request = new GetPortletProperties();
        request.setPortletContext(getPortlet().getPortletContext());
        request.getNames().addAll(Arrays.asList(names));

        RegistrationContext regCtx = producer.getRegistrationContext();
        if (regCtx != null)
            request.setRegistrationContext(regCtx);

        UserContext userCtx = getUserContext(userID);
        if (userCtx != null)
            request.setUserContext(userCtx);

        PropertyList response = null;

        try {

            response = portletPort.getPortletProperties(request);
            parameterChecker.check(response, Constants.NILLABLE_FALSE);

        } catch (InvalidUserCategory fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InconsistentParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidHandle fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }

    /**
     * Set the portlet properties specified in the property list
     * 
     * @param properties
     *            List of properties to be set.
     * @param userID
     *            The ID of the user is used to get the user context
     */
    public PortletContext setPortletProperties(PropertyList properties, String userID)
            throws WSRPException {
        SetPortletProperties request = new SetPortletProperties();
        request.setPortletContext(getPortlet().getPortletContext());

        RegistrationContext regCtx = producer.getRegistrationContext();
        if (regCtx != null)
            request.setRegistrationContext(regCtx);

        UserContext userCtx = getUserContext(userID);
        if (userCtx != null)
            request.setUserContext(userCtx);
        request.setPropertyList(properties);

        PortletContext response = null;

        try {

            response = portletPort.setPortletProperties(request);
            parameterChecker.check(response, Constants.NILLABLE_FALSE);

        } catch (InvalidUserCategory fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InconsistentParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidRegistration fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (OperationFailed fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (MissingParameters fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (AccessDenied fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        } catch (InvalidHandle fault) {
          WSRPXHelper.handleWSRPFault(log, fault);
        }

      return response;
    }
}
