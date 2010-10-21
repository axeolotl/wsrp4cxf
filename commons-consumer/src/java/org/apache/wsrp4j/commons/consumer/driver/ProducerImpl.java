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

import java.net.URL;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oasis.names.tc.wsrp.v1.intf.WSRPV1PortletManagementPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1RegistrationPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1ServiceDescriptionPortType;
import oasis.names.tc.wsrp.v1.intf.*;
import oasis.names.tc.wsrp.v1.types.PortletDescription;
import oasis.names.tc.wsrp.v1.types.RegistrationContext;
import oasis.names.tc.wsrp.v1.types.RegistrationData;
import oasis.names.tc.wsrp.v1.types.RegistrationState;
import oasis.names.tc.wsrp.v1.types.ReturnAny;
import oasis.names.tc.wsrp.v1.types.ServiceDescription;
import oasis.names.tc.wsrp.v1.types.GetServiceDescription;
import oasis.names.tc.wsrp.v1.types.ModifyRegistration;
import oasis.names.tc.wsrp.v1.wsdl.WSRPService;

import org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer;
import org.apache.wsrp4j.commons.exception.ErrorCodes;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.exception.WSRPXHelper;
import org.apache.wsrp4j.commons.util.ParameterChecker;
import org.apache.wsrp4j.commons.util.impl.StateChangedServiceImpl;


/**
 * A consumer representation of a WSRP-producer providing WSRP-portlets.
 * Generally a producer can expose up to four WSRP-Interfaces. These interfaces 
 * are Markup-, Service Description-,Registration- and 
 * WSRPPortlet Management Interface. Whereas the Registration- and 
 * Portlet Management Interface are optional.
 *
 * @see Producer
 * @version $Id: ProducerImpl.java 405796 2006-05-10 16:48:00Z dlouzan $
 **/
public class ProducerImpl extends StateChangedServiceImpl implements Producer {
    
    private static Log log = LogFactory.getLog(ProducerImpl.class);
    
    // ID and misc. infos
    private String name;
    private String producerID;
    private String description;
    
    // URL's of the WSRP interfaces
    private String registrationURL;
    private String serviceDescriptionURL;
    private String markupURL;
    private String portletManagementURL;
    
    // Registration
    private boolean registrationRequired = false;
    private RegistrationContext registrationContext;
    private RegistrationData consumerRegData;
    
    // Service and portlet description
    private ServiceDescription serviceDescription;
    private Hashtable portletDesc;
    
    // Stubs
    private WSRPV1ServiceDescriptionPortType serviceDescriptionInterface;
    private WSRPV1RegistrationPortType registrationInterface;
    private WSRPV1PortletManagementPortType portletManagementInterface;
    
    // Parameter checker
    private ParameterChecker checker;
    
    /**
     * Default Constructor should not be used directly. But is
     * required for persistence support by castor.
     **/
    public ProducerImpl() {
        this.portletDesc = new Hashtable();
        checker = new ParameterChecker();
        
    }
    
    /**
     * This constructor can be used to create a new producer object
     **/
    public ProducerImpl(String producerID, String markupURL, 
            String serviceDescriptionURL)
    throws WSRPException {
        
        this();
        this.producerID = producerID;
        
        if (markupURL != null) {
            this.markupURL = markupURL;
        } else {
            WSRPXHelper.throwX(log, ErrorCodes.MISSING_MARKUP_PORT);
        }
        
        if (serviceDescriptionURL != null) {
            
            this.serviceDescriptionURL = serviceDescriptionURL;
            initServiceDescInterface(serviceDescriptionURL);
            
        } else {
            WSRPXHelper.throwX(log, ErrorCodes.MISSING_SERVICE_DESC_PORT);
        }
        
    }
    
    /**
     * This constructor can be used to create a new producer object
     **/
    public ProducerImpl(
            String producerID,
            String markupURL,
            String serviceDescriptionURL,
            String registrationURL,
            String portletManagementURL,
            RegistrationData registrationData)
            throws WSRPException {
        
        this(producerID, markupURL, serviceDescriptionURL);
        
        if (registrationURL != null) {
            
            initRegistrationInterface(registrationURL);
            this.registrationURL = registrationURL;
            this.consumerRegData = registrationData;
        }
        
        if (portletManagementURL != null) {
            this.portletManagementURL = portletManagementURL;
            initPortletManagementInterface(portletManagementURL);
        }
    }
    
    /**
     * Initialize the service description interface of the producer.
     *
     * @param serviceDescriptionURL The URL of the producers service 
     * description interface
     **/
    public void initServiceDescInterface(String serviceDescriptionURL) 
    throws WSRPException {
        
        // just in case this has not been done before and for castor persistence
        this.serviceDescriptionURL = serviceDescriptionURL;
        
        try {
            WSRPService service = new WSRPService(new URL(serviceDescriptionURL));
            serviceDescriptionInterface =
                    service.getWSRPServiceDescriptionService();
            
        } catch (java.net.MalformedURLException urlEx) {
            
            WSRPXHelper.throwX(log, ErrorCodes.INVALID_URL_OF_SERVICE_DESC_PORT,
                    urlEx);

        } catch (Exception xmlEx) {

            WSRPXHelper.throwX(log, ErrorCodes.INIT_OF_SERVICE_DESC_PORT_FAILED,
                    xmlEx);

        }
    }
    
    /**
     * Initialize the registration interface of the producer.
     *
     * @param registrationURL The URL of the producers registration interface
     **/
    public void initRegistrationInterface(String registrationURL) 
    throws WSRPException {
        
        // just in case this has not been done before and for castor persistence
        this.registrationURL = registrationURL;
        
        try {
          WSRPService service = new WSRPService(new URL(registrationURL));
            registrationInterface = service.getWSRPRegistrationService();
            
        } catch (java.net.MalformedURLException urlEx) {
            
            WSRPXHelper.throwX(log, ErrorCodes.INVALID_URL_OF_REGISTRATION_PORT,
                    urlEx);

        } catch (Exception xmlEx) {

            WSRPXHelper.throwX(log, ErrorCodes.INIT_OF_SERVICE_DESC_PORT_FAILED,
                    xmlEx);

        }
    }
    
    /**
     * Get the URL of the producers service description interface.
     *
     * @return URL of the service description interface.
     **/
    public String getServiceDescriptionInterfaceEndpoint() {
        return this.serviceDescriptionURL;
    }
    
    /**
     * Set the URL of the producers service description interface.
     *
     * @param url URL of the service description interface.
     **/
    public void setServiceDescriptionInterfaceEndpoint(String url) {
        serviceDescriptionURL = url;
        stateChanged();
    }
    
    /**
     * Get the URL of the producers registration interface.
     *
     * @return URL of the registration interface.
     **/
    public String getRegistrationInterfaceEndpoint() {
        return registrationURL;
    }
    
    /**
     * Set the URL of the producers registration interface.
     *
     * @param url URL of the registration interface.
     **/
    public void setRegistrationInterfaceEndpoint(String url) {
        registrationURL = url;
        stateChanged();
    }
    
    /**
     * Get the ID of the producer.
     *
     * @return The ID of the producer
     **/
    public String getID() {
        return producerID;
    }
    
    /**
     * Set the ID of the producer to he given value.
     *
     * @param id ID of the producer.
     **/
    public void setID(String id) {
        producerID = id;
        stateChanged();
    }
    
    /**
     * Get the name of the producer.
     *
     * @return The name of the producer
     **/
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the producer.
     *
     * @param name The name of the producer
     **/
    public void setName(String name) {
        this.name = name;
        stateChanged();
    }
    
    /**
     * Get a description of the producer.
     *
     * @return A description of the producer
     **/
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Set a description of the producer.
     *
     * @param description Some descriptive information about the producer
     **/
    public void setDescription(String description) {
        this.description = description;
        stateChanged();
    }
    
    /**
     * Indicates wether or not the producer requires consumer registration.
     *
     * @return True if consumer registration is required.
     **/
    public boolean isRegistrationRequired() {
        return this.registrationRequired;
    }
    
    /**
     * Define if the producer requires in-band registration or not.
     *
     * @param registrationRequired True if the producer requires in-band 
     * registration
     **/
    public void setIsRegistrationRequired(boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
        stateChanged();
    }
    
    /**
     * Get the registration data the consumer uses to register at this producer.
     *
     * @return The consumer registration data
     **/
    public RegistrationData getRegistrationData() {
        return consumerRegData;
    }
    
    /**
     * Set the registration the consumer uses the register at this producer.
     *
     * @param regData The registration data which is used to register at this 
     * producer
     **/
    public void setRegistrationData(RegistrationData regData) {
        consumerRegData = regData;
        stateChanged();
    }
    
    /**
     * Get the current registration context of the consumer registered at this 
     * producer or null
     * if no registration is required or happend so far.
     *
     * @return The current registration context of the consumer at this 
     * producer or null.
     **/
    public RegistrationContext getRegistrationContext() {
        return this.registrationContext;
    }
    
    /**
     * Set the registration context.
     *
     * @param registrationContext The registration context of a consumer 
     * registered at the producer.
     **/
    public void setRegistrationContext(
            RegistrationContext registrationContext) {
        if (registrationContext != null) {
            this.registrationContext = registrationContext;
            stateChanged();
        }
    }
    
    /**
     * Same as getServiceDescription(false)
     **/
    public ServiceDescription getServiceDescription() throws WSRPException {
        return getServiceDescription(false);
    }
    
    /**
     * Get the service description of the producer
     *
     * @param newRequest If set to true a new request is send to the producer 
     * otherwise a cached service description
     * is used if available
     *
     * @return Service description of the producer
     **/
    public ServiceDescription getServiceDescription(boolean newRequest) 
    throws WSRPException {
        if (this.serviceDescription == null || newRequest) {
            GetServiceDescription request = new GetServiceDescription();
            
            if (registrationContext != null) {
                request.setRegistrationContext(registrationContext);
            }
            
            // TODO: Set desired locales which are supported by the consumer
            //request.setDesiredLocales(new String[]{Constants.LOCALE_EN_US, 
            // Constants.LOCALE_DE_DE});
            // for now request all locales
            request.getDesiredLocales().clear();
            
            ServiceDescription response = null;
            try {
                
                response = serviceDescriptionInterface.
                        getServiceDescription(request);
                
                if (response != null) {
                    
                    try {
                        
                        checker.check(response);
                        
                    } catch (MissingParameters wsrpFault) {

                        WSRPXHelper.handleWSRPFault(log, wsrpFault);

                    }
                    
                    if (registrationContext == null && 
                            response.isRequiresRegistration()) {
                        register(consumerRegData);
                        getServiceDescription(true);
                        
                    } else {
                        setServiceDescription(response);
                    }
                    
                } else {
                    
                    WSRPXHelper.throwX(log, ErrorCodes.INVALID_SERVICE_DESCRIPTION);
                    
                }
                
            } catch (InvalidRegistration invalidReg) {
                
                // producer did not accept the registration
                // lets try it again
                register(consumerRegData);
                getServiceDescription(true);
                
            } catch (OperationFailed wsrpFault) {
                
                WSRPXHelper.handleWSRPFault(log, wsrpFault);
                
            }
        }
        
        return this.serviceDescription;
    }
    
    /**
     * Get the portlet description of the portlet with the given handle from the
     * service description or null if the producer doesn't offer an portlet 
     * with this handle in it's service description.
     *
     * @param portletHandle The portlet handle of the portlet
     *
     * @return The portlet description of the portlet with the given handle
     **/
    public PortletDescription getPortletDescription(String portletHandle) 
    throws WSRPException {
        
        PortletDescription desc = null;
        if (portletHandle != null) {
            
            if (serviceDescription == null) {
                
                // fetch a new portlet description from the producer
                getServiceDescription();
            }
            
            desc = (PortletDescription)portletDesc.get(portletHandle);
            
            if (desc == null) {
                
                WSRPXHelper.throwX(log, ErrorCodes.INVALID_PORTLET_HANDLE);
            }
        }
        
        return desc;
    }
    
    /**
     * Set the service description.
     **/
    public void setServiceDescription(ServiceDescription serviceDescription) {
        if (serviceDescription != null) {
            this.serviceDescription = serviceDescription;
            updatePortletDescriptions(serviceDescription);
            setIsRegistrationRequired(
                    serviceDescription.isRequiresRegistration());
        }
    }
    
    /**
     * Add an portlet description to the producer. This portlet description is
     * accessable through the portlet handle in the portlet description. If the
     * producer has already an portlet description with this portlet handle than
     * the old description will be overwritten.
     *
     * @param portletDescription New portlet description
     **/
    public void addPortletDescription(PortletDescription portletDescription) {
        if (portletDescription != null) {
            this.portletDesc.put(portletDescription.getPortletHandle(), 
                    portletDescription);
        }
    }
    
    private void updatePortletDescriptions(
            ServiceDescription serviceDescription) {
        if (serviceDescription != null) {
            List<PortletDescription> offeredPortlets =
                    serviceDescription.getOfferedPortlets();
            if (offeredPortlets != null) {
                this.portletDesc.clear();
                
                for (PortletDescription pd: offeredPortlets) {
                    if (pd != null) {
                        addPortletDescription(pd);
                    }
                    
                }
            }
        }
    }
    
    /**
     * Method establishes a relationship between consumer and producer.
     *
     * Note: A additional call of setRegistrationContext() is not neccesary
     *
     * @param registrationData Data which is used to register the consumer
     *
     * @return The registration context received by the producer
     **/
    public RegistrationContext register(RegistrationData registrationData) 
    throws WSRPException {
        
        if (registrationData != null && this.registrationInterface != null) {
            
            try {
                
                RegistrationContext regContext = 
                        registrationInterface.register(registrationData);
                checker.check(regContext, false);
                setRegistrationContext(regContext);
                
            } catch (OperationFailed operationFailed) {
              WSRPXHelper.handleWSRPFault(log, operationFailed);
            } catch (MissingParameters missingParameters) {
              WSRPXHelper.handleWSRPFault(log, missingParameters);
            }

        } else {
            
            // TODO: Either no registration data or producer does not support 
            // in-band registration
        }
        
        return this.registrationContext;
    }
    
    /**
     * Can be used to modify the relationship between consumer and producer.
     *
     * Note: A additional call of setRegistrationContext() is not neccesary
     *
     * @param registrationData The new registration data
     *
     * @return New registration context
     **/
    public RegistrationState modifyRegistration(
            RegistrationData registrationData) 
    throws WSRPException {
        
        RegistrationState newState = null;
        
        try {
            if (registrationData != null && 
                    this.registrationInterface != null) {
                
                ModifyRegistration request = new ModifyRegistration();
                if (registrationContext != null) {
                    request.setRegistrationContext(registrationContext);
                }
                request.setRegistrationData(registrationData);
                
                newState = registrationInterface.modifyRegistration(request);
                
                getRegistrationContext().setRegistrationState(
                        newState.getRegistrationState());
                stateChanged();
                
            } else {
                
                // TODO: Either no data or producer does not support in-band 
                // registration
            }
        } catch (InvalidRegistration invalidRegistration) {
          WSRPXHelper.handleWSRPFault(log, invalidRegistration);
        } catch (OperationFailed operationFailed) {
          WSRPXHelper.handleWSRPFault(log, operationFailed);
        } catch (MissingParameters missingParameters) {
          WSRPXHelper.handleWSRPFault(log, missingParameters);
        }

      return newState;
    }
    
    /**
     * End an existing consumer producer relationship and remove the 
     * registration context
     *
     * @return Can be anything
     **/
    public ReturnAny deregister() throws WSRPException {
        ReturnAny any = null;
        
        try {
            if (registrationContext != null && 
                    this.registrationInterface != null) {
                
                any = registrationInterface.deregister(
                        this.registrationContext);
                registrationContext = null;
                registrationRequired = false;
                
                stateChanged();
                
            } else {
                // TODO: Either we aren't registered at the producer or
                //      the producer does not support in-band registration
            }
            
        } catch (InvalidRegistration invalidRegistration) {
          WSRPXHelper.handleWSRPFault(log, invalidRegistration);
        } catch (OperationFailed operationFailed) {
          WSRPXHelper.handleWSRPFault(log, operationFailed);
        }

      return any;
    }
    /**
     * @see org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer#getMarkupInterfaceEndpoint()
     */
    public String getMarkupInterfaceEndpoint() {
        return markupURL;
    }
    
    /**
     * @see org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer#setMarkupInterfaceEndpoint(java.lang.String)
     */
    public void setMarkupInterfaceEndpoint(String url) {
        markupURL = url;
        stateChanged();
    }
    
    /**
     * @see org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer#getPortletManagementInterfaceEndpoint()
     */
    public String getPortletManagementInterfaceEndpoint() {
        return portletManagementURL;
    }
    
    /**
     * @see org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer#setPortletManagementInterfaceEndpoint(java.lang.String)
     */
    public void setPortletManagementInterfaceEndpoint(String url) {
        portletManagementURL = url;
        stateChanged();
    }
    
    public void initPortletManagementInterface(String portletManagementURL) 
    throws WSRPException {
        final String MN = "initPortletManagementInterface";
        
        // just in case this has not been done before and for castor persistence
        this.portletManagementURL = portletManagementURL;
        
        try {
            WSRPService service = new WSRPService(new URL(portletManagementURL));
            portletManagementInterface = 
                    service.getWSRPPortletManagementService();
        } catch (java.net.MalformedURLException urlEx) {
            
            WSRPXHelper.throwX(
                    log,
                    ErrorCodes.INVALID_URL_OF_PORTLET_MGMT_PORT,
                    urlEx);
            
        }
    }
    
    public WSRPV1PortletManagementPortType getPortletManagementInterface() {
        return portletManagementInterface;
    }
    
    public WSRPV1RegistrationPortType getRegistrationInterface() {
        return registrationInterface;
    }
    
    
    public WSRPV1ServiceDescriptionPortType
            getServiceDescriptionInterface() {
        return serviceDescriptionInterface;
    }
    
    public boolean isPortletManagementInferfaceSupported() {
        if ( portletManagementURL != null ) {
            return true;
        }
        return false;
    }
    
    public boolean isRegistrationInterfaceSupported() {
        if ( registrationURL != null ) {
            return true;
        }
        return false;
    }
    
}
