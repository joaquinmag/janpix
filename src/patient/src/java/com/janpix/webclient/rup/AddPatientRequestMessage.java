
package com.janpix.webclient.rup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addPatientRequestMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addPatientRequestMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="person" type="{jpx:ar.com.janpix}personDTO"/>
 *         &lt;element name="healthEntity" type="{jpx:ar.com.janpix}assigningAuthorityDTO"/>
 *         &lt;element name="organizationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addPatientRequestMessage", propOrder = {
    "person",
    "healthEntity",
    "organizationId"
})
public class AddPatientRequestMessage {

    @XmlElement(required = true)
    protected PersonDTO person;
    @XmlElement(required = true)
    protected AssigningAuthorityDTO healthEntity;
    @XmlElement(required = true)
    protected String organizationId;

    /**
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link PersonDTO }
     *     
     */
    public PersonDTO getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonDTO }
     *     
     */
    public void setPerson(PersonDTO value) {
        this.person = value;
    }

    /**
     * Gets the value of the healthEntity property.
     * 
     * @return
     *     possible object is
     *     {@link AssigningAuthorityDTO }
     *     
     */
    public AssigningAuthorityDTO getHealthEntity() {
        return healthEntity;
    }

    /**
     * Sets the value of the healthEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssigningAuthorityDTO }
     *     
     */
    public void setHealthEntity(AssigningAuthorityDTO value) {
        this.healthEntity = value;
    }

    /**
     * Gets the value of the organizationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Sets the value of the organizationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationId(String value) {
        this.organizationId = value;
    }

}
