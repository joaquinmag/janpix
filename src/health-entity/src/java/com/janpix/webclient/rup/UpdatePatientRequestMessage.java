
package com.janpix.webclient.rup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updatePatientRequestMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updatePatientRequestMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patient" type="{jpx:ar.com.janpix}patientDTO"/>
 *         &lt;element name="healthEntity" type="{jpx:ar.com.janpix}assigningAuthorityDTO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updatePatientRequestMessage", propOrder = {
    "patient",
    "healthEntity"
})
public class UpdatePatientRequestMessage {

    @XmlElement(required = true)
    protected PatientDTO patient;
    @XmlElement(required = true)
    protected AssigningAuthorityDTO healthEntity;

    /**
     * Gets the value of the patient property.
     * 
     * @return
     *     possible object is
     *     {@link PatientDTO }
     *     
     */
    public PatientDTO getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientDTO }
     *     
     */
    public void setPatient(PatientDTO value) {
        this.patient = value;
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

}
