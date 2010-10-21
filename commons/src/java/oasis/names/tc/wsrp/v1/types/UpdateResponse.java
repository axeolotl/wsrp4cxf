
package oasis.names.tc.wsrp.v1.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionContext" type="{urn:oasis:names:tc:wsrp:v1:types}SessionContext" minOccurs="0"/>
 *         &lt;element name="portletContext" type="{urn:oasis:names:tc:wsrp:v1:types}PortletContext" minOccurs="0"/>
 *         &lt;element name="markupContext" type="{urn:oasis:names:tc:wsrp:v1:types}MarkupContext" minOccurs="0"/>
 *         &lt;element name="navigationalState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newWindowState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="newMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateResponse", propOrder = {
    "sessionContext",
    "portletContext",
    "markupContext",
    "navigationalState",
    "newWindowState",
    "newMode"
})
public class UpdateResponse {

    protected SessionContext sessionContext;
    protected PortletContext portletContext;
    protected MarkupContext markupContext;
    @XmlElement(required = true, nillable = true)
    protected String navigationalState;
    protected String newWindowState;
    protected String newMode;

    /**
     * Gets the value of the sessionContext property.
     * 
     * @return
     *     possible object is
     *     {@link SessionContext }
     *     
     */
    public SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * Sets the value of the sessionContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionContext }
     *     
     */
    public void setSessionContext(SessionContext value) {
        this.sessionContext = value;
    }

    /**
     * Gets the value of the portletContext property.
     * 
     * @return
     *     possible object is
     *     {@link PortletContext }
     *     
     */
    public PortletContext getPortletContext() {
        return portletContext;
    }

    /**
     * Sets the value of the portletContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link PortletContext }
     *     
     */
    public void setPortletContext(PortletContext value) {
        this.portletContext = value;
    }

    /**
     * Gets the value of the markupContext property.
     * 
     * @return
     *     possible object is
     *     {@link MarkupContext }
     *     
     */
    public MarkupContext getMarkupContext() {
        return markupContext;
    }

    /**
     * Sets the value of the markupContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkupContext }
     *     
     */
    public void setMarkupContext(MarkupContext value) {
        this.markupContext = value;
    }

    /**
     * Gets the value of the navigationalState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNavigationalState() {
        return navigationalState;
    }

    /**
     * Sets the value of the navigationalState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNavigationalState(String value) {
        this.navigationalState = value;
    }

    /**
     * Gets the value of the newWindowState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewWindowState() {
        return newWindowState;
    }

    /**
     * Sets the value of the newWindowState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewWindowState(String value) {
        this.newWindowState = value;
    }

    /**
     * Gets the value of the newMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewMode() {
        return newMode;
    }

    /**
     * Sets the value of the newMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewMode(String value) {
        this.newMode = value;
    }

}
