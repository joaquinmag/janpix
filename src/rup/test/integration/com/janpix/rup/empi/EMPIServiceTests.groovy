/**
 * Test de integracion que testea el servicio de EMPI
 * @author martin
 *
 */

package com.janpix.rup.empi

import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.DuplicateIdentifierException
import com.janpix.rup.exceptions.identifier.IdentifierNotFoundException
 
class EMPIServiceTests extends GroovyTestCase {
	
	//Servicios utilizados
	def EMPIService
	def demographicPersonService
	def grailsApplication
	def identityComparatorService
	def uuidGenerator
	def factoryMatchRecord
	
	def patient
	def person
	def healthEntity1,healthEntity2
	def city1,city2,city3,city4,city5
	def assingingAuthorityArgentina
	
	
	
	
	void setUp(){
		//Inicializo servicios
		super.setUp()
		EMPIService.demographicPersonService = demographicPersonService
		EMPIService.uuidGenerator = uuidGenerator
		EMPIService.demographicPersonService.grailsApplication =  grailsApplication
		EMPIService.demographicPersonService.identityComparatorService = identityComparatorService
		EMPIService.demographicPersonService.identityComparatorService.grailsApplication =  grailsApplication
		EMPIService.demographicPersonService.factoryMatchRecord = factoryMatchRecord
		EMPIService.factoryMatchRecord = factoryMatchRecord
		
		//Creo 2 entidades sanitarias
		healthEntity1 = new HealthEntity("2.16.32.1.256.1", "Entidad Sanitaria 1")
		healthEntity1.save(flush:true,failOnError:true)
		healthEntity2 = new HealthEntity("2.16.32.1.256.2", "Entidad Sanitaria 2")
		healthEntity2.save(flush:true,failOnError:true)
		
		//Creo algunas ciudades
		createCities()
		
		//Autoridad de asignacion de documentos
		assingingAuthorityArgentina = EmitterCountry.buildArgentinaEmitterCountry()
		assingingAuthorityArgentina.save(flush:true,failOnError:true)
		
		//Creo un nuevo paciente
		patient = new Patient(givenName: new PersonName(firstName:"Martín", lastName:"Barnech"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		patient.addToAddresses(new Address(type:Address.TYPE_LEGAL,street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		patient.save(flush:true,failOnError:true)
		
		//Creo una persona
		person = new Person(givenName: new PersonName(firstName:"Joaquin Ignacio", lastName:"Magneres"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-05-01" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city2,
			)
		person.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32900250",assigningAuthority:assingingAuthorityArgentina))
		person.addToAddresses(new Address(type:Address.TYPE_LEGAL,street:"Zapata",number:"346",floor:"5",department:"A",city:city2))
		person.addToPhoneNumbers(new PhoneNumber(type:PhoneNumber.TYPE_CELL,number:"2323521616"))

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
	 * Testea que falle la creacion de un paciente debido a que se le brinda
	 * poca informacion demografica
	 */
	void testFailCreatePatientBecauseShortInformation(){
		//Creo una persona sin administrativeSex, el cual es requerido
		def p = new Person(givenName: new PersonName(firstName:"Martin Gonzalo", lastName:"Barneche"),
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
		//Primero creo el paciente
		def returnedPatient = EMPIService.createPatient(person)
		def patientUUID = returnedPatient.uniqueId
		
		//Verifico los datos actuales del paciente que voy a modificar
		//Name
		assertEquals("Magneres, Joaquin Ignacio",returnedPatient.givenName.toString())
		//Birthplace
		assertEquals("C.A.B.A",returnedPatient.birthplace.province.name)
		//Address
		def address = returnedPatient.principalAddress()
		assertEquals("Zapata",address.street)
		assertEquals("346",address.number)
		assertEquals("5",address.floor)
		assertEquals("A",address.department)
		//Birthdate
		def birthdate = returnedPatient.birthdate
		assertEquals(ExtendedDate.TYPE_PRECISSION_DAY,birthdate.precission)
		assertEquals(Date.parse( "yyyy-M-d", "1987-05-01" ),birthdate.date)
		
		//PhoneNumber
		def phone = returnedPatient.phoneNumbers.find{it.type == PhoneNumber.TYPE_CELL}
		assert phone.number == "2323521616"
		
		
		//Modifico los datos, para eso creo una persona nueva
		def p = new Person(givenName: new PersonName(lastName:"Sosa"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_MONTH,date:Date.parse( "yyyy-M-d", "1987-07-15" )),
			birthplace:city3,
			)
		p.addToAddresses(new Address(type:Address.TYPE_CIVIL,street:"Zapata",number:"346",floor:"5",department:"B",city:city2))
		//Le agrego un identificador a la nueva persona
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_LE,number:"12870104",assigningAuthority:assingingAuthorityArgentina))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PPN,number:"AABB1234",assigningAuthority:assingingAuthorityArgentina))
		
		//Phones
		p.addToPhoneNumbers(new PhoneNumber(type:PhoneNumber.TYPE_CELL,number:"2323521717"))
		p.addToPhoneNumbers(new PhoneNumber(type:PhoneNumber.TYPE_HOME,number:"2323421646"))
		
		
		//Modifico y controlo
		def changedPatient = EMPIService.updateDemographicDataPatient(returnedPatient,p)
		//Name
		assertEquals("Sosa, Joaquin Ignacio",changedPatient.givenName.toString())
		//Birthplace
		assertEquals("Capital Federal",changedPatient.birthplace.province.name)
		//Address
		//La debe agregar como una direccion nueva y la principal ser la ultima que se cargo
		assertEquals(2,changedPatient.addresses.size()) 
		address = changedPatient.principalAddress()
		assertEquals("Zapata",address.street)
		assertEquals("346",address.number)
		assertEquals("5",address.floor)
		assertEquals("B",address.department)
		
		//Birthdate (Cambia aunque tenga una precision menor)
		birthdate = changedPatient.birthdate
		assertEquals(ExtendedDate.TYPE_PRECISSION_MONTH,birthdate.precission)
		assertEquals(Date.parse( "yyyy-M-d", "1987-07-15" ),birthdate.date)
		
		//Debe tener 3 identificadores. El documento, el pasaporte y la LE
		assertEquals(3,changedPatient.identifiers.size())
		
		def document = changedPatient.identityDocument()
		assertEquals(Identifier.TYPE_IDENTIFIER_DNI,document.type)
		assertEquals("32900250",document.number)
		assertEquals(assingingAuthorityArgentina,document.assigningAuthority)
		
		def passport = changedPatient.identifiers.find {it.type == Identifier.TYPE_IDENTIFIER_PPN}
		assertEquals(Identifier.TYPE_IDENTIFIER_PPN,passport.type)
		assertEquals("AABB1234",passport.number)
		assertEquals(assingingAuthorityArgentina,passport.assigningAuthority)
		
		def le = changedPatient.identifiers.find {it.type == Identifier.TYPE_IDENTIFIER_LE}
		assertEquals(Identifier.TYPE_IDENTIFIER_LE,le.type)
		assertEquals("12870104",le.number)
		assertEquals(assingingAuthorityArgentina,le.assigningAuthority)
		
		//Debe tener 2 telefonos
		assert 2 == changedPatient.phoneNumbers.size()
		def oldPhone = changedPatient.phoneNumbers.find{it.type == PhoneNumber.TYPE_CELL}
		assert oldPhone.number == "2323521717"
		def newPhone = changedPatient.phoneNumbers.find{it.type == PhoneNumber.TYPE_HOME}
		assert newPhone.number == "2323421646"
	}
	
	/**
	 * Testea la correcta actualizacion de algun identificador que
	 * no sea el de la entidad sanitaria
	 */
	void testUpdateOtherIdentifiers(){
		//Primero creo el paciente
		def returnedPatient = EMPIService.createPatient(person)
		def patientUUID = returnedPatient.uniqueId
		
		//creo una persona con un documento nuevo
		def p = new Person()
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"12345678",assigningAuthority:assingingAuthorityArgentina))
		
		//Mando a actualizar
		def changedPatient = EMPIService.updateDemographicDataPatient(returnedPatient,p)
		Identifier document = changedPatient.identityDocument()
		assertEquals(Identifier.TYPE_IDENTIFIER_DNI,document.type)
		assertEquals("12345678",document.number)
		assertEquals(assingingAuthorityArgentina,document.assigningAuthority)
		
	}
	
	/**
	 * Testea la actualizacion de una direccion ya existente en el paciente
	 */
	void testUpdateExistingAddress(){
		//Primero creo el paciente agregandole un unitId a la direccion
		def returnedPatient = EMPIService.createPatient(person)
		def patientUUID = returnedPatient.uniqueId
		
		def p = new Person()
		p.addToAddresses(new Address(type:Address.TYPE_LEGAL,street:"Zabala",number:"1300",city:city1))
		EMPIService.updateDemographicDataPatient(returnedPatient,p)
		
		//Address
		Address address = returnedPatient.principalAddress()
		assertEquals("Zabala",address.street)
		assertEquals("1300",address.number)
		assertEquals("5",address.floor)
		assertEquals("A",address.department)
		assertEquals(city1,address.city)
		assertEquals(Address.TYPE_LEGAL,address.type)
	}
	
	/**
	 * Testea la correcta eliminacion de un paciente del eMPI
	 * El paciente debe ser eliminado por una entidad sanitaria para que quede registro y verificar permisos
	 */
	void testRemovePatient(){
		//TODO fail "Implentar"
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
	 * Testea que falle el agregado de un nuevo identificador debido a que 
	 * ya existe ese mismo identificador para otro paciente creado por la misma entidad sanitaria
	 */
	void testFailAddNewIdentifierBecauseAlreadyExistsInOtherPatient(){
		//Primero creo 2 pacientes, cada uno con su identificador
		def returnedPatient = EMPIService.createPatient(person)
		def patientEntity1Id = "IDH1001"
		EMPIService.addEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id)
		
		def otherPatient = EMPIService.createPatient(
						new Person(givenName: new PersonName(firstName:"Maria", lastName:"Juarez"),
									birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1977-01-26" )),
									administrativeSex:Person.TYPE_SEX_FEMALE,
									birthplace:city1,
						)
						)
		def patientEntity2Id = "IDH1001"
		shouldFail(DuplicateIdentifierException) {
			EMPIService.addEntityIdentifierToPatient(otherPatient,healthEntity1,patientEntity2Id)
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
		//TODO fail "Implentar"
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
		EMPIService.updateEntityIdentifierToPatient(returnedPatient,healthEntity2,newPatientEntity2Id,patientEntity2Id)
		
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
						new Person(givenName: new PersonName(firstName:"Maria", lastName:"Juarez"),
									birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1977-01-26" )),
									administrativeSex:Person.TYPE_SEX_FEMALE,
									birthplace:city1,
						)
						)
		def patientEntity2Id = "IDH1002"
		EMPIService.addEntityIdentifierToPatient(otherPatient,healthEntity1,patientEntity2Id)
		
		//Intento modificar el ID de un paciente por el del otro
		shouldFail(DuplicateIdentifierException) {
			EMPIService.updateEntityIdentifierToPatient(returnedPatient,healthEntity1,patientEntity1Id,patientEntity2Id)
		}
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
		shouldFail(IdentifierNotFoundException) {
			EMPIService.updateEntityIdentifierToPatient(returnedPatient,healthEntity2,"IDH1555","IDH1999")
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
	 * Testea el correcto matcheo de un paciente sin incluir los posibles matcheos
	 */
	void testGetAllMatchedPatients(){
		def p = new Patient(givenName: new PersonName(firstName:"Martin Gonzalo", lastName:"Barneche"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-06" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p.addToIdentifiers(new Identifier(type:'DNI',number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		p.addToAddresses(new Address(type:Address.TYPE_CIVIL,street:"Constitucion",number:"2203",zipCode:"6700",city:city1))
		p.save(flush:true,failOnError:true)
		
		def matchedPatients = EMPIService.getAllMatchedPatients(patient,false)
		assertTrue(matchedPatients.size() == 1)
		def matchPatient = matchedPatients.get(0)
		assertEquals(p,matchPatient.person)
		//assertTrue("El paciente ${p} deberia de matchear",matchedPatients.contains(new MatchRecord(p,0d)))
	}
	
	/**
	 * Testea el correcto matcheo de un paciente incluidos los que son posibles matcheos
	 */
	void testGetAllMatchedPatientsIncludePossible(){
		def p = new Patient(givenName: new PersonName(firstName:"Martín", lastName:"Barnech"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p.addToIdentifiers(new Identifier(type:'DNI',number:"33850137",assigningAuthority:assingingAuthorityArgentina))
		p.addToAddresses(new Address(type:Address.TYPE_CIVIL,street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		p.save(flush:true,failOnError:true)
		
		//Primero busco sin incluir posibles
		assertFalse(EMPIService.matchPatient(patient,false))
		
		//Agrego los posibles
		def matchedPatients = EMPIService.getAllMatchedPatients(patient,true)
		
		assertEquals(1,matchedPatients.size())
		assertTrue("El paciente ${p} deberia de matchear",matchedPatients.contains(new MatchRecord(p,0d)))
	}
		
	//########################################
	//### Relaciones de pacientes ### 
	//########################################
	/**
	 * Testea la correcta obtencion de todas las relaciones que tiene un paciente
	 * ya agregado al eMPI con otros pacientes existentes en el eMPI
	 */
	void testGetRelationshipPerson(){
		
	}
	
	/**
	 * Testea que se agregue correctamente una relacion de una persona a otra
	 * Ambas existentes en el eMPI
	 */
	void testAddRelationshipPerson(){
		
	}
	
	/**
	 * Testea que se elimine de manera correcta la relacion
	 */
	void testRemoveRelationshipPerson(){
		
	}
	
	/**
	 * Testea que se actualice correctamente la relacion
	 * La actualizacion puede ser del estado como del tipo
	 */
	void testUpdateRelationshipPerson(){
		
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