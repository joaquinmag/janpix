/**
 * Test de integracion que testea el servicio de DemographicPatient
 * @author martin
 *
 */

package com.janpix.rup.empi
import groovy.util.GroovyTestCase;


import com.janpix.rup.exceptions.*
 
class DemographicPersonServiceTests extends GroovyTestCase {
	
	def demographicPersonService
	def p1
	def p2
	def p3
	def p4
	def p5
	def city1
	def city2
	def city3
	def city4
	def city5
	
	
	void setUp(){
		super.setUp()
		//Inicializo servicios
		demographicPersonService = new DemographicPersonService()
		demographicPersonService.grailsApplication =  new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
		demographicPersonService.identityComparatorService = new IdentityComparatorService()
		demographicPersonService.identityComparatorService.grailsApplication =  new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
				
		//Creo algunas ciudades
		createCities()
		
		def assingingAuthorityArgentina = new EmitterCountry(name:"Argentina")
		assingingAuthorityArgentina.save(flush:true,failOnError:true)
		
		//Creo las personas
		p1 = new Person(givenName: new PersonName(firstName:"Martín", lastName:"Barnech",motherLastName:"Mannino"),
					birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
					administrativeSex:Person.TYPE_SEX_MALE,
					birthplace:city1,
					)
		p1.addToIdentifiers(new Identifier(type:'DNI',number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		p1.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",neighborhood:"Luján",city:city1))
		p1.save(flush:true,failOnError:true)
		
		//p1 con error de tipeo en el nombre, la direccion y fecha de nacimiento (el dia difiere)
		p2 = new Person(givenName: new PersonName(firstName:"Martin Gonzalo", lastName:"Barneche",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-06" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p2.addToIdentifiers(new Identifier(type:'DNI',number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		p2.addToAddresses(new Address(street:"Constitucion",number:"2203",zipCode:"6700",neighborhood:"Luján",city:city1))
		p2.save(flush:true,failOnError:true)
		
		
		//p1 con error en fecha nacimiento (difiere el dia), diferente ciudad,  otra direccion y  error tipeo apellido madre
		p3 = new Person(givenName: new PersonName(firstName:"Martín", lastName:"Barnech",motherLastName:"Manino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-06" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city3,
			)
		p3.addToIdentifiers(new Identifier(type:'DNI',number:"32850137",assigningAuthority:assingingAuthorityArgentina))
		p3.addToAddresses(new Address(street:"Rosario",number:"130",zipCode:"7700",neighborhood:"Caballito",city:city3))
		p3.save(flush:true,failOnError:true)
		
		//p1 con diferente numero de documento
		p4 = new Person(givenName: new PersonName(firstName:"Martín", lastName:"Barnech",motherLastName:"Mannino"),
			birthdate: new ExtendedDate(precission:ExtendedDate.TYPE_PRECISSION_DAY,date:Date.parse( "yyyy-M-d", "1987-01-16" )),
			administrativeSex:Person.TYPE_SEX_MALE,
			birthplace:city1,
			)
		p4.addToIdentifiers(new Identifier(type:'DNI',number:"33850137",assigningAuthority:assingingAuthorityArgentina))
		p4.addToAddresses(new Address(street:"Constitución",number:"2213",zipCode:"6700",neighborhood:"Luján",city:city1))
		p4.save(flush:true,failOnError:true)
		/*
		p4	= new Person(givenName: new Name(firstName:"Joaquin Ignacio", lastName:"Magneres"),
				birthdate: Date.parse( "yyyy-M-d", "1987-05-01" ),
				document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32900250"),
				address: new Address(street:"Zapata",number:"346",floor:"5",depart:"A",town:"Belgrano"),
				administrativeSex:Person.TYPE_SEX_MALE,
				livingplace:city1,
				birthplace:city1,
				motherName: new Name(firstName:"Lucia", lastName:"Fontela"),
				fatherName: new Name(firstName:"Roque", lastName:"Margenes"),
			)
		p4.save(flush:true,failOnError:true)
		
		p5	= new Person(givenName: new Name(firstName:"Joaquin", lastName:"Megnere"),
				birthdate: Date.parse( "yyyy-M-d", "1987-01-05" ),
				document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32.900.250"),
				address: new Address(street:"Zabala",number:"336"),
				administrativeSex:Person.TYPE_SEX_MALE,
				livingplace:city2,
				birthplace:city4,
				motherName: new Name(firstName:"Lucia", lastName:"Fontela"),
				fatherName: new Name(firstName:"Roque", lastName:"Magneres"),
		)
		p5.save(flush:true,failOnError:true)*/
	}
	
	
	/**
	 * Testea la correcta construccion de una identidad a partir de una persona
	 */
	void testBuildIdentity(){
		def identity = Identity.buildFromPerson(p1)
		assertEquals("Martín Barnech Mannino",identity.name)
		assertEquals(Date.parse("yyyy-M-d","1987-01-16"),identity.birthdate.date)
		assertEquals(Person.TYPE_SEX_MALE,identity.sex)
		assertEquals("Argentina,Buenos Aires,Luján",identity.livingplace)
		assertEquals("Constitución 2213",identity.address)
		assertEquals("DNI32850137Argentina",identity.document)
	}
	
	/**
	 * Testea que el paciente1 matchee con los paciente 2 ya que difieren pocos datos
	 * que podrian ser productos de errores de tipeo
	 */
	void testMatchP1WithP2(){
		def matchedPersons = demographicPersonService.matchPerson(p1)
		
		//Matcheo
		assertTrue("Entre los matcheados NO se encuentra el paciente 2",matchedPersons.contains(p2))
	}
	
	/**
	 * Testea que el paciente1 sea un posible matcheo del paciente3 
	 * ya que aunque conicidan numero de documento difieren muchos datos
	 */
	void testPossibleMatchP1WithP3(){
		def matchedPersons = demographicPersonService.matchPerson(p1)
		
		//Posible matcheo
		assertTrue("Entre los posibles matcheados NO se encuentra el paciente 3",demographicPersonService.lastPossibleMatchedPersons().contains(p3))
	}
	
	
	/**
	 * Testea que el paciente1 sea un posible matcheo del paciente4 
	 * ya que aunque conicidan todos los datos los numeros de documento son diferentes
	 */
	void testPossibleMatchP1WithP4(){
		def matchedPersons = demographicPersonService.matchPerson(p1)
		
		//Posible matcheo
		assertTrue("Entre los posibles matcheados NO se encuentra el paciente 4",demographicPersonService.lastPossibleMatchedPersons().contains(p4))
	}
	
	/**
	 * Testea que matcheen los paciente 4 y 5
	 */
	void testMatchP4P5(){
		fail "implement me!"
	}
	
	/**
	 * Testea que el paciente1 NO matchee con los paciente 4 y 5
	 */
	void testDontMatchP1WithP4P5(){
		fail "implement me!"
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