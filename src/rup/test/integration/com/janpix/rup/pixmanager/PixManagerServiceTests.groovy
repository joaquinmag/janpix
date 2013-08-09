/**
 * Test de integracion que testea el servicio del PixManager
 * @author martin
 *
 */

package com.janpix.rup.pixmanager

import com.janpix.rup.empi.*
import com.janpix.rup.infrastructure.dto.AddressDTO
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.CityDTO
import com.janpix.rup.infrastructure.dto.ExtendedDateDTO
import com.janpix.rup.infrastructure.dto.IdentifierDTO
import com.janpix.rup.infrastructure.dto.PatientDTO
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.infrastructure.dto.PersonNameDTO
import com.janpix.rup.services.contracts.ACKMessage
import com.janpix.rup.services.contracts.ACKMessage.TypeCode

 
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
	def assigningAuthorityService
	
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
		healthEntity1 = new HealthEntity("2.16.32.1.255.1", "Entidad Sanitaria 1")
		healthEntity1.save(flush:true,failOnError:true)
		healthEntity2 = new HealthEntity("2.16.32.1.255.2", "Entidad Sanitaria 2")
		healthEntity2.save(flush:true,failOnError:true)
		healthEntity3 = new HealthEntity("2.16.32.1.255.3", "Entidad Sanitaria 3")
		healthEntity3.save(flush:true,failOnError:true)
		healthEntity4 = new HealthEntity("2.16.32.1.255.4", "Entidad Sanitaria 4")
		healthEntity4.save(flush:true,failOnError:true)
		rup = assigningAuthorityService.rupAuthority()
		
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
		patient.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"A123",assigningAuthority:healthEntity1))
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"B123",assigningAuthority:healthEntity2))
		patient.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:"C123",assigningAuthority:healthEntity3))
		patient.save(flush:true,failOnError:true)
		
		person = new Person(givenName: new PersonName(firstName:"Joaquin Ignacio", lastName:"Magneres"),
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
		ACKMessage ack = pixManagerService.patientRegistryGetIdentifiersQuery("C123",this.buildHealthEntityDTO("3"),[this.buildRupAuthorityDTO()])
		assert ack.typeCode == TypeCode.SuccededQuery
		def identifiers = ack.patient.identifiers
		assertEquals(1,identifiers.size())
		
		IdentifierDTO cuis = identifiers.first()
		assertEquals(patient.uniqueId.toString(),cuis.number)
		assertEquals(this.buildRupAuthorityDTO(),cuis.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,cuis.type)
	}
	
	/**
	 * Testea que no devuelva ningun identificador porque no existe paciente en los dominios pasados
	 */
	void testGetIdentifiersReturnEmpty(){
		ACKMessage ack = pixManagerService.patientRegistryGetIdentifiersQuery("C123",this.buildHealthEntityDTO("3"),[new AssigningAuthorityDTO("2.16.32.1.255.255", "No existe")])
		def identifiers = ack.patient.identifiers
		assertEquals(0,identifiers.size())
	}
	
	/**
	 * Testea que devuelva todos los identificadores de un paciente ya que no se le pasan dominios
	 */
	void testGetAllIdentifiers(){
		ACKMessage ack = pixManagerService.patientRegistryGetIdentifiersQuery("C123",this.buildHealthEntityDTO("3"))
		Set<IdentifierDTO> identifiers = ack.patient.identifiers
		assertEquals(3,identifiers.size())
		
		IdentifierDTO cuis = identifiers.find{it.assigningAuthority == this.buildRupAuthorityDTO()}
		assertEquals(patient.uniqueId.toString(),cuis.number)
		assertEquals(this.buildRupAuthorityDTO(),cuis.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,cuis.type)
		
		IdentifierDTO idh1 = identifiers.find{it.assigningAuthority == this.buildHealthEntityDTO("1")}
		assertEquals("A123",idh1.number)
		assertEquals(this.buildHealthEntityDTO("1"),idh1.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,idh1.type)
		
		IdentifierDTO idh2 = identifiers.find{it.assigningAuthority == this.buildHealthEntityDTO("2")}
		assertEquals("B123",idh2.number)
		assertEquals(this.buildHealthEntityDTO("2"),idh2.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,idh2.type)
	}
	
	/**
	 * Testea que se devuelvan los identificadores de los dominios pasados
	 */
	void testGetDomainIdentifiers(){
		ACKMessage ack = pixManagerService.patientRegistryGetIdentifiersQuery("C123",this.buildHealthEntityDTO("3"),[this.buildHealthEntityDTO("1"),this.buildHealthEntityDTO("2")])
		def identifiers = ack.patient.identifiers
		assertEquals(2,identifiers.size())
		
		IdentifierDTO idh1 = identifiers.find{it.assigningAuthority == this.buildHealthEntityDTO("1")}
		assertEquals("A123",idh1.number)
		assertEquals(this.buildHealthEntityDTO("1"),idh1.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,idh1.type)
		
		IdentifierDTO idh2 = identifiers.find{it.assigningAuthority == this.buildHealthEntityDTO("2")}
		assertEquals("B123",idh2.number)
		assertEquals(this.buildHealthEntityDTO("2"),idh2.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,idh2.type)
	}

	/**
	 * Llama al servicio para crear un paciente que NO existe
	 */
	void testCreateNotExistsPatient(){	
		CityDTO cityDTO = new CityDTO(nameCity:"C.A.B.A",nameProvince:"C.A.B.A",nameCountry:"Argentina")	
		PersonDTO personDTO = new PersonDTO(name: new PersonNameDTO(firstName:"Joaquin Ignacio", lastName:"Magneres"),
			birthdate: new ExtendedDateDTO(date:"1987-05-01",precission:"Day"),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:cityDTO,
			)
		personDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32900250",assigningAuthority:new AssigningAuthorityDTO("2.16.32","Argentina")))
		personDTO.address.add(new AddressDTO(street:"Zapata",number:"346",floor:"5",department:"A",city:cityDTO))
		AssigningAuthorityDTO healthEntity1DTO = new AssigningAuthorityDTO("2.16.32.1.255.1", "Entidad Sanitaria 1")
		
		
		ACKMessage ack = pixManagerService.patientRegistryRecordAdded(personDTO,healthEntity1DTO,"A123456")
		
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
		CityDTO cityDTO = new CityDTO(nameCity:"Luján",nameProvince:"Buenos Aires",nameCountry:"Argentina")
		PersonDTO personDTO = new PersonDTO(name: new PersonNameDTO(firstName:"Martín", lastName:"Barnech"),
			birthdate: new ExtendedDateDTO(date:"1987-01-16",precission:"Day"),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:cityDTO,
			)
		personDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:new AssigningAuthorityDTO("2.16.32","Argentina")))
		personDTO.address.add(new AddressDTO(street:"Constitución",number:"2213",zipCode:"6700",city:cityDTO))
		
		AssigningAuthorityDTO healthEntity4DTO = new AssigningAuthorityDTO("2.16.32.1.255.4", "Entidad Sanitaria 4")

		ACKMessage ack = pixManagerService.patientRegistryRecordAdded(personDTO,healthEntity4DTO,"F123456")
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
	 * FIXME!. Me parece que no esta tirando matcheo alto
	 */
	void testFailCreatePatientBecauseMuchMatched(){		
		//Creo una persona parecida al paciente que ya existe
		CityDTO cityDTO = new CityDTO(nameCity:"Luján",nameProvince:"Buenos Aires",nameCountry:"Argentina")
		PersonDTO personDTO = new PersonDTO(name: new PersonNameDTO(firstName:"Martín Gonzalo", lastName:"Varnech"),
			birthdate: new ExtendedDateDTO(date:"1987-01-16",precission:"Day"),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:cityDTO,
			)
		personDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850139",assigningAuthority:new AssigningAuthorityDTO("2.16.32","Argentina")))
		personDTO.address.add(new AddressDTO(street:"Constitución",number:"2213",zipCode:"6700",city:cityDTO))
		
		AssigningAuthorityDTO healthEntity4DTO = new AssigningAuthorityDTO("2.16.32.1.255.4", "Entidad Sanitaria 4")
		
		ACKMessage ack = pixManagerService.patientRegistryRecordAdded(personDTO,healthEntity4DTO,"F123456")
		//Verifico ACK
		assertEquals(TypeCode.PossibleMatchingPatientsError,ack.typeCode) //FIXME hay un problema con los DNIs, no logro que llegue a este error...
		assertEquals(i18nMessage("pixmanager.ackmessage.possiblematching.error"),ack.text)
		
		def patients = Patient.list()
		//1 paciente. El que ya existia
		assertEquals(1,patients.size())

	}
	
	/**
	 * Testea que se actualice correctamente la informacion demografica y se agreguen los identificadores nuevos
	 */
	void testUpdateDemographicDataAndIdentifiers(){		
		CityDTO cityDTO = new CityDTO(nameCity:"C.A.B.A",nameProvince:"C.A.B.A",nameCountry:"Argentina")
		PatientDTO patientDTO = new PatientDTO(name: new PersonNameDTO(firstName:"Joaquin Ignacio", lastName:"Magneres"),
			birthdate: new ExtendedDateDTO(date:"1987-05-01",precission:"Day"),
			administrativeSex:Person.TYPE_SEX_AMBIGUOS,
			birthplace:cityDTO,
			)
		patientDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_DNI,number:"33850139",assigningAuthority:new AssigningAuthorityDTO("2.16.32","Argentina")))
		patientDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_PI,number:"YMCA123",assigningAuthority:this.buildHealthEntityDTO("4")))
		patientDTO.address.add(new AddressDTO(street:"Zapata",number:"346",floor:"5",department:"A",city:cityDTO))
		patientDTO.uniqueId = this.patient.uniqueId.mainId
		
	
		ACKMessage ack = pixManagerService.patientRegistryRecordRevised(patientDTO,this.buildHealthEntityDTO("4"))
		
		//ACK
		assert TypeCode.SuccededUpdated == ack.typeCode
		assert i18nMessage("pixmanager.ackmessage.updated.succeded") == ack.text
		
		//Patient
		assert "${patient.givenName}" 				== "Magneres, Joaquin Ignacio"
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
		PatientDTO patientDTO = new PatientDTO()
		patientDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_PI,number:"Z321",assigningAuthority:this.buildHealthEntityDTO("1")))
		patientDTO.uniqueId = this.patient.uniqueId.mainId
		
		ACKMessage ack = pixManagerService.patientRegistryRecordRevised(patientDTO,this.buildHealthEntityDTO("1"))
		
		assert TypeCode.SuccededUpdated == ack.typeCode
		assert i18nMessage("pixmanager.ackmessage.updated.succeded") == ack.text
		
		Patient updatedPatient = Patient.findById(patient.id); 
		assert updatedPatient.identifiers.size() == 4 //Los 4 que siempre tuvo (DNI + 3 entidades sanitarias)
		Identifier updatedIdentifier = updatedPatient.identifiers.find {
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
		CityDTO city2DTO = new CityDTO(nameCity:"C.A.B.A",nameProvince:"Bs. As.",nameCountry:"Argentina")
		PatientDTO p = new PatientDTO(name: new PersonNameDTO(firstName:"Joaquin Ignacio", lastName:"Magneres"),
			birthdate: new ExtendedDateDTO(precission:"Day",date:"1987-05-01" ),
			administrativeSex:Person.TYPE_SEX_AMBIGUOS,
			birthplace:city2DTO
			)
		p.address.add(new AddressDTO(street:"Zapata",number:"346",floor:"5",zipCode:"6700",city:city2DTO))
		p.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_DNI,number:"33850139",assigningAuthority:new AssigningAuthorityDTO("2.16.32","Argentina")))
		p.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_PI,number:"YMCA123",assigningAuthority:this.buildHealthEntityDTO("4")))
		
		ACKMessage ack = pixManagerService.patientRegistryRecordRevised(p,this.buildHealthEntityDTO("4"))
		
		//ACK
		assert TypeCode.DontExistingPatientError == ack.typeCode
		
	}
	
	/**
	 * Testea que falle la actualizacion de un paciente porque el identificador agregado no es valido
	 */
	void testFailUpdateBecauseNotValidIdentifier(){
		//Mando a actualizar el identificador
		PatientDTO patientDTO = new PatientDTO()
		patientDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_PI,assigningAuthority:this.buildHealthEntityDTO("1")))
		patientDTO.uniqueId = this.patient.uniqueId.mainId
		ACKMessage ack = pixManagerService.patientRegistryRecordRevised(patientDTO,this.buildHealthEntityDTO("1"))
		
		assert TypeCode.IdentifierError == ack.typeCode
	}
	
	/**
	 * Testea la creacion forzada de un paciente aunque este matchee con otros
	 * FIXME Falla porque al tener diferente Documento y Nombre no entra ni como posible matcheo
	 */
	void testCreatePatientEvenMatchWithOthers(){		
		//Creo una persona parecida al paciente que ya existe
		CityDTO cityDTO = new CityDTO(nameCity:"Luján",nameProvince:"Buenos Aires",nameCountry:"Argentina")
		PersonDTO p = new PersonDTO(name: new PersonNameDTO(firstName:"Martín Gonzalo", lastName:"Varnech"),
			birthdate: new ExtendedDateDTO(precission:"Day",date:"1987-01-16" ),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:cityDTO,
			)
		p.address.add(new AddressDTO(street:"Constitución",number:"2213",zipCode:"6700",city:cityDTO))
		p.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:new AssigningAuthorityDTO("2.16.32","Argentina")))

		
		ACKMessage ack = pixManagerService.patientRegistryRecordAddedWithoutMatching(p,this.buildHealthEntityDTO("4"),"F123456")
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
		def p = new Person(givenName: new PersonName(firstName:"Martín Gonzalo", lastName:"Varnech"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",city:city1))
		p.addToIdentifiers(new Identifier(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		
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
	
	def private buildPatientDTO(){
		CityDTO cityDTO = new CityDTO(nameCity:"Luján",nameProvince:"Buenos Aires",nameCountry:"Argentina")
		PatientDTO patientDTO  = new PatientDTO(name: new PersonNameDTO(firstName:"Martín", lastName:"Barnech"),
			birthdate: new ExtendedDateDTO(date:"1987-01-06",precission:"Day"),
			administrativeSex:this.patient.administrativeSex,
			birthplace:cityDTO,
			)
		patientDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_DNI,number:"32850137",assigningAuthority:new AssigningAuthorityDTO("2.16.32","Argentina")))
		patientDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_PI,number:"A123",assigningAuthority:buildHealthEntityDTO("1")))
		patientDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_PI,number:"B123",assigningAuthority:buildHealthEntityDTO("2")))
		patientDTO.identifiers.add(new IdentifierDTO(type:Identifier.TYPE_IDENTIFIER_PI,number:"C123",assigningAuthority:buildHealthEntityDTO("3")))
		patientDTO.address.add(new AddressDTO(street:"Constitución",number:"2213",zipCode:"6700",city:cityDTO))
		patientDTO.uniqueId = this.patient.uniqueId.mainId
		
		return patientDTO
	}
	
	def private buildHealthEntityDTO(String number){
		return new AssigningAuthorityDTO("2.16.32.1.255."+number, "Entidad Sanitaria "+number)
	}
	
	def private buildRupAuthorityDTO(){
		AssigningAuthority rup = assigningAuthorityService.rupAuthority()
		AssigningAuthorityDTO rupDTO = new AssigningAuthorityDTO()
		rupDTO.name = rup.name
		rupDTO.oid = rup.oid
		return rupDTO
	}
}