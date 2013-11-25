package ar.com.healthentity.janpix

import grails.test.mixin.*
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import ar.com.healthentity.City
import ar.com.healthentity.Patient
import ar.com.healthentity.Province
import ar.com.healthentity.SexType
import ar.com.healthentity.janpix.utils.JanpixAssembler;

import com.janpix.webclient.rup.AddressDTO
import com.janpix.webclient.rup.AssigningAuthorityDTO
import com.janpix.webclient.rup.CityDTO
import com.janpix.webclient.rup.ExtendedDateDTO
import com.janpix.webclient.rup.IdentifierDTO
import com.janpix.webclient.rup.PatientDTO
import com.janpix.webclient.rup.PersonDTO
import com.janpix.webclient.rup.PersonNameDTO
import com.janpix.webclient.rup.PhoneNumberDTO
import com.janpix.webclient.rup.PersonDTO.Addresses
import com.janpix.webclient.rup.PersonDTO.Identifiers
import com.janpix.webclient.rup.PersonDTO.PhoneNumbers



/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
class JanpixAssemblerTestCaseSpec extends Specification {
	
    def setup() {
    }

    def cleanup() {
    }

    void "test convert Patient to PersonDTO"() {
		// Se crea un paciente
		given:
			Patient patient = new Patient();
			patient.firstName = "UnNombre"
			patient.lastName = "UnApellido"
			patient.dni = "40100200"
			patient.addressName = "UnaCalleMeSepara"
			patient.addressNumber = "574"
			
			City city = new City()
			city.name = "UnaCiudad"
			city.province = new Province()
			city.province.name = "UnaProvincia"
			city.province.country = "Argentina"
			patient.city = city
			
			patient.sex = SexType.Masculino
			patient.birthdate = Date.parse("yyyy-M-d","1990-05-16")
			patient.phone = "11-12131415"
			
		when:
			PersonDTO person = JanpixAssembler.toPerson(patient)
			
		then:
			person.name.firstName 							== patient.firstName
			person.name.lastName 							== patient.lastName
			person.birthdate.precission						== "Day"
			person.birthdate.date							== patient.birthdate.toString()
			person.administrativeSex						== "M"
			person.birthplace.nameCity						== patient.city.name
			person.birthplace.nameProvince					== patient.city.province.name
			person.birthplace.nameCountry					== patient.city.province.country
			person.addresses.address[0].street				== patient.addressName
			person.addresses.address[0].number				== patient.addressNumber
			person.addresses.address[0].city.nameCity		== patient.city.name
			person.identifiers.identifier[0].type			== "DNI"
			person.identifiers.identifier[0].number			== patient.dni
			person.identifiers.identifier[0].assigningAuthority.name== "Argentina"
			person.identifiers.identifier[0].assigningAuthority.oid	== "2.16.32"
			person.phoneNumbers.phoneNumber[0].number		== patient.phone
    }
	
	void "test convert Config Health Entity to HealthEntityDTO"() {
		given:
			ConfigObject parameters = new ConfigObject()
			parameters.healthEntity.name = "Argentina"
			parameters.healthEntity.oid = "2.16.32"
		when:
			AssigningAuthorityDTO healthEntity = JanpixAssembler.toAssigningAuthority(parameters.healthEntity)
		then:
			healthEntity.name 	== parameters.healthEntity.name
			healthEntity.oid 	== parameters.healthEntity.oid
			
	}
	
	void "test convert PatientDTO to Patient"() {
		given:
			PatientDTO patientJanpix = new PatientDTO()
			patientJanpix.name = new PersonNameDTO()
			patientJanpix.name.firstName = "UnPrimerNombre"
			patientJanpix.name.lastName = "UnApellido"
			patientJanpix.identifiers = new Identifiers()
			patientJanpix.identifiers.identifier.add(new IdentifierDTO())
			patientJanpix.identifiers.identifier[0].type = "DNI"
			patientJanpix.identifiers.identifier[0].number = "40020010"
			patientJanpix.addresses = new Addresses()
			patientJanpix.addresses.address.add(new AddressDTO())
			patientJanpix.addresses.address[0].street = "UnaCalle"
			patientJanpix.addresses.address[0].number = "MeSepara"
			patientJanpix.addresses.address[0].city = new CityDTO()
			patientJanpix.addresses.address[0].city.nameCity = "Luján"
			patientJanpix.addresses.address[0].city.nameProvince = "Buenos Aires"
			patientJanpix.addresses.address[0].city.nameCountry = "Argentina"
			patientJanpix.administrativeSex = "M"
			patientJanpix.birthdate = new ExtendedDateDTO()
			patientJanpix.birthdate.date = "2013-10-30"
			patientJanpix.birthdate.precission = "Day"
			patientJanpix.phoneNumbers = new PhoneNumbers()
			patientJanpix.phoneNumbers.phoneNumber.add(new PhoneNumberDTO())
			patientJanpix.phoneNumbers.phoneNumber[0].number = "1112131415"
			
		when:
			Patient patient = JanpixAssembler.fromPatient(patientJanpix)
			
		then:
			patient.firstName 		== patientJanpix.name.firstName
			patient.lastName		== patientJanpix.name.lastName
			patient.dni				== patientJanpix.identifiers.identifier[0].number
			patient.addressName		== patientJanpix.addresses.address[0].street
			patient.addressNumber	== patientJanpix.addresses.address[0].number
			patient.city.name		== patientJanpix.addresses.address[0].city.nameCity
			patient.city.province.name	== patientJanpix.addresses.address[0].city.nameProvince
			patient.city.province.country	== patientJanpix.addresses.address[0].city.nameCountry
			patient.sex				== SexType.Masculino
			patient.birthdate		== Date.parse("yyyy-M-d",patientJanpix.birthdate.date)
			patient.email			== null
			patient.phone			== patientJanpix.phoneNumbers.phoneNumber[0].number
	}
}
