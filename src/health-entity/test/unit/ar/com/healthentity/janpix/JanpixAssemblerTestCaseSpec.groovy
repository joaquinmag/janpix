package ar.com.healthentity.janpix

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import ar.com.healthentity.City
import ar.com.healthentity.Patient
import ar.com.healthentity.Province
import ar.com.healthentity.SexType

import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.PersonDTO

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
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
			person.birthdate.precission						== "DAY"
			person.birthdate.date							== patient.birthdate.toString()
			person.administrativeSex						== "M"
			person.birthplace.nameCity						== patient.city.name
			person.birthplace.nameProvince					== patient.city.province.name
			person.birthplace.nameCountry					== patient.city.province.country
			person.address[0].street						== patient.addressName
			person.address[0].number						== patient.addressNumber
			person.address[0].city.nameCity					== patient.city.name
			person.identifiers[0].type						== "DNI"
			person.identifiers[0].number					== patient.dni
			person.identifiers[0].assigningAuthority.name	== "Argentina"
			person.identifiers[0].assigningAuthority.oid	== "2.16.32"
			person.phoneNumbers[0].number					== patient.phone
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
}
