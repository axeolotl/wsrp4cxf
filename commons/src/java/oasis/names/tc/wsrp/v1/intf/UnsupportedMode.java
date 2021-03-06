
package oasis.names.tc.wsrp.v1.intf;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.1.1
 * Wed Aug 13 15:22:22 CEST 2008
 * Generated source version: 2.1.1
 * 
 */

@WebFault(name = "UnsupportedMode", targetNamespace = "urn:oasis:names:tc:wsrp:v1:types")

public class UnsupportedMode extends Exception {
    public static final long serialVersionUID = 20080813152222L;
    
    private oasis.names.tc.wsrp.v1.types.UnsupportedModeFault unsupportedMode;

    public UnsupportedMode() {
        super();
    }
    
    public UnsupportedMode(String message) {
        super(message);
    }
    
    public UnsupportedMode(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMode(String message, oasis.names.tc.wsrp.v1.types.UnsupportedModeFault unsupportedMode) {
        super(message);
        this.unsupportedMode = unsupportedMode;
    }

    public UnsupportedMode(String message, oasis.names.tc.wsrp.v1.types.UnsupportedModeFault unsupportedMode, Throwable cause) {
        super(message, cause);
        this.unsupportedMode = unsupportedMode;
    }

    public oasis.names.tc.wsrp.v1.types.UnsupportedModeFault getFaultInfo() {
        return this.unsupportedMode;
    }
}
