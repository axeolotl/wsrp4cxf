
package oasis.names.tc.wsrp.v1.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="registrationContext" type="{urn:oasis:names:tc:wsrp:v1:types}RegistrationContext"/>
 *         &lt;element name="registrationData" type="{urn:oasis:names:tc:wsrp:v1:types}RegistrationData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "registrationContext",
    "registrationData"
})
@XmlRootElement(name = "modifyRegistration")
public class ModifyRegistration {

    @XmlElement(required = true, nillable = true)
    protected RegistrationContext registrationContext;
    @XmlElement(required = true)
    protected RegistrationData registrationData;

    /**
     * Gets the value of the registrationContext property.
     * 
     * @return
     *     possible object is
     *     {@link RegistrationContext }
     *     
     */
    public RegistrationContext getRegistrationContext() {
        return registrationContext;
    }

    /**
     * Sets the value of the registrationContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationContext }
     *     
     */
    public void setRegistrationContext(RegistrationContext value) {
        this.registrationContext = value;
    }

    /**
     * Gets the value of the registrationData property.
     * 
     * @return
     *     possible object is
     *     {@link RegistrationData }
     *     
     */
    public RegistrationData getRegistrationData() {
        return registrationData;
    }

    /**
     * Sets the value of the registrationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationData }
     *     
     */
    public void setRegistrationData(RegistrationData value) {
        this.registrationData = value;
    }

}
