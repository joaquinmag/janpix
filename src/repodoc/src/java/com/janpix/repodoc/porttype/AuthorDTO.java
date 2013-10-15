
package com.janpix.repodoc.porttype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authorDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="authorDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="healthEntity" type="{jpx:ar.com.janpix}healthEntityDTO"/>
 *         &lt;element name="authorPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorSpecialty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authorDTO", propOrder = {
    "healthEntity",
    "authorPerson",
    "authorRole",
    "authorSpecialty"
})
public class AuthorDTO {

    @XmlElement(required = true)
    protected HealthEntityDTO healthEntity;
    protected String authorPerson;
    protected String authorRole;
    protected String authorSpecialty;

    /**
     * Gets the value of the healthEntity property.
     * 
     * @return
     *     possible object is
     *     {@link HealthEntityDTO }
     *     
     */
    public HealthEntityDTO getHealthEntity() {
        return healthEntity;
    }

    /**
     * Sets the value of the healthEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthEntityDTO }
     *     
     */
    public void setHealthEntity(HealthEntityDTO value) {
        this.healthEntity = value;
    }

    /**
     * Gets the value of the authorPerson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorPerson() {
        return authorPerson;
    }

    /**
     * Sets the value of the authorPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorPerson(String value) {
        this.authorPerson = value;
    }

    /**
     * Gets the value of the authorRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorRole() {
        return authorRole;
    }

    /**
     * Sets the value of the authorRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorRole(String value) {
        this.authorRole = value;
    }

    /**
     * Gets the value of the authorSpecialty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorSpecialty() {
        return authorSpecialty;
    }

    /**
     * Sets the value of the authorSpecialty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorSpecialty(String value) {
        this.authorSpecialty = value;
    }

}
