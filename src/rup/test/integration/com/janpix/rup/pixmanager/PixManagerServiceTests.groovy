/**
 * Test de integracion que testea el servicio de EMPI
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
 
class PixManagerServiceTests extends GroovyTestCase {
	
	//Servicios utilizados
	def EMPIService
	def demographicPersonService
	def grailsApplication
	def identityComparatorService
	def uuidGenerator
	def pixManagerService
	
	Patient patient
	def healthEntity1,healthEntity2,healthEntity3,rup
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
		
		//Creo 2 entidades sanitarias y el RUP
		healthEntity1 = new HealthEntity(name:"Entidad Sanitaria 1")
		healthEntity1.save(flush:true,failOnError:true)
		healthEntity2 = new HealthEntity(name:"Entidad Sanitaria 2")
		healthEntity2.save(flush:true,failOnError:true)
		healthEntity3 = new HealthEntity(name:"Entidad Sanitaria 3")
		healthEntity3.save(flush:true,failOnError:true)
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
		

	}
	
	
	/**
	 * Testea la correcta obtencion del CUIS de un paciente
	 */
	void testGetCUIS(){
		def identifiers = pixManagerService.patientRegistryGetIdentifiersQuery("C123",healthEntity3,[AssigningAuthority.rupAuthority()])
		assertEquals(1,identifiers.size())
		
		Identifier cuis = identifiers.get(0)
		assertEquals(patient.uniqueId.toString(),cuis.number)
		assertEquals(AssigningAuthority.rupAuthority(),cuis.assigningAuthority)
		assertEquals(Identifier.TYPE_IDENTIFIER_PI,cuis.type)
	}
	
	/**
	 * Testea que no devuelva ningun identificador porque no existe paciente en los dominios pasados
	 */
	void testGetIdentifiersReturnEmpty(){
		def identifiers = pixManagerService.patientRegistryGetIdentifiersQuery("C123",healthEntity3,[new HealthEntity(name:"No existe")])
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