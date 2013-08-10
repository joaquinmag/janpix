package com.janpix.rup.infrastructure.dto


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.rup.infrastructure.Mapper


@XmlRootElement
class PersonDTO {
	@XmlElement(required=true)
	PersonNameDTO name
	
	@XmlElement
	ExtendedDateDTO birthdate
	
	@XmlElement
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
	
	List<AddressDTO> address
	List<String> phoneNumbers
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
