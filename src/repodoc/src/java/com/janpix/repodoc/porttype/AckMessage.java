
package com.janpix.repodoc.porttype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ackMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ackMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{jpx:ar.com.janpix}typeCode" minOccurs="0"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "ackMessage", propOrder = {
    "typeCode",
    "text",
    "clinicalDocument"
})
public class AckMessage {

    protected TypeCode typeCode;
    protected String text;
    @XmlElement(required = true)
    protected ClinicalDocumentDTO clinicalDocument;

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCode }
     *     
     */
    public TypeCode getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCode }
     *     
     */
    public void setTypeCode(TypeCode value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

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
