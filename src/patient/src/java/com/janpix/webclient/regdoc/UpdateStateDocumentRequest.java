
package com.janpix.webclient.regdoc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateStateDocumentRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateStateDocumentRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authority" type="{jpx:ar.com.janpix}healthEntityDTO"/>
 *         &lt;element name="documentUniqueId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stateDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateStateDocumentRequest", propOrder = {
    "authority",
    "documentUniqueId",
    "stateDescription"
})
public class UpdateStateDocumentRequest {

    @XmlElement(required = true)
    protected HealthEntityDTO authority;
    @XmlElement(required = true)
    protected String documentUniqueId;
    @XmlElement(required = true)
    protected String stateDescription;

    /**
     * Gets the value of the authority property.
     * 
     * @return
     *     possible object is
     *     {@link HealthEntityDTO }
     *     
     */
    public HealthEntityDTO getAuthority() {
        return authority;
    }

    /**
     * Sets the value of the authority property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthEntityDTO }
     *     
     */
    public void setAuthority(HealthEntityDTO value) {
        this.authority = value;
    }

    /**
     * Gets the value of the documentUniqueId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentUniqueId() {
        return documentUniqueId;
    }

    /**
     * Sets the value of the documentUniqueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentUniqueId(String value) {
        this.documentUniqueId = value;
    }

    /**
     * Gets the value of the stateDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateDescription() {
        return stateDescription;
    }

    /**
     * Sets the value of the stateDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateDescription(String value) {
        this.stateDescription = value;
    }

}
