
package com.janpix.webclient.rup;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for personDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{jpx:ar.com.janpix}personNameDTO"/>
 *         &lt;element name="birthdate" type="{jpx:ar.com.janpix}extendedDateDTO"/>
 *         &lt;element name="administrativeSex" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maritalStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="birthplace" type="{jpx:ar.com.janpix}cityDTO" minOccurs="0"/>
 *         &lt;element name="multipleBirthIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="organDonorIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="deathdate" type="{jpx:ar.com.janpix}extendedDateDTO" minOccurs="0"/>
 *         &lt;element name="addresses" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="address" type="{jpx:ar.com.janpix}addressDTO" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="phoneNumbers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="phoneNumber" type="{jpx:ar.com.janpix}phoneNumberDTO" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="identifiers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="identifier" type="{jpx:ar.com.janpix}identifierDTO" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "personDTO", propOrder = {
    "name",
    "birthdate",
    "administrativeSex",
    "maritalStatus",
    "birthplace",
    "multipleBirthIndicator",
    "organDonorIndicator",
    "deathdate",
    "addresses",
    "phoneNumbers",
    "identifiers"
})
@XmlSeeAlso({
    PatientDTO.class
})
public class PersonDTO {

    @XmlElement(required = true)
    protected PersonNameDTO name;
    @XmlElement(required = true)
    protected ExtendedDateDTO birthdate;
    @XmlElement(required = true)
    protected String administrativeSex;
    protected String maritalStatus;
    protected CityDTO birthplace;
    protected Boolean multipleBirthIndicator;
    protected Boolean organDonorIndicator;
    protected ExtendedDateDTO deathdate;
    protected PersonDTO.Addresses addresses;
    protected PersonDTO.PhoneNumbers phoneNumbers;
    protected PersonDTO.Identifiers identifiers;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link PersonNameDTO }
     *     
     */
    public PersonNameDTO getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonNameDTO }
     *     
     */
    public void setName(PersonNameDTO value) {
        this.name = value;
    }

    /**
     * Gets the value of the birthdate property.
     * 
     * @return
     *     possible object is
     *     {@link ExtendedDateDTO }
     *     
     */
    public ExtendedDateDTO getBirthdate() {
        return birthdate;
    }

    /**
     * Sets the value of the birthdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtendedDateDTO }
     *     
     */
    public void setBirthdate(ExtendedDateDTO value) {
        this.birthdate = value;
    }

    /**
     * Gets the value of the administrativeSex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrativeSex() {
        return administrativeSex;
    }

    /**
     * Sets the value of the administrativeSex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrativeSex(String value) {
        this.administrativeSex = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the birthplace property.
     * 
     * @return
     *     possible object is
     *     {@link CityDTO }
     *     
     */
    public CityDTO getBirthplace() {
        return birthplace;
    }

    /**
     * Sets the value of the birthplace property.
     * 
     * @param value
     *     allowed object is
     *     {@link CityDTO }
     *     
     */
    public void setBirthplace(CityDTO value) {
        this.birthplace = value;
    }

    /**
     * Gets the value of the multipleBirthIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMultipleBirthIndicator() {
        return multipleBirthIndicator;
    }

    /**
     * Sets the value of the multipleBirthIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMultipleBirthIndicator(Boolean value) {
        this.multipleBirthIndicator = value;
    }

    /**
     * Gets the value of the organDonorIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOrganDonorIndicator() {
        return organDonorIndicator;
    }

    /**
     * Sets the value of the organDonorIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOrganDonorIndicator(Boolean value) {
        this.organDonorIndicator = value;
    }

    /**
     * Gets the value of the deathdate property.
     * 
     * @return
     *     possible object is
     *     {@link ExtendedDateDTO }
     *     
     */
    public ExtendedDateDTO getDeathdate() {
        return deathdate;
    }

    /**
     * Sets the value of the deathdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtendedDateDTO }
     *     
     */
    public void setDeathdate(ExtendedDateDTO value) {
        this.deathdate = value;
    }

    /**
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link PersonDTO.Addresses }
     *     
     */
    public PersonDTO.Addresses getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonDTO.Addresses }
     *     
     */
    public void setAddresses(PersonDTO.Addresses value) {
        this.addresses = value;
    }

    /**
     * Gets the value of the phoneNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link PersonDTO.PhoneNumbers }
     *     
     */
    public PersonDTO.PhoneNumbers getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Sets the value of the phoneNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonDTO.PhoneNumbers }
     *     
     */
    public void setPhoneNumbers(PersonDTO.PhoneNumbers value) {
        this.phoneNumbers = value;
    }

    /**
     * Gets the value of the identifiers property.
     * 
     * @return
     *     possible object is
     *     {@link PersonDTO.Identifiers }
     *     
     */
    public PersonDTO.Identifiers getIdentifiers() {
        return identifiers;
    }

    /**
     * Sets the value of the identifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonDTO.Identifiers }
     *     
     */
    public void setIdentifiers(PersonDTO.Identifiers value) {
        this.identifiers = value;
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
     *         &lt;element name="address" type="{jpx:ar.com.janpix}addressDTO" maxOccurs="unbounded"/>
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
        "address"
    })
    public static class Addresses {

        @XmlElement(required = true)
        protected List<AddressDTO> address;

        /**
         * Gets the value of the address property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the address property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAddress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AddressDTO }
         * 
         * 
         */
        public List<AddressDTO> getAddress() {
            if (address == null) {
                address = new ArrayList<AddressDTO>();
            }
            return this.address;
        }

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
     *         &lt;element name="identifier" type="{jpx:ar.com.janpix}identifierDTO" maxOccurs="unbounded" minOccurs="0"/>
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
        "identifier"
    })
    public static class Identifiers {

        protected List<IdentifierDTO> identifier;

        /**
         * Gets the value of the identifier property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the identifier property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIdentifier().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdentifierDTO }
         * 
         * 
         */
        public List<IdentifierDTO> getIdentifier() {
            if (identifier == null) {
                identifier = new ArrayList<IdentifierDTO>();
            }
            return this.identifier;
        }

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
     *         &lt;element name="phoneNumber" type="{jpx:ar.com.janpix}phoneNumberDTO" maxOccurs="unbounded" minOccurs="0"/>
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
        "phoneNumber"
    })
    public static class PhoneNumbers {

        protected List<PhoneNumberDTO> phoneNumber;

        /**
         * Gets the value of the phoneNumber property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the phoneNumber property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoneNumber().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PhoneNumberDTO }
         * 
         * 
         */
        public List<PhoneNumberDTO> getPhoneNumber() {
            if (phoneNumber == null) {
                phoneNumber = new ArrayList<PhoneNumberDTO>();
            }
            return this.phoneNumber;
        }

    }

}
