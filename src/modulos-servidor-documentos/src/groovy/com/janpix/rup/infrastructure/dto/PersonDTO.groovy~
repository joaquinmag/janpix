package com.janpix.rup.infrastructure.dto


import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

import org.springframework.beans.factory.annotation.Required;

import com.janpix.rup.infrastructure.Mapper


@XmlRootElement
class PersonDTO {
	@XmlElement(required=true)
	PersonNameDTO name
	
	@XmlElement(required=true)
	ExtendedDateDTO birthdate
	
	@XmlElement(required=true)
	String administrativeSex
	
	@XmlElement
	String maritalStatus
	
	@XmlElement
	CityDTO birthplace
	
	@XmlElement
	Boolean multipleBirthIndicator
	
	@XmlElement
	Boolean organDonorIndicator
	
	@XmlElement
	ExtendedDateDTO deathdate
	
	@XmlElementWrapper(name = "addresses")
	@XmlElement(name = "address",required=true)
	List<AddressDTO> address
	

	@XmlElementWrapper(name = "phoneNumbers")
	@XmlElement(name = "phoneNumber")
	List<PhoneNumberDTO> phoneNumbers
	
	@XmlElementWrapper(name = "identifiers")
	@XmlElement(name = "identifier")
	Set<IdentifierDTO> identifiers
	
	PersonDTO(){
		address = []
		phoneNumbers = []
		identifiers = []
	}
		
	def convert(Mapper mapper){
		mapper.convert(this);
	}

}
