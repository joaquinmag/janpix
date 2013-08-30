package com.janpix.rup.services.mappings;



import com.janpix.rup.empi.Address
import com.janpix.rup.empi.ExtendedDate
import com.janpix.rup.empi.Identifier
import com.janpix.rup.empi.Person
import com.janpix.rup.empi.PersonName
import com.janpix.rup.empi.City
import com.janpix.rup.empi.PhoneNumber;
import com.janpix.rup.infrastructure.MapperDtoDomain
import com.janpix.rup.infrastructure.dto.AddressDTO
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.CityDTO
import com.janpix.rup.infrastructure.dto.ExtendedDateDTO
import com.janpix.rup.infrastructure.dto.IdentifierDTO
import com.janpix.rup.infrastructure.dto.PatientDTO
import com.janpix.rup.infrastructure.dto.PersonNameDTO
import com.janpix.rup.infrastructure.dto.PhoneNumberDTO


class MapperDtoDomainTest extends GroovyTestCase {
	//def placeService
	def assigningAuthorityService
	def mapperDtoDomain
	
	/**
	 * Testea el correcto mapeo de un DTO a una clase de Dominio
	 */
	public void testMapPersonDtoToPersonDomain(){
		CityDTO city = new CityDTO(nameCity:"Venado Tuerto",nameProvince:"AR-S",nameCountry:"AR");
		PatientDTO dto = new PatientDTO(
				name:new PersonNameDTO(firstName:"Joaquin",lastName:"Magneres"),
				birthdate:new ExtendedDateDTO(date:"1986-05-01",precission:"Day"),
				administrativeSex:Person.TYPE_SEX_MALE,
				maritalStatus:Person.TYPE_MARITALSTATUS_SINGLE,
				birthplace:city
			);
		dto.address = []
		dto.address.add(new AddressDTO(
				street:"Zapata",
				number:"345",
				floor:"5",
				department:"A",
				zipCode:"1267",
				city:city
			));
		dto.identifiers = []
		dto.identifiers.add(new IdentifierDTO(
				type:Identifier.TYPE_IDENTIFIER_PI,
				number:"123",
				assigningAuthority:new AssigningAuthorityDTO(oid:"2.16.32.1.256.0", name:"RUP")
			));
		
		dto.phoneNumbers = []
		dto.phoneNumbers.add(new PhoneNumberDTO(number:"2323-421646", type: PhoneNumber.TYPE_HOME))
		
		//MapperDtoDomain mapper = new MapperDtoDomain();
		//mapper.placeService = placeService
		
		
		Person person = dto.convert(mapperDtoDomain);
		
		assert person.givenName == new PersonName(firstName:"Joaquin",lastName:"Magneres");
		assert person.birthdate == new ExtendedDate(date:Date.parse("yyyy-M-d","1986-05-01"),precission:ExtendedDate.TYPE_PRECISSION_DAY)
		assert person.administrativeSex == Person.TYPE_SEX_MALE
		assert person.maritalStatus == Person.TYPE_MARITALSTATUS_SINGLE
		assert person.birthplace == City.findByName("Venado Tuerto");
		assert person.addresses[0] == new Address(type:Address.TYPE_CIVIL,street:"Zapata",number:"345",
													floor:"5",department:"A",zipCode:"1267",city:City.findByName("Venado Tuerto"))
		assert person.identifiers.find{it->it.number == "123"} == new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"123",
																		assigningAuthority:assigningAuthorityService.rupAuthority())
		assert person.phoneNumbers[0].number == "2323-421646"
		
		assert person.principalAddress().type == Address.TYPE_CIVIL
	}
	
	public void testFailConvertDateWithIncorrectFormat(){
		
	}
	

}
