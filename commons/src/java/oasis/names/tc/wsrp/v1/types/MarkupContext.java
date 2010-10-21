
package oasis.names.tc.wsrp.v1.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MarkupContext complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MarkupContext">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="useCachedMarkup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="markupString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="markupBinary" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requiresUrlRewriting" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="cacheControl" type="{urn:oasis:names:tc:wsrp:v1:types}CacheControl" minOccurs="0"/>
 *         &lt;element name="preferredTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "MarkupContext", propOrder = {
    "useCachedMarkup",
    "mimeType",
    "markupString",
    "markupBinary",
    "locale",
    "requiresUrlRewriting",
    "cacheControl",
    "preferredTitle",
    "extensions"
})
public class MarkupContext {

    @XmlElement(defaultValue = "false")
    protected Boolean useCachedMarkup;
    protected String mimeType;
    protected String markupString;
    protected byte[] markupBinary;
    protected String locale;
    @XmlElement(defaultValue = "false")
    protected Boolean requiresUrlRewriting;
    protected CacheControl cacheControl;
    protected String preferredTitle;
    protected List<Extension> extensions;

    /**
     * Gets the value of the useCachedMarkup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseCachedMarkup() {
        return useCachedMarkup;
    }

    /**
     * Sets the value of the useCachedMarkup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseCachedMarkup(Boolean value) {
        this.useCachedMarkup = value;
    }

    /**
     * Gets the value of the mimeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the markupString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarkupString() {
        return markupString;
    }

    /**
     * Sets the value of the markupString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarkupString(String value) {
        this.markupString = value;
    }

    /**
     * Gets the value of the markupBinary property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getMarkupBinary() {
        return markupBinary;
    }

    /**
     * Sets the value of the markupBinary property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setMarkupBinary(byte[] value) {
        this.markupBinary = ((byte[]) value);
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the requiresUrlRewriting property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRequiresUrlRewriting() {
        return requiresUrlRewriting;
    }

    /**
     * Sets the value of the requiresUrlRewriting property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequiresUrlRewriting(Boolean value) {
        this.requiresUrlRewriting = value;
    }

    /**
     * Gets the value of the cacheControl property.
     * 
     * @return
     *     possible object is
     *     {@link CacheControl }
     *     
     */
    public CacheControl getCacheControl() {
        return cacheControl;
    }

    /**
     * Sets the value of the cacheControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link CacheControl }
     *     
     */
    public void setCacheControl(CacheControl value) {
        this.cacheControl = value;
    }

    /**
     * Gets the value of the preferredTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredTitle() {
        return preferredTitle;
    }

    /**
     * Sets the value of the preferredTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredTitle(String value) {
        this.preferredTitle = value;
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
