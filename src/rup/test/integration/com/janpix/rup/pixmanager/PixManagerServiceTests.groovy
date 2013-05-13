/**
 * Test de integracion que testea el servicio del PixManager
 * @author martin
 *
 */

package com.janpix.rup.pixmanager

import com.janpix.rup.empi.*
import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.RUPException;
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.DuplicateIdentifierException
import com.janpix.rup.exceptions.identifier.IdentifierNotFoundException
import com.janpix.rup.services.contracts.ACKMessage;
import com.janpix.rup.services.contracts.ACKMessage.TypeCode;

 
class PixManagerServiceTests extends GroovyTestCase {
	
	//Servicios utilizados
	def EMPIService
	def demographicPersonService
	def grailsApplication
	def identityComparatorService
	def uuidGenerator
	def pixManagerService
	def messageSource
	def i18nMessage
	def factoryMatchRecord
	
	Patient patient
	Person person
	def healthEntity1,healthEntity2,healthEntity3,healthEntity4,rup
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
		
		//Creo 2 entidades sanitarias y el RUP
		healthEntity1 = new HealthEntity(name:"Entidad Sanitaria 1")
		healthEntity1.save(flush:true,failOnError:true)
		healthEntity2 = new HealthEntity(name:"Entidad Sanitaria 2")
		healthEntity2.save(flush:true,failOnError:true)
		healthEntity3 = new HealthEntity(name:"Entidad Sanitaria 3")
		healthEntity3.save(flush:true,failOnError:true)
		healthEntity4 = new HealthEntity(name:"Entidad Sanitaria 4")
		healthEntity4.save(flush:true,failOnError:true)
		rup			 = new AuthorityRUP(name:"Registro Único de Pacientes ")
		rup.save(flush:true,failOnError:true)
		
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
		patient.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"A123",assigningAuthority:healthEntity1))
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"B123",assigningAuthority:healthEntity2))
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"C123",assigningAuthority:healthEntity3))
		patient.save(flush:true,failOnError:true)
		
		person = new Person(givenName: new PersonName(firstName:"Joaquin Ignacio", lastName:"Magneres",motherLastName:"Fontela"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-05-01" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city2,
			)
		person.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32900250",assigningAuthority:assingingAuthorityArgentina))
		person.addToAddresses(new Address(street:"Zapata",number:"346",floor:"5",department:"A",city:city2))
	}
	
	
	/**
	 * Testea la correcta obtencion del CUIS de un paciente
	 */
	void testGetCUIS(){
		def identifiers = pixManagerService.patientRegistryGetIdentifiersQuery("C123",healthEntity3,[assigningAuthorityService.rupAuthority()])
		assertEquals(1,identifiers.size())
		
		Identifier cuis = identifiers.get(0)
		assertEquals(patient.uniqueId.toString(),cuis.number)
		assertEquals(assigningAuthorityService.rupAuthority(),cuis.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,cuis.type)
	}
	
	/**
	 * Testea que no devuelva ningun identificador porque no existe paciente en los dominios pasados
	 */
	void testGetIdentifiersReturnEmpty(){
		def identifiers = pixManagerService.patientRegistryGetIdentifiersQuery("C123",healthEntity3,[new HealthEntity("2.16.32.1.255.255", "No existe")])
		assertEquals(0,identifiers.size())
	}
	
	/**
	 * Testea que devuelva todos los identificadores de un paciente ya que no se le pasan dominios
	 */
	void testGetAllIdentifiers(){
		def identifiers = pixManagerService.patientRegistryGetIdentifiersQuery("C123",healthEntity3)
		assertEquals(3,identifiers.size())
		
		Identifier cuis = identifiers.find{it.assigningAuthority == AssigningAuthority.rupAuthority()}
		assertEquals(patient.uniqueId.toString(),cuis.number)
		assertEquals(AssigningAuthority.rupAuthority(),cuis.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,cuis.type)
		
		Identifier idh1 = identifiers.find{it.assigningAuthority == healthEntity1}
		assertEquals("A123",idh1.number)
		assertEquals(healthEntity1,idh1.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,idh1.type)
		
		Identifier idh2 = identifiers.find{it.assigningAuthority == healthEntity2}
		assertEquals("B123",idh2.number)
		assertEquals(healthEntity2,idh2.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,idh2.type)
	}
	
	/**
	 * Testea que se devuelvan los identificadores de los dominios pasados
	 */
	void testGetDomainIdentifiers(){
		def identifiers = pixManagerService.patientRegistryGetIdentifiersQuery("C123",healthEntity3,[healthEntity1,healthEntity2])
		assertEquals(2,identifiers.size())
		
		Identifier idh1 = identifiers.find{it.assigningAuthority == healthEntity1}
		assertEquals("A123",idh1.number)
		assertEquals(healthEntity1,idh1.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,idh1.type)
		
		Identifier idh2 = identifiers.find{it.assigningAuthority == healthEntity2}
		assertEquals("B123",idh2.number)
		assertEquals(healthEntity2,idh2.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,idh2.type)
	}

	/**
	 * Llama al servicio para crear un paciente que NO existe
	 */
	void testCreateNotExistsPatient(){
		ACKMessage ack = pixManagerService.patientRegistryRecordAdded(person,healthEntity1,"A123456")
		
		//Verifico ACK
		assertEquals(TypeCode.SuccededCreation,ack.typeCode)
		assertEquals(i18nMessage("pixmanager.ackmessage.creation.succeded"),ack.text)
		
		
		def patients = Patient.list()
		//2 pacientes. El creado en setup y el nuevo que creo
		assertEquals(2,patients.size())
		//Verifico que se haya creado y agregado el identificador
		def createdPatient = patients.find{it.identityDocument().number == "32900250"}
		assertEquals("Magneres",createdPatient.givenName.lastName)
		
		def identifier = createdPatient.identifiers.find{it.type == Identifier.TYPE_IDENTIFIER_PI}
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,identifier.type)
		assertEquals("A123456",identifier.number)
		assertEquals(healthEntity1,identifier.assigningAuthority)
	}
	
	/**
	 * LLama al servicio para crear un paciente con alto nivel de matcheo
	 * Lo que va a hacer es agregar el identificador al paciente
	 */
	void testCreateHighMatchingPatient(){
		//Creo una persona con los mismos datos que patient
		def p = new Person(givenName: new PersonName(firstName:"Martín", lastName:"Barnech",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:assingingAuthorityArgentina))

		
		ACKMessage ack = pixManagerService.patientRegistryRecordAdded(p,healthEntity4,"F123456")
		//Verifico ACK
		assertEquals(TypeCode.SuccededInsertion,ack.typeCode)
		assertEquals(i18nMessage("pixmanager.ackmessage.insertion.succeded"),ack.text)
		
		
		def patients = Patient.list()
		//1 paciente. El creado en setup que se le agrego el identificador
		assertEquals(1,patients.size())
		def createdPatient = patients.find{it.identityDocument().number == "32850137"}
		assertEquals("Barnech",createdPatient.givenName.lastName)
		
		//Verifico que tenga 4 identificadores (los 4 anteriores mas el nuevo)
		assert createdPatient.identifiers.size() == 5
		def identifier = createdPatient.identifiers.find{it.type == Identifier.TYPE_IDENTIFIER_PI && it.assigningAuthority == healthEntity4}
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,identifier.type)
		assertEquals("F123456",identifier.number)
		assertEquals(healthEntity4,identifier.assigningAuthority)
	}
	
	/**
	 * Testea que falle la creacion del paciente porque tiene varios matcheos
	 */
	void testFailCreatePatientBecauseMuchMatched(){		
		//Creo una persona parecida al paciente que ya existe
		def p = new Person(givenName: new PersonName(firstName:"Martín Gonzalo", lastName:"Varnech",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850139",assigningAuthority:assingingAuthorityArgentina))

		
		ACKMessage ack = pixManagerService.patientRegistryRecordAdded(p,healthEntity4,"F123456")
		//Verifico ACK
		assertEquals(TypeCode.PossibleMatchingPatientsError,ack.typeCode)
		assertEquals(i18nMessage("pixmanager.ackmessage.possiblematching.error"),ack.text)
		
		def patients = Patient.list()
		//1 paciente. El que ya existia
		assertEquals(1,patients.size())

	}
	
	/**
	 * Testea que se actualice correctamente la informacion demografica y se agreguen los identificadores nuevos
	 */
	void testUpdateDemographicDataAndIdentifiers(){
		Person p = new Person(givenName: new PersonName(firstName:"Joaquin Ignacio", lastName:"Magneres"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-05-01" )),
			administrativeSex:Person.TYPE_SEX_AMBIGUOS,
			birthplace:city2
			)
		//p.addToAddresses(new Address(street:"Zapata",number:"346",floor:"5",zipCode:"6700",city:city2))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"33850139",assigningAuthority:assingingAuthorityArgentina))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"YMCA123",assigningAuthority:healthEntity4))
		
		ACKMessage ack = pixManagerService.patientRegistryRecordRevised(patient,p,healthEntity4)
		
		//ACK
		assert TypeCode.SuccededUpdated == ack.typeCode
		assert i18nMessage("pixmanager.ackmessage.updated.succeded") == ack.text
		
		//Patient
		assert "${patient.givenName}" 				== "Magneres, Joaquin Ignacio"
		assert patient.givenName.motherLastName		== "Mannino"
		assert patient.birthdate					== new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-05-01" ))
		assert patient.administrativeSex			== Person.TYPE_SEX_AMBIGUOS
		assert patient.birthplace					== city2
		assert patient.identifiers.size()			== 5 //Los 4 de entidades sanitarias mas el documento
		assert patient.identityDocument().number 	== "33850139"
		
		Identifier addedIdentifier = patient.identifiers.find {
			it.type == Identifier.TYPE_IDENTIFIER_PI &&
			it.assigningAuthority == healthEntity4 &&
			it.number == "YMCA123"
			 }
		assertNotNull(addedIdentifier)
		
	}
	
	/**
	 * Testea que se actualicen correctamente los identificadores que ya existian
	 */
	void testUpdateIdentifer(){
		//Mando a actualizar el identificador
		Person p = new Person()
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"Z321",assigningAuthority:healthEntity1))
		ACKMessage ack = pixManagerService.patientRegistryRecordRevised(patient,p,healthEntity1)
		
		assert TypeCode.SuccededUpdated == ack.typeCode
		assert i18nMessage("pixmanager.ackmessage.updated.succeded") == ack.text
		
		
		assert patient.identifiers.size() == 4 //Los 4 que siempre tuvo (DNI + 3 entidades sanitarias)
		Identifier updatedIdentifier = patient.identifiers.find {
				it.type == Identifier.TYPE_IDENTIFIER_PI && 
				it.assigningAuthority == healthEntity1 &&
				it.number == "Z321"
				 }
		assertNotNull(updatedIdentifier)
	}
	
	/**
	 * Testea que falle la actualizacion de un paciente porque el mismo no existe
	 */
	void testFailUpdateBecauseNotExistingPatient(){
		Patient p = new Patient(givenName: new PersonName(firstName:"Joaquin Ignacio", lastName:"Magneres"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-05-01" )),
			administrativeSex:Person.TYPE_SEX_AMBIGUOS,
			birthplace:city2
			)
		p.addToAddresses(new Address(street:"Zapata",number:"346",floor:"5",zipCode:"6700",city:city2))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"33850139",assigningAuthority:assingingAuthorityArgentina))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"YMCA123",assigningAuthority:healthEntity4))
		
		ACKMessage ack = pixManagerService.patientRegistryRecordRevised(p,p,healthEntity4)
		
		//ACK
		assert TypeCode.DontExistingPatientError == ack.typeCode
		
	}
	
	/**
	 * Testea que falle la actualizacion de un paciente porque el identificador agregado no es valido
	 */
	void testFailUpdateBecauseNotValidIdentifier(){
		//Mando a actualizar el identificador
		Person p = new Person()
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,assigningAuthority:healthEntity1))
		ACKMessage ack = pixManagerService.patientRegistryRecordRevised(patient,p,healthEntity1)
		
		assert TypeCode.IdentifierError == ack.typeCode
		
	}
	
	/**
	 * Testea la creacion forzada de un paciente aunque este matchee con otros
	 */
	void testCreatePatientEvenMatchWithOthers(){		
		//Creo una persona parecida al paciente que ya existe
		def p = new Person(givenName: new PersonName(firstName:"Martín Gonzalo", lastName:"Varnech",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:assingingAuthorityArgentina))

		
		ACKMessage ack = pixManagerService.patientRegistryRecordAddedWithoutMatching(p,healthEntity4,"F123456")
		//Verifico ACK
		assertEquals(TypeCode.SuccededCreation,ack.typeCode)
		assertEquals(i18nMessage("pixmanager.ackmessage.creation.succeded"),ack.text)
		
		def patients = Patient.list()
		//2 pacientes. El que ya existia mas el nuevo
		assertEquals(2,patients.size())

	} 
	
	/**
	 * Teste el corrcto matcheo de una persona con el paciente que existe
	 */
	void testMatchedPatients(){
		//Creo una persona parecida al paciente que ya existe
		def p = new Person(givenName: new PersonName(firstName:"Martín Gonzalo", lastName:"Varnech",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850139",assigningAuthority:assingingAuthorityArgentina))
		
		List<Patient> patients = pixManagerService.getAllPossibleMatchedPatients(p)
		
		assert patients.size() == 1
		
		Patient matchedPatient = patients.get(0)
		
		assert patient.uniqueId == matchedPatient.uniqueId
		assert matchedPatient.identityDocument().number == "32850137" 
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