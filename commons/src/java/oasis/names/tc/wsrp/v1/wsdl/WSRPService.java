
/*
 * 
 */

package oasis.names.tc.wsrp.v1.wsdl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import oasis.names.tc.wsrp.v1.intf.WSRPV1MarkupPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1PortletManagementPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1RegistrationPortType;
import oasis.names.tc.wsrp.v1.intf.WSRPV1ServiceDescriptionPortType;

/**
 * This class was generated by Apache CXF 2.1.1
 * Wed Aug 13 15:22:22 CEST 2008
 * Generated source version: 2.1.1
 * patched for _v1_ service name 
 */
 
/*
 * 
 */

@WebServiceClient(name = "WSRP_v1_Service",
                  wsdlLocation = "file:wsrp_service.wsdl",
                  targetNamespace = "urn:oasis:names:tc:wsrp:v2:wsdl")
public class WSRPService extends Service {

    public final static QName SERVICE = new QName("urn:oasis:names:tc:wsrp:v2:wsdl", "WSRP_v1_Service");
    public final static QName WSRPPortletManagementService = new QName("urn:oasis:names:tc:wsrp:v2:wsdl", "WSRP_v1_PortletManagement_Service");
    public final static QName WSRPRegistrationService = new QName("urn:oasis:names:tc:wsrp:v2:wsdl", "WSRP_v1_Registration_Service");
    public final static QName WSRPBaseService = new QName("urn:oasis:names:tc:wsrp:v2:wsdl", "WSRP_v1_Markup_Service");
    public final static QName WSRPServiceDescriptionService = new QName("urn:oasis:names:tc:wsrp:v2:wsdl", "WSRP_v1_ServiceDescription_Service");

    public WSRPService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public WSRPService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     * 
     * @return
     *     returns WSRPV1PortletManagementPortType
     */
    @WebEndpoint(name = "WSRP_v1_PortletManagement_Service")
    public WSRPV1PortletManagementPortType getWSRPPortletManagementService() {
        return super.getPort(WSRPPortletManagementService, WSRPV1PortletManagementPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSRPV1PortletManagementPortType
     */
    @WebEndpoint(name = "WSRP_v1_PortletManagement_Service")
    public WSRPV1PortletManagementPortType getWSRPPortletManagementService(WebServiceFeature... features) {
        return super.getPort(WSRPPortletManagementService, WSRPV1PortletManagementPortType.class, features);
    }
    /**
     * 
     * @return
     *     returns WSRPV1RegistrationPortType
     */
    @WebEndpoint(name = "WSRP_v1_Registration_Service")
    public WSRPV1RegistrationPortType getWSRPRegistrationService() {
        return super.getPort(WSRPRegistrationService, WSRPV1RegistrationPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSRPV1RegistrationPortType
     */
    @WebEndpoint(name = "WSRP_v1_Registration_Service")
    public WSRPV1RegistrationPortType getWSRPRegistrationService(WebServiceFeature... features) {
        return super.getPort(WSRPRegistrationService, WSRPV1RegistrationPortType.class, features);
    }
    /**
     * 
     * @return
     *     returns WSRPV1MarkupPortType
     */
    @WebEndpoint(name = "WSRP_v1_Markup_Service")
    public WSRPV1MarkupPortType getWSRPBaseService() {
        return super.getPort(WSRPBaseService, WSRPV1MarkupPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSRPV1MarkupPortType
     */
    @WebEndpoint(name = "WSRP_v1_Markup_Service")
    public WSRPV1MarkupPortType getWSRPBaseService(WebServiceFeature... features) {
        return super.getPort(WSRPBaseService, WSRPV1MarkupPortType.class, features);
    }
    /**
     * 
     * @return
     *     returns WSRPV1ServiceDescriptionPortType
     */
    @WebEndpoint(name = "WSRP_v1_ServiceDescription_Service")
    public WSRPV1ServiceDescriptionPortType getWSRPServiceDescriptionService() {
        return super.getPort(WSRPServiceDescriptionService, WSRPV1ServiceDescriptionPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WSRPV1ServiceDescriptionPortType
     */
    @WebEndpoint(name = "WSRP_v1_ServiceDescription_Service")
    public WSRPV1ServiceDescriptionPortType getWSRPServiceDescriptionService(WebServiceFeature... features) {
        return super.getPort(WSRPServiceDescriptionService, WSRPV1ServiceDescriptionPortType.class, features);
    }

}
