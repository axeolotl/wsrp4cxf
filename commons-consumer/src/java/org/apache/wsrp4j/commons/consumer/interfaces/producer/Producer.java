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
package org.apache.wsrp4j.commons.consumer.interfaces.producer;

import oasis.names.tc.wsrp.v1.intf.WSRPV1PortletManagementPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1RegistrationPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1ServiceDescriptionPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1PortletManagementPortType;
import oasis.names.tc.wsrp.v1.types.PortletDescription;
import oasis.names.tc.wsrp.v1.types.RegistrationContext;
import oasis.names.tc.wsrp.v1.types.RegistrationData;
import oasis.names.tc.wsrp.v1.types.RegistrationState;
import oasis.names.tc.wsrp.v1.types.ReturnAny;
import oasis.names.tc.wsrp.v1.types.ServiceDescription;

import org.apache.wsrp4j.commons.exception.WSRPException;


/**
 * A consumer representation of a WSRP-producer providing WSRP-portlets.
 * Generally a producer can expose up to four WSRP-Interfaces. These 
 * interfaces are Markup-, Service Description-,Registration- and 
 * Portlet Management Interface. Whereas the Registration- and 
 * Portlet Management Interface are optional.
 * 
 * @version $Id: Producer.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface Producer {

    /**
     * Get the name of the producer.               
     *
     * @return The name of the producer
     **/
    String getName();

    /**
     * Set the name of the producer.
     *
     * @param name The name of the producer     
     **/
    void setName(String name);

    /**
     * Get the ID of the producer.
     * 
     * @return The ID of the producer
     **/
    String getID();

    /**
     * Set the ID of the producer to he given value.
     * 
     * @param id ID of the producer.
     **/
    void setID(String id);

    /**
     * Get a description of the producer.
     *
     * @return A description of the producer
     **/
    String getDescription();

    /**
     * Set a description of the producer.
     *
     * @param description Some descriptive information about the producer    
     **/
    void setDescription(String description);

    /**
     * Get the URL of the producers service description interface.
     *
     * @return URL of the service description interface.
     **/
    String getServiceDescriptionInterfaceEndpoint();

    /**
     * Set the URL of the producers service description interface.
     *
     * @param url of the service description interface.
     **/
    void setServiceDescriptionInterfaceEndpoint(String url);

	/**
	 * Get the producers service description interface.
	 *
	 * @return service description interface.
	 **/
	WSRPV1ServiceDescriptionPortType getServiceDescriptionInterface();

    /**
     * Get the URL of the producers markup interface.
     *
     * @return URL of the markup interface.
     **/
    String getMarkupInterfaceEndpoint();

    /**
     * Set the URL of the producers markup interface.
     *
     * @param url of the markup interface.
     **/
    void setMarkupInterfaceEndpoint(String url);

	/**
	 * Get the URL of the producers portlet management interface.
	 *
	 * @return URL of the portlet management interface.
	 **/
	String getPortletManagementInterfaceEndpoint();

	/**
	 * Set the URL of the producers portlet management interface.
	 *
	 * @param url of the portlet management interface.
	 **/
	void setPortletManagementInterfaceEndpoint(String url);

	/**
	 * Get the producers portlet management interface.
	 *
	 * @return portlet management interface.
	 **/
	WSRPV1PortletManagementPortType getPortletManagementInterface();

	/**
	 * Get the URL of the producers registration interface.
	 *
	 * @return URL of the registration interface.
	 **/
	String getRegistrationInterfaceEndpoint();

	/**
	 * Set the URL of the producers registration interface.
	 *
	 * @param url of the registration interface.
	 **/
	void setRegistrationInterfaceEndpoint(String url);

	/**
	 * Get the producers registration interface.
	 *
	 * @return registration interface.
	 **/
	WSRPV1RegistrationPortType getRegistrationInterface();

    /**
     * Indicates wether or not the producer requires consumer registration.
     *
     * @return True if consumer registration is required.
     **/
    boolean isRegistrationRequired();

    /**
     * Define if the producer requires in-band registration or not.
     * 
     * @param registrationRequired True if the producer requires in-band 
     * registration
     **/
    void setIsRegistrationRequired(boolean registrationRequired);

    /**
     * Get the registration data the consumer uses to register at this producer.
     * 
     * @return The consumer registration data
     **/
    RegistrationData getRegistrationData();

    /**
     * Set the registration the consumer uses the register at this producer.
     * 
     * @param regData The registration data which is used to register at 
     * this producer
     **/
    void setRegistrationData(RegistrationData regData);

    /**
     * Get the service description of the producer
     * 
     * @param newRequest If set to true a new request is send to the producer 
     * otherwise a cached service description is used if available
     * 
     * @return Service description of the producer
     **/
    ServiceDescription getServiceDescription(boolean newRequest) 
    throws WSRPException;

    /**
     * Same as getServiceDescription(false)
     **/
    ServiceDescription getServiceDescription() throws WSRPException;

    /**
     * Get the portlet description of the portlet with the given handle or 
     * null if the producer doesn't know an portlet with this handle.
     * 
     * @param portletHandle The portlet handle of the portlet
     * 
     * @return The portlet description of the portlet with the given handle
     **/
    PortletDescription getPortletDescription(String portletHandle) 
    throws WSRPException;

    /**
     * Add an portlet description to the producer. This portlet description is
     * accessable through the portlet handle in the portlet description. If the
     * producer has already an portlet description with this portlet handle than
     * the old description will be overwritten.
     * 
     * @param portletDescription New portlet description 
     **/
    void addPortletDescription(PortletDescription portletDescription);

    /**
     * Get the current registration context of the consumer registered at this 
     * producer or null if no registration is required or happend so far.
     * 
     * @return The current registration context of the consumer at this
     * producer or null.
     **/
    RegistrationContext getRegistrationContext();

    /**
     * Set the registration context.
     * 
     * @param registrationContext The registration context of a consumer 
     * registered at the producer.
     **/
    void setRegistrationContext(RegistrationContext registrationContext);

    /**
     * Method establishes a relationship between consumer and producer.
     * 
     * Note: A additional call of setRegistrationContext() is not neccesary 
     *      
     * @param registrationData Data which is used to register the consumer     
     * 
     * @return The registration context received by the producer      
     **/
    RegistrationContext register(RegistrationData registrationData) 
    throws WSRPException;

    /**
     * Can be used to modify the relationship between consumer and producer.
     * 
     * Note: A additional call of setRegistrationContext() is not neccesary
     *     
     * @param registrationData The new registration data                 
     * 
     * @return New registration context     
     **/
    RegistrationState modifyRegistration(RegistrationData registrationData) 
    throws WSRPException;

    /**
     * End an existing consumer producer relationship and remove the 
     * registration context        
     * 
     * @return Can be anything
     **/
    ReturnAny deregister() throws WSRPException;
    
    /**
     * Check wether the optional registration interface is supported
     *
     * @return true if a registration interface endpoint URL is set
     */
    boolean isRegistrationInterfaceSupported();

	/**
	 * Check wether the optional portlet management interface is supported
	 *
	 * @return true if a portlet management interface endpoint URL is set
	 */
	boolean isPortletManagementInferfaceSupported();

}
