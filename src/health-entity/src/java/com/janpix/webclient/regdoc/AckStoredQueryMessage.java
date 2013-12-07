
package com.janpix.webclient.regdoc;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ackStoredQueryMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ackStoredQueryMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clinicalDocuments" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="clinicalDocument" type="{jpx:ar.com.janpix}clinicalDocumentDTO" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ackStoredQueryMessage", propOrder = {
    "clinicalDocuments"
})
public class AckStoredQueryMessage {

    protected AckStoredQueryMessage.ClinicalDocuments clinicalDocuments;

    /**
     * Gets the value of the clinicalDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link AckStoredQueryMessage.ClinicalDocuments }
     *     
     */
    public AckStoredQueryMessage.ClinicalDocuments getClinicalDocuments() {
        return clinicalDocuments;
    }

    /**
     * Sets the value of the clinicalDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link AckStoredQueryMessage.ClinicalDocuments }
     *     
     */
    public void setClinicalDocuments(AckStoredQueryMessage.ClinicalDocuments value) {
        this.clinicalDocuments = value;
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
     *         &lt;element name="clinicalDocument" type="{jpx:ar.com.janpix}clinicalDocumentDTO" maxOccurs="unbounded" minOccurs="0"/>
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
        "clinicalDocument"
    })
    public static class ClinicalDocuments {

        protected List<ClinicalDocumentDTO> clinicalDocument;

        /**
         * Gets the value of the clinicalDocument property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the clinicalDocument property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getClinicalDocument().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClinicalDocumentDTO }
         * 
         * 
         */
        public List<ClinicalDocumentDTO> getClinicalDocument() {
            if (clinicalDocument == null) {
                clinicalDocument = new ArrayList<ClinicalDocumentDTO>();
            }
            return this.clinicalDocument;
        }

    }

}
