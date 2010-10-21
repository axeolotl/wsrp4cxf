
package oasis.names.tc.wsrp.v1.intf;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.1.1
 * Wed Aug 13 15:22:22 CEST 2008
 * Generated source version: 2.1.1
 * 
 */

@WebFault(name = "InconsistentParameters", targetNamespace = "urn:oasis:names:tc:wsrp:v1:types")

public class InconsistentParameters extends Exception {
    public static final long serialVersionUID = 20080813152222L;
    
    private oasis.names.tc.wsrp.v1.types.InconsistentParametersFault inconsistentParameters;

    public InconsistentParameters() {
        super();
    }
    
    public InconsistentParameters(String message) {
        super(message);
    }
    
    public InconsistentParameters(String message, Throwable cause) {
        super(message, cause);
    }

    public InconsistentParameters(String message, oasis.names.tc.wsrp.v1.types.InconsistentParametersFault inconsistentParameters) {
        super(message);
        this.inconsistentParameters = inconsistentParameters;
    }

    public InconsistentParameters(String message, oasis.names.tc.wsrp.v1.types.InconsistentParametersFault inconsistentParameters, Throwable cause) {
        super(message, cause);
        this.inconsistentParameters = inconsistentParameters;
    }

    public oasis.names.tc.wsrp.v1.types.InconsistentParametersFault getFaultInfo() {
        return this.inconsistentParameters;
    }
}
