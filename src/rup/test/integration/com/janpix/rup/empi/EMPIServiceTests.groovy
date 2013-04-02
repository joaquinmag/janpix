/**
 * Test de integracion que testea el servicio de EMPI
 * @author martin
 *
 */

package com.janpix.rup.empi
import groovy.util.GroovyTestCase;
import com.janpix.rup.exceptions.*
import com.janpix.rup.infrastructure.MockUUIDGenerator
 
class EMPIServiceTests extends GroovyTestCase {
	
	def EMPIService
	def patient
	def person
	def healthEntity1,healthEntity2
	def city1,city2,city3,city4,city5
	def assingingAuthorityArgentina
	
	def uuidGenerator
	
	
	void setUp(){
		//Inicializo servicios
		super.setUp()
		EMPIService = new EMPIService()
		EMPIService.demographicPersonService = new DemographicPersonService()
		EMPIService.uuidGenerator = uuidGenerator
		EMPIService.demographicPersonService.grailsApplication =  new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
		EMPIService.demographicPersonService.identityComparatorService = new IdentityComparatorService()
		EMPIService.demographicPersonService.identityComparatorService.grailsApplication =  new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
		
		//Creo 2 entidades sanitarias
		healthEntity1 = new HealthEntity(name:"Entidad Sanitaria 1")
		healthEntity1.save(flush:true,failOnError:true)
		healthEntity2 = new HealthEntity(name:"Entidad Sanitaria 2")
		healthEntity2.save(flush:true,failOnError:true)
		
		//Creo algunas ciudades
		createCities()
		
		//Autoridad de asignacion de documentos
		assingingAuthorityArgentina = new EmitterCountry(name:"Argentina")
		assingingAuthorityArgentina.save(flush:true,failOnError:true)
		
		//Creo un nuevo paciente
		patient = new Patient(givenName: new PersonName(firstName:"Martín", lastName:"Barnech",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		patient.addToIdentifiers(new Identifier(type:'DNI',number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		patient.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",neighborhood:"Luján",city:city1))
		patient.save(flush:true,failOnError:true)
		
		//Creo una persona
		person = new Person(givenName: new PersonName(firstName:"Joaquin Ignacio", lastName:"Magneres",motherLastName:"Fontela"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-05-01" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city2,
			)
		person.addToIdentifiers(new Identifier(type:'DNI',number:"32900250",assigningAuthority:assingingAuthorityArgentina))
		person.addToAddresses(new Address(street:"Zapata",number:"346",floor:"5",department:"A",neighborhood:"Belgrano",city:city2))

	}

	//########################################
	// ### ABM Paciente ###
	//########################################
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
		def p = new Person(givenName: new PersonName(firstName:"Martin Gonzalo", lastName:"Barneche",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-06" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p.addToIdentifiers(new Identifier(type:'DNI',number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		p.addToAddresses(new Address(street:"Constitucion",number:"2203",zipCode:"6700",neighborhood:"Luján",city:city1))
		p.save(flush:true,failOnError:true)
		
		shouldFail (ExistingPatientException) {
			EMPIService.createPatient(p)
		}
	}
	
	/**
	 * Testea que falle la creacion de un paciente debido a que se le brinda
	 * poca informacion demografica
	 */
	void testFailCreatePatientBecauseShortInformation(){
		//Creo una persona sin administrativeSex, el cual es requerido
		def p = new Person(givenName: new PersonName(firstName:"Martin Gonzalo", lastName:"Barneche",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-06" )),
			birthplace:city1,
			)
		shouldFail(ShortDemographicDataException) {
			def returnedPatient = EMPIService.createPatient(p)
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
	 * Testea la correcta union de dos pacientes diferentes que existen en el eMPI
	 * y que alguna entidad sanitaria dice que son el mismo
	 */
	void testMergePatients(){
		fail "Implentar"
	}
	
	//########################################
	// ### ABM Identificadores paciente ###
	//########################################
	/**
	 * Testea que se agregue el identificador de la nueva entidad al paciente que ya existe en el eMPI
	 */
	void testAddNewIdentifierToPatient(){		
		//Despues agrego el identificador al paciente
		def patientEntityId = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(patient,healthEntity1,patientEntityId)
		
		def identifiers = patient.identifiers
		assertEquals(2,identifiers.size()) //El documento y el que agrega la entidad sanitaria
		
		def identifierHealthEntity = identifiers.find{it.type == Identifier.TYPE_IDENTIFIER_PI}
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,identifierHealthEntity.type)
		assertEquals(patientEntityId,identifierHealthEntity.number)
		assertEquals(healthEntity1,identifierHealthEntity.assigningAuthority)
	}
	
	/**
	 * Testea que falle el agregado de un nuevo identificador debido a que el paciente no existe
	 */
	void testFailAddNewIdentifierBecauseNotExistsPatient(){
		shouldFail(DontExistingPatientException) {
			//Creo un paciente que no existe en la base de datos
			def patientMock = new Patient(person)
			patientMock.uniqueId = new PatientIdentifier(mainId:"UUID-1234")
			
			//Despues agrego el identificador al paciente
			def patientEntityId = "IDH1001"
			EMPIService.addEntityIdentifierToPatient(patientMock,healthEntity1,patientEntityId)
			
		}
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
	 * Testea que falle la actualizacion de un identificador ya que
	 * el mismo se encuentra asignado en otro paciente
	 */
	void testFailUpdateIdentifierPatientBecauseAlreadyExists(){
		//Primero creo 2 pacientes, cada uno con su identificador
		def returnedPatient = EMPIService.createPatient(person)
		def patientEntity1Id = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id)
		
		def otherPatient = EMPIService.createPatient(
						new Person(givenName: new PersonName(firstName:"Maria", lastName:"Juarez",motherLastName:"Rodriguez"),
									birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1977-01-26" )),
									administrativeSex:Person.TYPE_SEX_FEMALE,
									birthplace:city1,
						)
						)
		def patientEntity2Id = "IDH1002"
		EMPIService.addEntityIdentifierToPatient(otherPatient,healthEntity1,patientEntity2Id)
		
		//Intento modificar el ID de un paciente por el del otro
		def e = shouldFail(IdentifierException) {
			EMPIService.updateEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id,patientEntity2Id)
		}
		
		//assertEquals(IdentifierException.TYPE_ENTITY_DUPLICATE,e.type)
	}
	
	/**
	 * Testea que falle la actualizacion de un identificador ya que
	 * el identificador pasado para actualizar no existe
	 */
	void testFailUpdateIdentifierPatientBecauseDontExistsIdentifier(){
		//Creo un paciente con su identificador
		def returnedPatient = EMPIService.createPatient(person)
		def patientEntity1Id = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id)
		
		//Mando a actualizar un identificador que NO existe
		shouldFail(IdentifierException){
			EMPIService.updateEntityIdentifierToPatient(returnedPatient,healthEntity1,"IDH1999","IDH1555")
			//assertEquals(IdentifierException.TYPE_NOTFOUND,e.type)
		}
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
		
		//Deben tener 3 identificadores (el documento y el de ambas entidades sanitarias)
		assertEquals(3,identifiers.size())
		
		//Comparo los identificadores
		def identifier1 = new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:patientEntity1Id,assigningAuthority:healthEntity1)
		def identifier2 = new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:patientEntity2Id,assigningAuthority:healthEntity2)
		identifiers.each {
			if(it.type == Identifier.TYPE_IDENTIFIER_PI){
				if(it == identifier1 || it==identifier2)
					assertTrue(true)
				else
					fail "Los identificadores devueltos no son los esperados"
			}
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
	
	//########################################
	//### Matcheo de pacientes ### 
	//########################################
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
		
	//########################################
	//### Relaciones de pacientes ### 
	//########################################
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
	
	
	/** Metodos Privados **/
	def private createCities(){
		//Paises y provincias
		def country  = new Country(name:"Argentina").save(failOnError:true,flush:true)
		def provBsAs1 = new Province(name:"Buenos Aires",country:country)
		provBsAs1.save(failOnError:true,flush:true)
		def provBsAs2 = new Province(name:"Bs. As.",country:country)
		provBsAs2.save(failOnError:true,flush:true)
		def provCABA1 = new Province(name:"Capital Federal",country:country)
		provCABA1.save(failOnError:true,flush:true)
		def provCABA2 = new Province(name:"C.A.B.A",country:country)
		provCABA2.save(failOnError:true,flush:true)
		
		
		city1 = new City(province:provBsAs1,name:"Luján")
		city1.save(flush:true,failOnError:true)
		city2 = new City(province:provCABA2,name:"C.A.B.A")
		city2.save(flush:true,failOnError:true)
		city3 = new City(province:provCABA1,name:"Capital Federal")
		city3.save(flush:true,failOnError:true)
		city4 = new City(province:provBsAs2,name:"Lujan")
		city4.save(flush:true,failOnError:true)
		city5 = new City(province:provBsAs2,name:"Pergamino")
		city5.save(flush:true,failOnError:true)
	}
}