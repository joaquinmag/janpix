
package com.janpix.webclient.rup;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getIdentifiersRequestMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getIdentifiersRequestMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="assigningAuthority" type="{jpx:ar.com.janpix}assigningAuthorityDTO"/>
 *         &lt;element name="othersDomain" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="domain" type="{jpx:ar.com.janpix}assigningAuthorityDTO" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIdentifiersRequestMessage", propOrder = {
    "patientIdentifier",
    "assigningAuthority",
    "othersDomain"
})
public class GetIdentifiersRequestMessage {

    @XmlElement(required = true)
    protected String patientIdentifier;
    @XmlElement(required = true)
    protected AssigningAuthorityDTO assigningAuthority;
    protected GetIdentifiersRequestMessage.OthersDomain othersDomain;

    /**
     * Gets the value of the patientIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    /**
     * Sets the value of the patientIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientIdentifier(String value) {
        this.patientIdentifier = value;
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

    /**
     * Gets the value of the othersDomain property.
     * 
     * @return
     *     possible object is
     *     {@link GetIdentifiersRequestMessage.OthersDomain }
     *     
     */
    public GetIdentifiersRequestMessage.OthersDomain getOthersDomain() {
        return othersDomain;
    }

    /**
     * Sets the value of the othersDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetIdentifiersRequestMessage.OthersDomain }
     *     
     */
    public void setOthersDomain(GetIdentifiersRequestMessage.OthersDomain value) {
        this.othersDomain = value;
    }


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
     *         &lt;element name="domain" type="{jpx:ar.com.janpix}assigningAuthorityDTO" maxOccurs="unbounded" minOccurs="0"/>
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
        "domain"
    })
    public static class OthersDomain {

        protected List<AssigningAuthorityDTO> domain;

        /**
         * Gets the value of the domain property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the domain property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDomain().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AssigningAuthorityDTO }
         * 
         * 
         */
        public List<AssigningAuthorityDTO> getDomain() {
            if (domain == null) {
                domain = new ArrayList<AssigningAuthorityDTO>();
            }
            return this.domain;
        }

    }

}
