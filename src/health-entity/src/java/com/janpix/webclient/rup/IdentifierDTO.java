
package com.janpix.webclient.rup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for identifierDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="identifierDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="assigningAuthority" type="{jpx:ar.com.janpix}assigningAuthorityDTO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identifierDTO", propOrder = {
    "type",
    "number",
    "assigningAuthority"
})
public class IdentifierDTO {

    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String number;
    @XmlElement(required = true)
    protected AssigningAuthorityDTO assigningAuthority;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the assigningAuthority property.
     * 
     * @return
     *     possible object is
     *     {@link AssigningAuthorityDTO }
     *     
     */
    public AssigningAuthorityDTO getAssigningAuthority() {
        return assigningAuthority;
    }

    /**
     * Sets the value of the assigningAuthority property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssigningAuthorityDTO }
     *     
     */
    public void setAssigningAuthority(AssigningAuthorityDTO value) {
        this.assigningAuthority = value;
    }

}
