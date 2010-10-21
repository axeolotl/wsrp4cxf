
package oasis.names.tc.wsrp.v1.intf;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.1.1
 * Wed Aug 13 15:22:22 CEST 2008
 * Generated source version: 2.1.1
 * 
 */

@WebFault(name = "InvalidRegistration", targetNamespace = "urn:oasis:names:tc:wsrp:v1:types")

public class InvalidRegistration extends Exception {
    public static final long serialVersionUID = 20080813152222L;
    
    private oasis.names.tc.wsrp.v1.types.InvalidRegistrationFault invalidRegistration;

    public InvalidRegistration() {
        super();
    }
    
    public InvalidRegistration(String message) {
        super(message);
    }
    
    public InvalidRegistration(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRegistration(String message, oasis.names.tc.wsrp.v1.types.InvalidRegistrationFault invalidRegistration) {
        super(message);
        this.invalidRegistration = invalidRegistration;
    }

    public InvalidRegistration(String message, oasis.names.tc.wsrp.v1.types.InvalidRegistrationFault invalidRegistration, Throwable cause) {
        super(message, cause);
        this.invalidRegistration = invalidRegistration;
    }

    public oasis.names.tc.wsrp.v1.types.InvalidRegistrationFault getFaultInfo() {
        return this.invalidRegistration;
    }
}