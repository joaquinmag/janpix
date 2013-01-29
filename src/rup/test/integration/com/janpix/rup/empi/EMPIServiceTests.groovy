/**
 * Test de integracion que testea el servicio de EMPI
 * @author martin
 *
 */

package com.janpix.rup.empi
import groovy.util.GroovyTestCase;
import com.janpix.rup.exceptions.*
 
class EMPIServiceTests extends GroovyTestCase {
	//static transactional = false
	
	def EMPIService
	def person
	def healthEntity1
	def healthEntity2
	
	void setUp(){
		super.setUp()
		EMPIService = new EMPIService()
		EMPIService.demographicPersonService = new DemographicPersonService()
		
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

		person = new Person(
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
	void testCreatePatient() {
		def returnedPatient = EMPIService.createPatient(person)
		
		//Solo debe existir un paciente guardado
		assertEquals(1,Patient.count())
		
		//Debe tener un identificador unico asignado
		assertNotNull(returnedPatient.uniqueId)
		
	}
	
	/**
	 * Testea que falle la creacion de un nuevo paciente porque ya existe un paciente
	 * que matchea con esos datos demograficos
	 */
	void testFailCreatePatientBecauseMuchMatched(){
		fail "Implentar"
	}
	
	/**
	 * Testea que falle la creacion de un paciente debido a que se le brinda
	 * poca informacion demografica
	 */
	void testFailCreatePatientBecauseShortInformation(){
		fail "Implentar"
	}
	
	
	/**
	 * Testea que se agregue el identificador de la nueva entidad al paciente que ya existe en el eMPI
	 */
	void testAddNewIdentifierToPatient(){
		//Primero agrego el paciente
		def returnedPatient = EMPIService.createPatient(person)
		
		//Despues agrego el identificador al paciente
		def patientEntityId = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntityId)
		
		def identifiers = returnedPatient.identifiers
		assertEquals(1,identifiers.size())
		identifiers.each {
			assertEquals("PI",it.type)
			assertEquals(patientEntityId,it.number)
			assertEquals(healthEntity1,it.assigningAuthority)
		}
	}
	
	/**
	 * Testea que falle el agregado de un nuevo identificador debido a que el paciente no existe
	 */
	void testFailAddNewIdentifierBecauseNotExistsPatient(){
		try{
			//Creo un paciente que no existe en la base de datos
			def patientMock = new Patient(person.properties)
			patientMock.uniqueId = "UUID-1234"
			
			//Despues agrego el identificador al paciente
			def patientEntityId = "IDH1001"
			EMPIService.addEntityIdentifierToPatient(patientMock,healthEntity1,patientEntityId)
			
			fail "No puede llegar hasta aca"
			
		}catch(DontExistingPatientException e){
			//Si lanza excepcion es correcto
			assertTrue(true)
		}
	}
	
	/**
	 * Testea la correcta obtencion de un paciente a partir de su UUID
	 */
	void testFindPatientByUUid(){
		fail "Implentar"
	}
	
	/**
	 * Testea la correcta obtencion de un paciente que se busca por el id que tiene 
	 * la entidad sanitaria para representarlo
	 */
	void testFindPatientByHealthEntityId(){
		fail "Implentar"
	}
	
	/**
	 * Testea el correcto matcheo de un paciente
	 */
	void testMatchPatient(){
		fail "Implentar"
	}
	
	/**
	 * Testea que un paciente no matchee con ninguno existente
	 */
	void testDontMatchPatient(){
		fail "Implentar"
	}
	
	/**
	 * Testea la correcta actualizacion de la informacion demografica de un paciente
	 */
	void testUpdateDemographicDataPatient(){
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