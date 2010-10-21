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
package org.apache.wsrp4j.commons.util;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oasis.names.tc.wsrp.v1.types.*;
import oasis.names.tc.wsrp.v1.intf.MissingParameters;

import java.util.List;

/**
 * This class validates the objects and their attributes used for the WSRP
 * communication requests. The object and attribute tree is scanned including
 * the 2nd level. This means, that the input object, it's attributes and ,if the
 * attribute is itself an object, the subsequent object and It's attributes are
 * also checked. The checking is done only for required (R) parameters in the
 * WSRP specification. Optional (O) parameters are ignored. If a parameter is
 * specified as 'nillable' in the WSRP specification, the check is performed if
 * the value is 'not null'. On null value, no checking is done.
 * 
 * @version $Id: ParameterChecker.java 405794 2006-05-10 16:46:24Z dlouzan $
 */
public class ParameterChecker {

    // for logging and exception support
    private static Log log = LogFactory.getLog(ParameterChecker.class);

    /**
     * @return true if we check
     */
    private boolean isCheckEnabled() {
        if (log.isDebugEnabled()) {
            return true;
        }
        return false;
    }

    /**
     * --- THIS IS THE REQUEST SECTION OF THE PARAMETER-CHECKER ---
     */

    /**
     * Check the GetServiceDescritpion. The following attributes are mandatory:
     * 
     * 1) RegistrationContext, only if available 2) String[] DesiredLocales,
     * only if SendAllLocales == false
     * 
     * @param request
     *            GetServiceDescription
     * 
     * @throws MissingParameters
     * 
     * @see GetServiceDescription
     */
    public void check(GetServiceDescription request) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check ClonePortlet request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);

        }
        
    }

    /**
     * Check the ModifyRegistration. The following attributes are mandatory:
     * 
     * 1) RegistrationContext, only if available 2) RegistrationData
     * 
     * @param request
     *            ModifyRegistration
     * 
     * @throws MissingParameters
     * 
     * @see ModifyRegistration
     */
    public void check(ModifyRegistration request) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check ClonePortlet request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getRegistrationData(), Constants.NILLABLE_FALSE);
        }
        
    }

    /**
     * Check the GetMarkup. The following attributes are mandatory:
     * 
     * 1) RegistrationContext, only if available 2) PortletContext 3)
     * RuntimeContext 4) UserContext, only if available 5) MarkupParams
     * 
     * @param request
     *            getMarkup
     * 
     * @throws MissingParameters
     * 
     * @see GetMarkup
     */
    public void check(GetMarkup request) throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check ClonePortlet request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getPortletContext(), Constants.NILLABLE_FALSE);
            check(request.getRuntimeContext(), Constants.NILLABLE_FALSE);
            check(request.getUserContext(), Constants.NILLABLE_TRUE);
            check(request.getMarkupParams(), Constants.NILLABLE_FALSE);
        }
        
    }

    /**
     * Check the PerformBlockingInteraction. The following attributes are
     * mandatory:
     * 
     * 1) RegistrationContext, only if available 2) PortletContext 3)
     * RuntimeContext 4) UserContext, only if available 5) MarkupParams 6)
     * InteractionParams
     * 
     * @param request
     *            PerformBlockingInteraction
     * 
     * @throws MissingParameters
     * 
     * @see PerformBlockingInteraction
     */
    public void check(PerformBlockingInteraction request) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check PerformBlockingInteraction request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getPortletContext(), Constants.NILLABLE_FALSE);
            check(request.getRuntimeContext(), Constants.NILLABLE_FALSE);
            check(request.getUserContext(), Constants.NILLABLE_TRUE);
            check(request.getMarkupParams(), Constants.NILLABLE_FALSE);
            check(request.getInteractionParams(), Constants.NILLABLE_FALSE);
        }
        
    }

    /**
     * Check the InitCookie. The following attributes are mandatory:
     * 
     * 1) RegistrationContext, only if available
     * 
     * @param request
     *            InitCookie
     * 
     * @throws MissingParameters
     * 
     * @see InitCookie
     */
    public void check(InitCookie request) throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check InitCookie request object pointer
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * Parameter check for the GetPortletDescription object. The following
     * attributes are mandatory
     * 
     * 1) RegistrationContext, only if available 2) PortletContext 3)
     * UserContext, only if available
     * 
     * @param request
     *            GetPortletDescription
     * 
     * @throws MissingParameters
     * 
     * @see GetPortletDescription
     */
    public void check(GetPortletDescription request) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check GetPortletDescription request object pointer
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getPortletContext(), Constants.NILLABLE_FALSE);
            check(request.getUserContext(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * Parameter check for the ClonePortlet object. The following attributes are
     * mandatory:
     * 
     * 1) RegistrationContext, only if available 2) PortletContext 3)
     * UserContext, only if available
     * 
     * @param request
     *            ClonePortlet
     * 
     * @throws MissingParameters
     * 
     * @see ClonePortlet
     */
    public void check(ClonePortlet request) throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check ClonePortlet request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getPortletContext(), Constants.NILLABLE_FALSE);
            check(request.getUserContext(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * Parameter check for the DestroyEntites object. The following attributes
     * are mandatory:
     * 
     * 1) RegistrationContext, only if available 2) PortletHandle
     * 
     * @param request
     *            DestroyPortlets
     * 
     * @throws MissingParameters
     * 
     * @see DestroyPortlets
     */
    public void check(DestroyPortlets request) throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check DestroyPortlet request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getPortletHandles(), Constants.NILLABLE_FALSE);
        }
        
    }

    /**
     * Parameter check for the SetPortletProperties object. The following
     * attributes are mandatory:
     * 
     * 1) RegistrationContext, only if available 2) PortletContext 3)
     * UserContext, only if available 4) PropertyList
     * 
     * @param request
     *            SetPortletProperties
     * 
     * @throws MissingParameters
     * 
     * @see SetPortletProperties
     */
    public void check(SetPortletProperties request) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check SetPortletProperties request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getPortletContext(), Constants.NILLABLE_FALSE);
            check(request.getUserContext(), Constants.NILLABLE_TRUE);
            check(request.getPropertyList(), Constants.NILLABLE_FALSE);
        }
        
    }

    /**
     * Parameter check for the SetPortletProperties object. The following
     * attributes are mandatory:
     * 
     * 1) RegistrationContext, only if available 2) PortletContext 3)
     * UserContext, only if available 4) Names, only if available
     * 
     * @param request
     *            GetPortletProperties
     * 
     * @throws MissingParameters
     * 
     * @see GetPortletProperties
     */
    public void check(GetPortletProperties request) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check GetPortletProperties request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getPortletContext(), Constants.NILLABLE_FALSE);
            check(request.getUserContext(), Constants.NILLABLE_TRUE);
            check(request.getNames(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * Parameter check for the SetPortletProperties object. The following
     * attributes are mandatory:
     * 
     * 1) RegistrationContext, only if available 2) PortletContext 3)
     * UserContext, only if available
     * 
     * @param request
     *            GetPortletPropertyDescription
     * 
     * @throws MissingParameters
     * 
     * @see GetPortletPropertyDescription
     */
    public void check(GetPortletPropertyDescription request)
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check GetPortletPropertyDescription request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);
            check(request.getPortletContext(), Constants.NILLABLE_FALSE);
            check(request.getUserContext(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * Parameter check for the ReleaseSession object. The following attributes
     * are mandatory:
     * 
     * 1) RegistrationContext, only if available 2) String[] SessionHandles
     * 
     * @param request
     *            _releaseSession
     * 
     * @throws MissingParameters
     * 
     * @see ReleaseSessions
     */
    public void check(ReleaseSessions request) throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check ReleaseSession request object pointer
            // check for registration context
            check(request.getRegistrationContext(), Constants.NILLABLE_TRUE);

            // check sessionhandles array
            check(request.getSessionIDs(), Constants.NILLABLE_FALSE);
        }
        
    }

    /**
     * --- THIS IS THE RESPONSE SECTION OF THE PARAMETER-CHECKER ---
     */

    /**
     * Parameter check for the ServiceDescription object. The following
     * attribute needs to be set:
     *  - requiresRegistration
     * 
     * @param response
     *            ServiceDescription
     * 
     * @throws MissingParameters
     * 
     * @see ServiceDescription
     */
    public void check(ServiceDescription response) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            if (response != null) {
                // check ServiceDescription object pointer
                if (response.getOfferedPortlets() != null) {

                    List<PortletDescription> portletDesc =
                            response.getOfferedPortlets();

                    for (PortletDescription pd : portletDesc) {

                        check(pd);
                    }

                } else if (response.getRequiresInitCookie() != null) {

                    check(response.getRequiresInitCookie(), true);

                } else if (response.getResourceList() != null) {

                    check(response.getResourceList(), true);

                }
            } else {

                throwMissingParameters("No valid service description " +
                        "response found.");
            }
        }
        
    }

    /**
     * Parameter check for the BlockingInteractionResponse object. The following
     * attribute needs to be set:
     * 
     * either - updateResponse or - redirectURL
     * 
     * @param response
     *            BlockingInteractionResponse
     * 
     * @throws MissingParameters
     * 
     * @see BlockingInteractionResponse
     */
    public void check(BlockingInteractionResponse response) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check BlockingInteractionResponse object pointer
            if (response.getUpdateResponse() != null 
                    && response.getRedirectURL() == null) {

                check(response.getUpdateResponse());

            } else if (response.getRedirectURL() != null 
                    && response.getUpdateResponse() == null) {

                // everything is fine

            } else {

                throwMissingParameters("No valid blocking interaction " +
                        "response. UpdateResponse"
                        + "and redirect url are mutually exclusive");
            }
        }
        
    }

    /**
     * Parameter check for the UpdateResponse object. The following attribute
     * needs to be set:
     * 
     * 1) SessionContext, only if available 2) PortletContext, only if available
     * 3) MarkupContext, only if available 4) NavigationalState
     * 
     * @param response
     *            UpdateResponse
     * 
     * @throws MissingParameters
     * 
     * @see UpdateResponse
     */
    private void check(UpdateResponse response) throws MissingParameters {

        // check UpdateResponse object pointer
        check(response.getSessionContext(), Constants.NILLABLE_TRUE);
        check(response.getPortletContext(), Constants.NILLABLE_TRUE);
        check(response.getMarkupContext(), Constants.NILLABLE_TRUE);

        // TODO: check for valid window states and portlet modes
    }

    /**
     * Parameter check for the MarkupResponse object. The following attribute
     * needs to be set:
     * 
     * 1) MarkupContext 2) SessionContext, only if available
     * 
     * @param response
     *            MarkupResponse
     * 
     * @throws MissingParameters
     * 
     * @see MarkupResponse
     */
    public void check(MarkupResponse response) throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check MarkupResponse object pointer
            check(response.getMarkupContext(), Constants.NILLABLE_FALSE);
            check(response.getSessionContext(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * Validates the PortletDescriptionResponse object
     * 
     * 1) PortletDescription 2) ResourceList, only if available
     * 
     * @param response
     *            PortletDescriptionResponse
     * 
     * @throws MissingParameters
     * 
     * @see PortletDescriptionResponse
     */
    public void check(PortletDescriptionResponse response) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check MarkupResponse object pointer
            check(response.getPortletDescription());
            check(response.getResourceList(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * Validates the PortletPropertyDescriptionResponse object
     * 
     * 1) ResourceList
     * 
     * @param response
     *            PortletPropertyDescriptionResponse
     * 
     * @throws MissingParameters
     * 
     * @see PortletPropertyDescriptionResponse
     */
    public void check(PortletPropertyDescriptionResponse response) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check MarkupResponse object pointer
            // TODO: check ModelDescription

            check(response.getResourceList(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * Validates the DestroyPortletsResponse object
     * 
     * 1) DestroyFailed[], only if available
     * 
     * @param response
     *            DestroyPortletResponse
     * 
     * @throws MissingParameters
     * 
     * @see DestroyPortletsResponse
     */
    public void check(DestroyPortletsResponse response) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check MarkupResponse object pointer
            checkDestroyFailed(response.getDestroyFailed(), Constants.NILLABLE_TRUE);
        }
        
    }

    /**
     * --- THIS IS THE AUXILLARY SECTION OF THE PARAMETER-CHECKER ---
     */

    /**
     * Parameter check for the PortletDescription object. The following
     * parameter needs to be set:
     * 
     * 1) portletHandle 2) markupType[]
     * 
     * @param element
     *            PortletDescription
     * 
     * @throws MissingParameters
     * 
     * @see PortletDescription
     */
    public void check(PortletDescription element) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check PortletDescription object pointer
            if (element.getPortletHandle() == null) {
                throwMissingParameters("A portlet handle has to be set " +
                        "in the portlet description.");
            }

            if (element.getMarkupTypes() == null) {
                throwMissingParameters("Markup types have to be defined " +
                        "in the portlet description.");
            }
        }
        
    }

    /**
     * Parameter check for the PortletDescription object. The following
     * parameter needs to be set:
     * 
     * all templates...
     * 
     * @param element
     *            Templates
     * 
     * @throws MissingParameters
     * 
     * @see PortletDescription
     */
    public void check(Templates element) throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check PortletDescription object pointer
            if (element.getDefaultTemplate() == null) {
                throwMissingParameters("DefaultTemplate has not " +
                        "been set!");
            }

            if (element.getSecureDefaultTemplate() == null) {
                throwMissingParameters("SecureDefaultTemplate " +
                        "Template has not been set!");
            }
        }
        
    }

    /**
     * Parameter check for the SessionContext object. The following parameter
     * needs to be set:
     * 
     * 1) sessionID 2) expire
     * 
     * @param context
     *            SessionContext
     * @param nillable
     *            boolean true, if the SessionContext can be nill false, if the
     *            SessionContext is not nillable
     * 
     * @throws MissingParameters
     * 
     * @see SessionContext
     */
    private void check(SessionContext context, boolean nillable) 
    throws MissingParameters {
        
        if (context != null) {
            if (context.getSessionID() == null) {
                throwMissingParameters("No valid session context found. " +
                        "No session handle set.");
            } else {
                // check, if the ID is valid, instance and length
                // TODO: activate this, if ID is no longer as string declared!
                // check(context.getSessionID());
            }
            if (context.getExpires() == 0) {
                throwMissingParameters("No valid session context found. " +
                        "No session expire set.");
            }
        } else {
            if (!nillable) {
                throwMissingParameters("No valid session context found.");
            }
        }
        
    }

    /**
     * Parameter check for the MarkupContext object. The following parameter
     * needs to be set:
     * 
     * 1) markupBinary and markupString mutually exclusive, if markupType is
     * available 2) locale, if markupType is available
     * 
     * @param context
     *            MarkupContext
     * @param nillable
     *            boolean true, if the MarkupContext can be nill false, if the
     *            MarkupContext is not nillable
     * 
     * @throws MissingParameters
     * 
     * @see MarkupContext
     */
    private void check(MarkupContext context, boolean nillable) 
    throws MissingParameters {
        
        if (context != null) {
            boolean bMarkupBinary = false, bMarkupString = false;
            if (context.getMarkupBinary() != null) {
                bMarkupBinary = true;
            }
            if (context.getMarkupString() != null) {
                bMarkupString = true;
            }

            // XOR of markupBinary and markupString
            if (bMarkupBinary ^ bMarkupString) {
                if (context.getMimeType() == null) {
                    throwMissingParameters("MimeType not set in " +
                            "MarkupContext.");
                } else {
                    if (context.getLocale() == null) {
                        throwMissingParameters("Locale not set in " +
                                "MarkupContext.");
                    }
                }
            }
        } else {
            if (!nillable) {
                throwMissingParameters("No valid markup context found.");
            }
        }
        
    }

    /**
     * Check the PropertyList. The following attributes are mandatory:
     * 
     * 1) Property[]
     * 
     * @param propertyList
     *            PropertyList
     * @param nillable
     *            boolean true, if the PropertyList can be nill false, if the
     *            PropertyList is not nillable
     * 
     * @throws MissingParameters
     * 
     * @see PropertyList
     */
    public void check(PropertyList propertyList, boolean nillable) 
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check only, if object not null, otherwise ignore. Object is
            // defined as nillable
            if (propertyList != null) {
                // property is mandatory
                if (propertyList.getProperties() == null) {
                    throwMissingParameters("PropertyList[] in " +
                            "PropertyList is null");
                }

            } else {
                // check if nillable is allowed
                if (nillable == false) {
                    throwMissingParameters("PropertyList object is null");
                }
            }
        }
        
    }

    /**
     * Check the RegistrationData. The following attributes are mandatory:
     * 
     * 1) ConsumerName 2) ConsumerAgent
     * 
     * @param registrationData
     *            RegistrationData
     * @param nillable
     *            boolean true, if the RegistrationData can be nill false, if
     *            the RegistrationData is not nillable
     * 
     * @throws MissingParameters
     * 
     * @see RegistrationData
     */
    public void check(RegistrationData registrationData, boolean nillable)
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check only, if object not null, otherwise ignore. Object is
            // defined as nillable
            if (registrationData != null) {
                // check the consumer name, is mandatory
                if (registrationData.getConsumerName() == null) {
                    throwMissingParameters("ConsumerName in " +
                            "RegistrationData is null");
                }
                // check the consumer agent, is mandatory
                if (registrationData.getConsumerAgent() == null) {
                    throwMissingParameters("ConsumerAgent in " +
                            "RegistrationData is null");
                }

            } else {
                // if registrationcontext is null, check if nillable is allowed
                if (nillable == false) {
                    throwMissingParameters("RegistrationData object is null");
                }
            }
        }
        
    }

    /**
     * Check a string array. The following attributes are mandatory:
     * 
     * 1) must be a string array 2) must have a array.length > 0
     * 
     * @param array
     *            String[]
     * 
     * @param nillable
     *            boolean true, if the String[] can be nill false, if the
     *            String[] is not nillable
     * 
     * 
     * 
     * @throws MissingParameters
     * 
     * @see String
     */
    private void check(List<String> array, boolean nillable)
    throws MissingParameters {
        
        // check only, if object not null, otherwise ignore. Object is defined
        // as nillable
        if (array != null) {
            // check the array
            if (array.size() == 0) {
                throwMissingParameters("String[] array length is zero (0)");
            }
        } else {
            // if array is null, check if nillable is allowed
            if (nillable == false) {
                throwMissingParameters("String array[] object is null");
            }
        }
        
    }

    /**
     * Check the InteractionParams. The following attributes are mandatory:
     * 
     * 1) PortletStateChange
     * 
     * @param interactionParams
     *            InteractionParams
     * @param nillable
     *            boolean true, if the InteractionParams can be nill false, if
     *            the InteractionParams is not nillable
     * 
     * 
     * @throws MissingParameters
     * 
     * @see InteractionParams
     */
    private void check(InteractionParams interactionParams, boolean nillable)
    throws MissingParameters {
        
        // check only, if object not null, otherwise ignore. Object is defined
        // as nillable
        if (interactionParams != null) {
            // check the portletHandle, is mandatory
            if (interactionParams.getPortletStateChange() == null) {
                throwMissingParameters("PortletStateChange in " +
                        "InteractionParams is null");
            }
        } else {
            // if registrationcontext is null, check if nillable is allowed
            if (nillable == false) {
                throwMissingParameters("InteractionParams object is null");
            }
        }
        
    }

    /**
     * Check the RegistrationContext. The following attributes are mandatory:
     * 
     * 1) RegistrationHandle
     * 
     * @param registrationContext
     *            RegistrationContext
     * 
     * @param nillable
     *            boolean true, if the RegistrationContext can be nill false, if
     *            the RegistrationContext is not nillable
     * 
     * 
     * @throws MissingParameters
     * 
     * @see RegistrationContext
     */
    public void check(RegistrationContext registrationContext, boolean nillable)
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check only, if object not null, otherwise ignore. Object is
            // defined as nillable
            if (registrationContext != null) {
                // check the registrationHandle, is mandatory
                if (registrationContext.getRegistrationHandle() == null) {
                    throwMissingParameters("RegistrationHandle in " +
                            "RegistrationContext is null");
                }
            } else {
                // if registrationcontext is null, check if nillable is allowed
                if (nillable == false) {
                    throwMissingParameters("RegistrationContext object " +
                            "is null");
                }
            }
        }
        
    }

    /**
     * Check the PortletContext. The following attributes are mandatory:
     * 
     * 1) PortletHandle
     * 
     * @param portletContext
     *            PortletContext
     * 
     * @param nillable
     *            boolean true, if the PortletContext can be nill false, if the
     *            PortletContext is not nillable
     * 
     * @throws MissingParameters
     * 
     * @see PortletContext
     */
    public void check(PortletContext portletContext, boolean nillable)
    throws MissingParameters {
        
        if (isCheckEnabled()) {
            // check only, if object not null, otherwise ignore. Object is
            // defined as nillable
            if (portletContext != null) {
                // check the portletHandle, is mandatory
                if (portletContext.getPortletHandle() == null) {
                    throwMissingParameters("PortletHandle in " +
                            "PortletContext is null");
                }
            } else {
                // if registrationcontext is null, check if nillable is allowed
                if (nillable == false) {
                    throwMissingParameters("PortletContext object " +
                            "is null");
                }
            }
        }
        
    }

    /**
     * Check the RuntimeContext. The following attributes are mandatory:
     * 
     * 1) UserAuthentication 2) PortletInstanceKey
     * 
     * @param runtimeContext
     *            RuntimeContext
     * 
     * @param nillable
     *            boolean true, if the RuntimeContext can be nill false, if the
     *            RuntimeContext is not nillable
     * 
     * @throws MissingParameters
     * 
     * @see RuntimeContext
     */
    private void check(RuntimeContext runtimeContext, boolean nillable)
    throws MissingParameters {
        
        // check only, if object not null, otherwise ignore. Object is defined
        // as nillable
        if (runtimeContext != null) {
            // check the userAuthentication, is mandatory
            if (runtimeContext.getUserAuthentication() == null) {
                throwMissingParameters("UserAuthentication in " +
                        "RuntimeContext is null");
            }

            // check the portletHandle, is mandatory
            if (runtimeContext.getPortletInstanceKey() != null) {
                // TODO: activate this, if the string is changed to key type
                // check(runtimeContext.getPortletInstanceKey());
            }
        } else {
            // if registrationcontext is null, check if nillable is allowed
            if (nillable == false) {
                throwMissingParameters("RuntimeContext object is null");
            }
        }
        
    }

    /**
     * Validates the DestroyFailed Array. If DestroyFailed objects are
     * available, they are checked for their content.
     * 
     * @param destroyFailedArray
     *            DestroyFailed[]
     * 
     * @param nillable
     *            boolean true, if the DestroyFailed[] can be nill false, if the
     *            DestroyFailed[] is not nillable
     * 
     */
    private void checkDestroyFailed(List<DestroyFailed> destroyFailedArray, boolean nillable)
    throws MissingParameters {
        
        // check only, if object not null, otherwise ignore. Object is defined
        // as nillable
        if (destroyFailedArray != null) {
            if (destroyFailedArray.size() > 0) {
                for (DestroyFailed df : destroyFailedArray) {
                    // mandatory
                    if (df.getPortletHandle() == null) {
                        throwMissingParameters("Missing Portlet handle " +
                                "in DestroyFailed object.");
                    }

                    // mandatory
                    if (df.getReason() == null) {
                        throwMissingParameters("Missing Reason in " +
                                "DestroyFailed object.");
                    }
                }
            } else {
                throwMissingParameters("DestroyFailedArray length is " +
                        "zero (0).");
            }
        } else {
            // if destroyFailedArray is null, check if nillable is allowed
            if (nillable == false) {
                throwMissingParameters("DestroyFailed[] object is null");
            }
        }
        
    }

    /**
     * Check the UserContext. The following attributes are mandatory:
     * 
     * 1) UserContextKey
     * 
     * @param userContext
     *            UserContext
     * @param nillable
     *            boolean true, if the UserContext can be nill false, if the
     *            UserContext is not nillable
     * 
     * 
     * @throws MissingParameters
     * 
     * @see UserContext
     */
    private void check(UserContext userContext, boolean nillable) 
    throws MissingParameters {
        
        // check only, if object not null, otherwise ignore. Object is defined
        // as nillable
        if (userContext != null) {
            // check the UserContextKey, is mandatory
            if (userContext.getUserContextKey() == null) {
                throwMissingParameters("UserContextKey in " +
                        "UserContext is null");
            }
        } else {
            // if registrationcontext is null, check if nillable is allowed
            if (nillable == false) {
                throwMissingParameters("UserContext object is null");
            }
        }
        
    }

    /**
     * Validates the ResourceList object for available resources.
     * 
     * @param resourceList
     * @param nillable
     *            true if null is allowed
     * 
     * @throws MissingParameters
     */
    private void check(ResourceList resourceList, boolean nillable) 
    throws MissingParameters {
        
        // check only, if object not null.
        if (resourceList != null) {
            // check for Resources, it's mandatory
            if (resourceList.getResources() == null) {
                throwMissingParameters("Resource[] is null");
            } else {
                List<Resource> resourceArray = resourceList.getResources();
                if (resourceArray.size() < 1) {
                    throwMissingParameters("ResourceArray length is " +
                            "zero (0).");
                }
            }
        } else {
            // if registrationcontext is null, check if nillable is allowed
            if (nillable == false) {
                throwMissingParameters("ResourceList object is null");
            }
        }
        
    }

    /**
     * Validates the CookieProtocol object
     * 
     * @param requiresInit
     * @param nillable
     *            true if null is allowed
     * 
     * @throws MissingParameters
     */
    private void check(CookieProtocol requiresInit, boolean nillable) 
    throws MissingParameters {
        
        // check only, if object not null.
        if (requiresInit != null) {
            if (!requiresInit.equals(CookieProtocol.NONE)
                    && !requiresInit.equals(CookieProtocol.PER_GROUP)
                    && !requiresInit.equals(CookieProtocol.PER_USER)) {
                throwMissingParameters("Invalid value (" + requiresInit.toString()
                        + ") of CookieProtocol object.");
            }
        } else {
            // if requiresInit is null, check if nillable is allowed
            if (nillable == false) {
                throwMissingParameters("RequiresInitCookie object is null");
            }
        }
        
    }

    /**
     * Check the MarkupParams. The following attributes are mandatory:
     * 
     * 1) ClientData 2) Locale 3) MimeType 4) Mode 5) WindowState
     * 
     * @param markupParams
     *            MarkupParams
     * 
     * @param nillable
     *            boolean true, if the MarkupParams can be nill false, if the
     *            MarkupParams is not nillable
     * 
     * 
     * @throws MissingParameters
     * 
     * @see MarkupParams
     */
    private void check(MarkupParams markupParams, boolean nillable) 
    throws MissingParameters {
        
        // check only, if object not null, otherwise ignore. Object is defined
        // as nillable
        if (markupParams != null) {
            // check ClientData, is mandatory
            if (markupParams.getClientData() == null) {
                throwMissingParameters("ClientData in " +
                        "MarkupParams is null");
            }
            if (markupParams.getLocales() == null) {
                throwMissingParameters("Locales in " +
                        "MarkupParams is null");
            }
            if (markupParams.getMimeTypes() == null) {
                throwMissingParameters("MimeTypes in " +
                        "MarkupParams is null");
            }
            if (markupParams.getMode() == null) {
                throwMissingParameters("Mode in " +
                        "MarkupParams is null");
            }
            if (markupParams.getWindowState() == null) {
                throwMissingParameters("WindowState in " +
                        "MarkupParams is null");
            }
        } else {
            // if registrationcontext is null, check if nillable is allowed
            if (nillable == false) {
                throwMissingParameters("MarkupParams object is null");
            }
        }
        
    }

    /**
     * Creates and throws a MissingParameters exception
     * 
     * @param msg
     *            String error message
     * 
     * @throws MissingParameters
     * 
     * @see MissingParameters
     */
    private void throwMissingParameters(String msg)
    throws MissingParameters {
      MissingParametersFault fault = new MissingParametersFault();

      MissingParameters e = new MissingParameters(msg, fault);
      throw e;
        
    }
    
}
