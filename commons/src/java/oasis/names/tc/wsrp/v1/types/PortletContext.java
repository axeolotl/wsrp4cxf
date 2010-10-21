
package oasis.names.tc.wsrp.v1.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PortletContext complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PortletContext">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="portletHandle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="portletState" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="extensions" type="{urn:oasis:names:tc:wsrp:v1:types}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortletContext", propOrder = {
    "portletHandle",
    "portletState",
    "extensions"
})
public class PortletContext {

    @XmlElement(required = true)
    protected String portletHandle;
    protected byte[] portletState;
    protected List<Extension> extensions;

    /**
     * Gets the value of the portletHandle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortletHandle() {
        return portletHandle;
    }

    /**
     * Sets the value of the portletHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortletHandle(String value) {
        this.portletHandle = value;
    }

    /**
     * Gets the value of the portletState property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPortletState() {
        return portletState;
    }

    /**
     * Sets the value of the portletState property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPortletState(byte[] value) {
        this.portletState = ((byte[]) value);
    }

    /**
     * Gets the value of the extensions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extensions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtensions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extension }
     * 
     * 
     */
    public List<Extension> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<Extension>();
        }
        return this.extensions;
    }

}
