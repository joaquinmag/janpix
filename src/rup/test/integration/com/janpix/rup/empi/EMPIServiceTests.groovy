/**
 * Test de integracion que testea el servicio de EMPI
 * @author martin
 *
 */

package com.janpix.rup.empi
import groovy.util.GroovyTestCase;
 
class EMPIServiceTests extends GroovyTestCase {
	//static transactional = false
	
	def EMPIService
	def patient
	def healthEntity1
	def healthEntity2
	
	void setUp(){
		super.setUp()
		EMPIService = new EMPIService()
		
		//Creo 2 entidades sanitarias
		healthEntity1 = new HealthEntity(name:"Entidad Sanitaria 1")
		healthEntity1.save(flush:true,failOnError:true)
		healthEntity2 = new HealthEntity(name:"Entidad Sanitaria 2")
		healthEntity2.save(flush:true,failOnError:true)
		
		//Creo un nuevo paciente
		def citizenship = new Citizenship(name:"Argentino")
		citizenship.save(flush:true,failOnError:true)
		def city = new City(country:"Argentina",province:"Buenos Aires",name:"Luján")
		city.save(flush:true,failOnError:true)

		patient = new Patient(
					uniqueId:"UUID-00001",
					givenName: new Name(firstName:"Martín", lastName:"Barnech"),
					birthdate: Date.parse( "yyyy-M-d", "1983-01-01" ),
		//TODO Ver NO FUNCIONA			address: new Address(street:"Constitución",number:"2213",zipCode:"6700",town:"Luján"),
					administrativeSex:"M",
					citizenship:citizenship,
					livingplace:city,
					birthplace:city,
					motherName: new Name(firstName:"Maria Olga", lastName:"Mannino"),
					fatherName: new Name(firstName:"Pablo Juan", lastName:"Barnech"),
					)
	}

	/**
	 * Testea que se agregue correctamente un paciente
	 * que no existe en el eMPI
	 */
	void testAddNewPatientForAll() {
		def patientEntityId = "HC0012"
		def returnedPatient = EMPIService.addPatient(patient,healthEntity1,patientEntityId)
		
		//Solo debe existir un paciente guardado
		assertEquals(1,Patient.count())
		
		//Debe tener un solo identificador y debe ser el de mi entidad sanitaria
		def identifiers = returnedPatient.identifiers
		assertEquals(1,identifiers.size())
		identifiers.each {
			assertEquals("PI",it.type)
			assertEquals(patientEntityId,it.number)
			assertEquals(healthEntity1,it.assigningAuthority)
		}
	}
	
	
	/**
	 * Testea que se agregue el identificador de la nueva entidad
	 * al paciente que ya existe
	 * En este test se pasa exactamente el mismo paciente
	 */
	void testAddNewPatientForEntity(){
		fail "Implentar"
	}
	
	/**
	 * Testea que se agregue el identificador de la nueva entidad
	 * al paciente que ya existe en el eMPI
	 * Los datos pasados del nuevo paciente no son iguales al existente
	 * y el algoritmo de matcheo demografico debe darse cuenta
	 */
	void testAddNewPatientForEntityWithRegularData(){
		fail "Implentar"
	}
	
	/**
	 * Testea que no vuelva a agregar un paciente que la entidad
	 * ya agrego
	 */
	void testAddExistingPatientForEntity(){
		fail "Implentar"
	}
}