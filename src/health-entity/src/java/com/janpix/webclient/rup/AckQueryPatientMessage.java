
package com.janpix.webclient.rup;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ackQueryPatientMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ackQueryPatientMessage">
 *   &lt;complexContent>
 *     &lt;extension base="{jpx:ar.com.janpix}ackMessage">
 *       &lt;sequence>
 *         &lt;element name="patients" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="patient" type="{jpx:ar.com.janpix}patientDTO" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ackQueryPatientMessage", propOrder = {
    "patients"
})
public class AckQueryPatientMessage
    extends AckMessage
{

    protected AckQueryPatientMessage.Patients patients;

    /**
     * Gets the value of the patients property.
     * 
     * @return
     *     possible object is
     *     {@link AckQueryPatientMessage.Patients }
     *     
     */
    public AckQueryPatientMessage.Patients getPatients() {
        return patients;
    }

    /**
     * Sets the value of the patients property.
     * 
     * @param value
     *     allowed object is
     *     {@link AckQueryPatientMessage.Patients }
     *     
     */
    public void setPatients(AckQueryPatientMessage.Patients value) {
        this.patients = value;
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
     *         &lt;element name="patient" type="{jpx:ar.com.janpix}patientDTO" maxOccurs="unbounded" minOccurs="0"/>
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
        "patient"
    })
    public static class Patients {

        protected List<PatientDTO> patient;

        /**
         * Gets the value of the patient property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the patient property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPatient().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PatientDTO }
         * 
         * 
         */
        public List<PatientDTO> getPatient() {
            if (patient == null) {
                patient = new ArrayList<PatientDTO>();
            }
            return this.patient;
        }

    }

}
