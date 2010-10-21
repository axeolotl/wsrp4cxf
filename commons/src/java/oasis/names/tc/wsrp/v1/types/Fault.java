
package oasis.names.tc.wsrp.v1.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Fault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Fault">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fault")
@XmlSeeAlso({
    InconsistentParametersFault.class,
    MissingParametersFault.class,
    UnsupportedLocaleFault.class,
    UnsupportedModeFault.class,
    InvalidRegistrationFault.class,
    AccessDeniedFault.class,
    InvalidSessionFault.class,
    InvalidCookieFault.class,
    PortletStateChangeRequiredFault.class,
    OperationFailedFault.class,
    UnsupportedMimeTypeFault.class,
    InvalidUserCategoryFault.class,
    InvalidHandleFault.class,
    UnsupportedWindowStateFault.class
})
public class Fault {


}
