
package com.janpix.webclient.regdoc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryDocumentRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryDocumentRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="healthEntityFinder" type="{jpx:ar.com.janpix}healthEntityDTO"/>
 *         &lt;element name="titleCriteria" type="{jpx:ar.com.janpix}titleCriteriaDTO" minOccurs="0"/>
 *         &lt;element name="dateCreationCriteria" type="{jpx:ar.com.janpix}dateCreationCriteriaDTO" minOccurs="0"/>
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryDocumentRequest", propOrder = {
    "healthEntityFinder",
    "titleCriteria",
    "dateCreationCriteria",
    "patientId"
})
public class QueryDocumentRequest {

    @XmlElement(required = true)
    protected HealthEntityDTO healthEntityFinder;
    protected TitleCriteriaDTO titleCriteria;
    protected DateCreationCriteriaDTO dateCreationCriteria;
    protected String patientId;

    /**
     * Gets the value of the healthEntityFinder property.
     * 
     * @return
     *     possible object is
     *     {@link HealthEntityDTO }
     *     
     */
    public HealthEntityDTO getHealthEntityFinder() {
        return healthEntityFinder;
    }

    /**
     * Sets the value of the healthEntityFinder property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthEntityDTO }
     *     
     */
    public void setHealthEntityFinder(HealthEntityDTO value) {
        this.healthEntityFinder = value;
    }

    /**
     * Gets the value of the titleCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link TitleCriteriaDTO }
     *     
     */
    public TitleCriteriaDTO getTitleCriteria() {
        return titleCriteria;
    }

    /**
     * Sets the value of the titleCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link TitleCriteriaDTO }
     *     
     */
    public void setTitleCriteria(TitleCriteriaDTO value) {
        this.titleCriteria = value;
    }

    /**
     * Gets the value of the dateCreationCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link DateCreationCriteriaDTO }
     *     
     */
    public DateCreationCriteriaDTO getDateCreationCriteria() {
        return dateCreationCriteria;
    }

    /**
     * Sets the value of the dateCreationCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateCreationCriteriaDTO }
     *     
     */
    public void setDateCreationCriteria(DateCreationCriteriaDTO value) {
        this.dateCreationCriteria = value;
    }

    /**
     * Gets the value of the patientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the value of the patientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientId(String value) {
        this.patientId = value;
    }

}
