
package com.janpix.webclient.regdoc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registerDocumentRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registerDocumentRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clinicalDocument" type="{jpx:ar.com.janpix}clinicalDocumentDTO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerDocumentRequest", propOrder = {
    "clinicalDocument"
})
public class RegisterDocumentRequest {

    @XmlElement(required = true)
    protected ClinicalDocumentDTO clinicalDocument;

    /**
     * Gets the value of the clinicalDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ClinicalDocumentDTO }
     *     
     */
    public ClinicalDocumentDTO getClinicalDocument() {
        return clinicalDocument;
    }

    /**
     * Sets the value of the clinicalDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClinicalDocumentDTO }
     *     
     */
    public void setClinicalDocument(ClinicalDocumentDTO value) {
        this.clinicalDocument = value;
    }

}
