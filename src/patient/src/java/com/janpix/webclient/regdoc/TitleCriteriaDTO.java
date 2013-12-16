
package com.janpix.webclient.regdoc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for titleCriteriaDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="titleCriteriaDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="valueLookup" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "titleCriteriaDTO", propOrder = {
    "valueLookup"
})
public class TitleCriteriaDTO {

    @XmlElement(required = true)
    protected String valueLookup;

    /**
     * Gets the value of the valueLookup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueLookup() {
        return valueLookup;
    }

    /**
     * Sets the value of the valueLookup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueLookup(String value) {
        this.valueLookup = value;
    }

}
