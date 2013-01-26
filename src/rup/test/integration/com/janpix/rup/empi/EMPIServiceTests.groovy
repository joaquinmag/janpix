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
		EMPIService.demographicPatientService = new DemographicPatientIService()
		
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
	void testAddNewPatient() {
		def patientEntityId = "HC0012"
		def returnedPatient = EMPIService.addNewPatient(patient,healthEntity1,patientEntityId)
		
		assertNotNull(returnedPatient)
		
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
	 * Testea que se agregue el identificador de la nueva entidad al paciente que ya existe en el eMPI
	 * En este test se pasa exactamente el mismo paciente
	 */
	void testAddNewIdentifierToPatient(){
		fail "Implentar"
	}
	
	/**
	 * Testea que se agregue el identificador de la nueva entidad al paciente que ya existe en el eMPI
	 * Los datos pasados del nuevo paciente no son iguales al existente
	 * y el algoritmo de matcheo demografico debe darse cuenta
	 */
	void testAddNewIdentifierToPatientWithRegularData(){
		fail "Implentar"
	}
	
	/**
	 * Testea que no vuelva a agregar un paciente que la entidad sanitaria
	 * ya agrego
	 */
	void testDontAddExistingIdentifierForEntity(){
		fail "Implentar"
	}
	
	/**
	 * Testea la correcta eliminacion de un paciente del eMPI
	 * El paciente debe ser eliminado por una entidad sanitaria para que quede registro y verificar permisos
	 */
	void testRemovePatient(){
		fail "Implentar"
	}
	
	/**
	 * Testea la correcta eliminación de un identificador por parte de una entidad sanitaria
	 */
	void testRemoveIdentifierToPatient(){
		fail "Implentar"
	}
	
	/**
	 * Testea la correcta actualizacion de un identificador por parte de una entidad sanitaria
	 */
	void testUpdateIdentifierToPatient(){
		fail "Implentar"
	}
	
	/**
	 * Testea la correcta obtencion de todos los identificadores que puede tener un paciente
	 */
	void testFindIdentifiersPatient(){
		fail "Implentar"
	}
	
	/**
	 * Testea la obtencion del identificador unico de un paciente (CUIS) a partir
	 * del identificador de alguna entidad sanitaria
	 */
	void testGetUniqueIdFromEntityIdentifier(){
		fail "Implentar"
	}
	
	/**
	 * Testea la correcta union de dos pacientes diferentes que existen en el eMPI
	 * y que alguna entidad sanitaria dice que son el mismo
	 */
	void testMergePatients(){
		fail "Implentar"
	}
	
	/**
	 * Testea la correcta obtencion de todas las relaciones que tiene un paciente
	 * ya agregado al eMPI con otros pacientes existentes en el eMPI
	 */
	void testGetRelationshipPerson(){
		fail "Implentar"
	}
	
	/**
	 * Testea que se agregue correctamente una relacion de una persona a otra
	 * Ambas existentes en el eMPI
	 */
	void testAddRelationshipPerson(){
		fail "Implentar"
	}
	
	/**
	 * Testea que se elimine de manera correcta la relacion
	 */
	void testRemoveRelationshipPerson(){
		fail "Implentar"
	}
	
	/**
	 * Testea que se actualice correctamente la relacion
	 * La actualizacion puede ser del estado como del tipo
	 */
	void testUpdateRelationshipPerson(){
		fail "Implentar"
	}
}