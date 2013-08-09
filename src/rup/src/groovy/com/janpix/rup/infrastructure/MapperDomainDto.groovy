package com.janpix.rup.infrastructure

import com.janpix.rup.empi.Address
import com.janpix.rup.empi.AssigningAuthority
import com.janpix.rup.empi.City
import com.janpix.rup.empi.ExtendedDate
import com.janpix.rup.empi.Identifier
import com.janpix.rup.empi.Patient
import com.janpix.rup.empi.PatientIdentifier
import com.janpix.rup.empi.Person
import com.janpix.rup.empi.PersonName
import com.janpix.rup.infrastructure.dto.AddressDTO
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.CityDTO
import com.janpix.rup.infrastructure.dto.ExtendedDateDTO
import com.janpix.rup.infrastructure.dto.IdentifierDTO
import com.janpix.rup.infrastructure.dto.PatientDTO
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.infrastructure.dto.PersonNameDTO


/**
 * Convierte Clases de Dominio en DTOs
 *
 */
class MapperDomainDto extends Mapper {
	
	/** Person **/
	public PersonDTO convert(Person domain){
		PersonDTO dto = new PersonDTO();
		setPropertiesPerson(dto,domain)
		return dto
	}
	
	public PatientDTO convert (Patient domain){
		PatientDTO dto = new PatientDTO();
		dto.uniqueId = domain.uniqueId?.mainId
		setPropertiesPerson(dto,domain)
		return dto
	}

	public PersonNameDTO convert(PersonName domain){
		PersonNameDTO dto = new PersonNameDTO()
		dto.firstName = domain.firstName
		dto.lastName = domain.lastName
		
		return dto
	}
	
	public ExtendedDateDTO convert(ExtendedDate domain){
		ExtendedDateDTO dto = new ExtendedDateDTO()
		dto.date = domain.date.format("yyyy-M-d") 
		switch(domain.precission){
			case ExtendedDate.TYPE_PRECISSION_UNKNOWN : 
				dto.precission =  "Unknown"
				break
			case ExtendedDate.TYPE_PRECISSION_YEAR :
				dto.precission = "Year"
				break
			case ExtendedDate.TYPE_PRECISSION_MONTH :
				dto.precission = "Month"
				break
			case ExtendedDate.TYPE_PRECISSION_DAY :
				dto.precission = "Day"
				break
		}
		return dto;
	}
	
	public CityDTO convert(City domain){
		CityDTO dto = new CityDTO();
		dto.nameCity = domain?.name
		dto.nameProvince = domain?.province?.name
		dto.nameCountry = domain?.province?.country?.name
		
		return dto
	}
	
	public AddressDTO convert(Address domain){
		AddressDTO dto = new AddressDTO()
		
		dto.unitId = domain.unitId
		dto.street = domain.street
		dto.number = domain.number
		dto.floor = domain.floor
		dto.department = domain.department
		dto.zipCode = domain.zipCode
		dto.city = domain.city?.convert(this)
		return dto
	}
	
	public IdentifierDTO convert(Identifier domain){
		IdentifierDTO dto = new IdentifierDTO();
		dto.type = domain.type
		dto.number = domain.number
		dto.assigningAuthority = domain.assigningAuthority.convert(this)

		return dto
	}
	
	
	public AssigningAuthorityDTO convert(AssigningAuthority domain){
		AssigningAuthorityDTO dto = new AssigningAuthorityDTO(domain.oid, domain.name)
		return dto;
	}
	
	/** Privados **/
	private void setPropertiesPerson(PersonDTO dto,Person domain){
		dto.name = domain.givenName?.convert(this)
		dto.birthdate = domain.birthdate?.convert(this)
		dto.administrativeSex = domain.administrativeSex
		dto.maritalStatus = domain.maritalStatus
		dto.birthplace = domain.birthplace?.convert(this)
		dto.multipleBirthIndicator = domain.multipleBirthIndicator
		dto.organDonorIndicator = domain.organDonorIndicator
		dto.deathdate = domain.deathdate?.convert(this)
		
		domain.addresses.each { Address it ->
			dto.address.add(it.convert(this))
		}
		
		domain.identifiers.each { Identifier it->
			dto.identifiers.add(it.convert(this))
		}
		if(dto.phoneNumbers!=null)
			dto.phoneNumbers.addAll(domain.phoneNumbers);
		
	}
}
