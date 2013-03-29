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
	def patient
	def person
	def healthEntity1
	def healthEntity2
	
	void setUp(){
		//Inicializo servicios
		super.setUp()
		EMPIService = new EMPIService()
		EMPIService.demographicPersonService = new DemographicPersonService()
		EMPIService.demographicPersonService.grailsApplication =  new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
		EMPIService.demographicPersonService.identityComparatorService = new IdentityComparatorService()
		EMPIService.demographicPersonService.identityComparatorService.grailsApplication =  new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
		
		//Creo 2 entidades sanitarias
		healthEntity1 = new HealthEntity(name:"Entidad Sanitaria 1")
		healthEntity1.save(flush:true,failOnError:true)
		healthEntity2 = new HealthEntity(name:"Entidad Sanitaria 2")
		healthEntity2.save(flush:true,failOnError:true)
		
		//Creo algunas ciudades
		/*def city = new City(country:"Argentina",province:"Buenos Aires",name:"Luján")
		city.save(flush:true,failOnError:true)
		def city1 = new City(country:"Argentina",province:"C.A.B.A",name:"C.A.B.A")
		city1.save(flush:true,failOnError:true)
		//Creo un nuevo paciente
		patient = new Patient(givenName: new Name(firstName:"Martín", lastName:"Barnech"),
					birthdate: Date.parse( "yyyy-M-d", "1987-01-16" ),
					document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32850137"),
					address: new Address(street:"Constitución",number:"2213",zipCode:"6700",town:"Luján"),
					administrativeSex:Person.TYPE_SEX_MALE,
					livingplace:city,
					birthplace:city,
					motherName: new Name(firstName:"Maria Olga Lucia", lastName:"Mannino"),
					fatherName: new Name(firstName:"Pablo Juan", lastName:"Barnech"),
					)
		patient.save(flush:true,failOnError:true)
		
		//Creo una persona
		person	= new Person(givenName: new Name(firstName:"Joaquin Ignacio", lastName:"Magneres"),
			birthdate: Date.parse( "yyyy-M-d", "1987-05-01" ),
			document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32900250"),
			address: new Address(street:"Zapata",number:"346",floor:"5",depart:"A",town:"Belgrano"),
			administrativeSex:Person.TYPE_SEX_MALE,
			livingplace:city1,
			birthplace:city1,
			motherName: new Name(firstName:"Lucia", lastName:"Fontela"),
			fatherName: new Name(firstName:"Roque", lastName:"Margenes"),
		)*/
	}

	/**
	 * Testea que se agregue correctamente un paciente
	 * que no existe en el eMPI
	 */
	void testCreatePatient() {
		def returnedPatient = EMPIService.createPatient(person)
		
		//Solo debe existir el paciente viejo y el nuevo creado
		assertEquals(2,Patient.count())
		
		//Debe tener un identificador unico asignado
		assertNotNull(returnedPatient.uniqueId)
		
	}
	
	/**
	 * Testea que falle la creacion de un nuevo paciente porque ya existe un paciente
	 * que matchea con esos datos demograficos
	 */
	void testFailCreatePatientBecauseMuchMatched(){
		/*def p = new Person(givenName: new Name(firstName:"Martin Gonzalo", lastName:"Barneche"),
					birthdate: Date.parse( "yyyy-M-d", "1987-01-16" ),
					document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32850187"),
					address: new Address(street:"Constitucion",number:"2203",zipCode:"6700",town:"Luján"),
					administrativeSex:Person.TYPE_SEX_MALE,
					livingplace:City.findByName("Luján"),
					birthplace:City.findByName("Luján"),
					motherName: new Name(firstName:"Maria Olga", lastName:"Mannino"),
					fatherName: new Name(firstName:"Pablo Juan", lastName:"Barnech"),
					)*/
		try{
			def returnedPatient = EMPIService.createPatient(p)
			fail "No puede seguir"
		}catch(ExistingPatientException e){
			//Si lanza excepcion es correcto
			assertTrue(true)
		}
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
		//Despues agrego el identificador al paciente
		def patientEntityId = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(patient,healthEntity1,patientEntityId)
		
		def identifiers = patient.identifiers
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
		//Primero creo el paciente
		def returnedPatient = EMPIService.createPatient(person)
		def patientUUID = returnedPatient.uniqueId
		
		def findedPatient = EMPIService.findPatientByUUID(patientUUID)
		
		assertEquals(returnedPatient,findedPatient)
		
	}
	
	/**
	 * Testea la correcta obtencion de un paciente que se busca por el id que tiene 
	 * la entidad sanitaria para representarlo
	 */
	void testFindPatientByHealthEntityId(){
		//Primero creo el paciente
		def returnedPatient = EMPIService.createPatient(person)
		
		//Le agrego 2 identificadores de entidades sanitarias
		def patientEntity1Id = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id)
		def patientEntity2Id = "IDH2005"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity2,patientEntity2Id)
		
		//Busco al paciente a traves de las 2 entidades
		def findedPatient1 = EMPIService.findPatientByHealthEntityId(patientEntity1Id,healthEntity1)
		assertEquals(returnedPatient,findedPatient1)
		
		def findedPatient2 = EMPIService.findPatientByHealthEntityId(patientEntity2Id,healthEntity2)
		assertEquals(returnedPatient,findedPatient2)
	}
	
	/**
	 * Testea el correcto matcheo de un paciente
	 */
	void testGetAllMatchedPatients(){
		fail "Implentar"
	}
	
	/**
	 * Testea el correcto matcheo de un paciente incluidos los que son posibles matcheos
	 */
	void testGetAllMatchedPatientsIncludePossible(){
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
		//Primero creo el paciente
		def returnedPatient = EMPIService.createPatient(person)
		
		//Le agrego 2 identificadores de entidades sanitarias
		def patientEntity1Id = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id)
		def patientEntity2Id = "IDH2005"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity2,patientEntity2Id)
		
		//Actualizo la identificacion de algun paciente
		def newPatientEntity2Id = "IDH2005898"
		EMPIService.updateEntityIdentifierToPatient(returnedPatient,healthEntity2,patientEntity2Id,newPatientEntity2Id)
		
		//Busco al paciente por el viejo identificador y debe devolver null
		assertNull(EMPIService.findPatientByHealthEntityId(patientEntity2Id,healthEntity2))
		
		//Busco por el nuevo
		assertEquals(returnedPatient,EMPIService.findPatientByHealthEntityId(newPatientEntity2Id,healthEntity2))
		
		
	}
	
	/**
	 * Testea la correcta obtencion de todos los identificadores que puede tener un paciente
	 */
	void testFindIdentifiersPatient(){
		//Primero creo el paciente
		def returnedPatient = EMPIService.createPatient(person)
		
		//Le agrego 2 identificadores de entidades sanitarias
		def patientEntity1Id = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id)
		def patientEntity2Id = "IDH2005"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity2,patientEntity2Id)
		
		//Busco el paciente
		def findedPatient = EMPIService.findPatientByUUID(returnedPatient.uniqueId)
		def identifiers = findedPatient.identifiers
		
		//Deben tener 2 identificadores
		assertEquals(2,identifiers.size())
		
		//Comparo los identificadores
		def identifier1 = new Identifier(type:Identifier.TYPE_PI,number:patientEntity1Id,assigningAuthority:healthEntity1)
		def identifier2 = new Identifier(type:Identifier.TYPE_PI,number:patientEntity2Id,assigningAuthority:healthEntity2)
		identifiers.each {
			if(it == identifier1 || it==identifier2)
				assertTrue(true)
			else
				fail "Los identificadores devueltos no son los esperados"
		}
		
	}
	
	/**
	 * Testea la obtencion del identificador unico de un paciente (CUIS) a partir
	 * del identificador de alguna entidad sanitaria
	 */
	void testGetUniqueIdFromEntityIdentifier(){
		//Primero creo el paciente
		def returnedPatient = EMPIService.createPatient(person)
		
		//Le agrego algun identificador
		def patientEntity1Id = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id)
		
		
		//Busco al paciente a traves de la entidad
		def findedPatient1 = EMPIService.findPatientByHealthEntityId(patientEntity1Id,healthEntity1)
		assertEquals(returnedPatient.uniqueId,findedPatient1.uniqueId)
		
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