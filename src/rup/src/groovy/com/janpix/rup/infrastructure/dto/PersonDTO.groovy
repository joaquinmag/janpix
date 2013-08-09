package com.janpix.rup.infrastructure.dto


import com.janpix.rup.infrastructure.Mapper

class PersonDTO {
	PersonNameDTO name
	ExtendedDateDTO birthdate
	String administrativeSex
	String maritalStatus
	CityDTO birthplace
	Boolean multipleBirthIndicator
	Boolean organDonorIndicator
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
