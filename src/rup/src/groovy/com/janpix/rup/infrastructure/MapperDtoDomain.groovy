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
 * Convierte DTOs en clases de Dominio
 *
 */
class MapperDtoDomain extends Mapper {
	def placeService
	def assigningAuthorityService
	
	/** Person **/
	public Person convert(PersonDTO dto){
		Person person = new Person();
		setPropertiesPerson(person,dto)
		return person
	}
	
	public Patient convert (PatientDTO dto){
		Patient patient = new Patient();
		patient.uniqueId = new PatientIdentifier(mainId:dto.uniqueId);
		setPropertiesPerson(patient,dto)
		return patient
	}

	public PersonName convert(PersonNameDTO dto){
		PersonName personName = new PersonName()
		personName.firstName = dto.firstName
		personName.lastName = dto.lastName
		
		return personName
	}
	
	public ExtendedDate convert(ExtendedDateDTO dto){
		ExtendedDate extendedDate = new ExtendedDate()
		extendedDate.date = Date.parse( "yyyy-M-d", dto.date ) //FIXME! Revisar si el formato es correcto 
		switch(dto.precission){
			case "Unknown" : 
				extendedDate.precission = ExtendedDate.TYPE_PRECISSION_UNKNOWN 
				break
			case "Year" :
				extendedDate.precission = ExtendedDate.TYPE_PRECISSION_YEAR
				break
			case "Month" :
				extendedDate.precission = ExtendedDate.TYPE_PRECISSION_MONTH
				break
			case "Day" :
				extendedDate.precission = ExtendedDate.TYPE_PRECISSION_DAY
				break
		}
		return extendedDate;
	}
	
	public City convert(CityDTO dto){
		return placeService.findByPlace(dto.nameCity,dto.nameProvince,dto.nameCountry)
	}
	
	public Address convert(AddressDTO dto){
		Address address = new Address();
		address.unitId = dto.unitId
		address.street = dto.street
		address.number = dto.number
		address.floor = dto.floor
		address.department = dto.department
		address.zipCode = dto.zipCode
		address.city = this.convert(dto.city)
		return address
	}
	
	public Identifier convert(IdentifierDTO dto){
		Identifier identifier = new Identifier(
				type:dto.type,
				number:dto.number,
				assigningAuthority:this.convert(dto.assigningAuthority)
			);
		return identifier
	}
	
	
	public AssigningAuthority convert(AssigningAuthorityDTO dto){
		AssigningAuthority entity = assigningAuthorityService.findAssigningAuthorityByOid(dto.oid)
		if(entity == null)
			entity = new AssigningAuthority(dto.oid,dto.name) 
		
		return entity 
	}
	
	/** Privados **/
	private void setPropertiesPerson(Person person,dto){
		person.givenName = dto.name?.convert(this)
		person.birthdate = dto.birthdate?.convert(this)
		person.administrativeSex = dto.administrativeSex
		person.maritalStatus = dto.maritalStatus
		person.birthplace = dto.birthplace?.convert(this)
		person.multipleBirthIndicator = dto.multipleBirthIndicator
		person.organDonorIndicator = dto.organDonorIndicator
		person.deathdate = dto.deathdate?.convert(this)
		
		dto.address.each { AddressDTO it ->
			person.addresses.add(it.convert(this))
		}
		
		dto.identifiers.each { IdentifierDTO it->
			person.identifiers.add(it.convert(this))
		}
		if(dto.phoneNumbers!=null)
			person.phoneNumbers.addAll(dto.phoneNumbers);
		
	}
}
