
package com.janpix.webclient.rup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cityDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cityDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nameCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nameProvince" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nameCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cityDTO", propOrder = {
    "nameCity",
    "nameProvince",
    "nameCountry"
})
public class CityDTO {

    @XmlElement(required = true)
    protected String nameCity;
    @XmlElement(required = true)
    protected String nameProvince;
    protected String nameCountry;

    /**
     * Gets the value of the nameCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameCity() {
        return nameCity;
    }

    /**
     * Sets the value of the nameCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameCity(String value) {
        this.nameCity = value;
    }

    /**
     * Gets the value of the nameProvince property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameProvince() {
        return nameProvince;
    }

    /**
     * Sets the value of the nameProvince property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameProvince(String value) {
        this.nameProvince = value;
    }

    /**
     * Gets the value of the nameCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameCountry() {
        return nameCountry;
    }

    /**
     * Sets the value of the nameCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameCountry(String value) {
        this.nameCountry = value;
    }

}
