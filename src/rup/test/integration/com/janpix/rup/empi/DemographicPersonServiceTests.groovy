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
	
	
	void setUp(){
		super.setUp()
		demographicPersonService = new DemographicPersonService()
				
		//Creo algunas nacionalidades
		def citizenship = new Citizenship(name:"Argentino")
		citizenship.save(flush:true,failOnError:true)
		def citizenship1 = new Citizenship(name:"Paraguayo")
		citizenship1.save(flush:true,failOnError:true)
		//Creo algunas ciudades
		def city = new City(country:"Argentina",province:"Buenos Aires",name:"Luján")
		city.save(flush:true,failOnError:true)
		def city1 = new City(country:"Argentina",province:"C.A.B.A",name:"C.A.B.A")
		city1.save(flush:true,failOnError:true)
		def city2 = new City(country:"Argentina",province:"Capital Federal",name:"Capital Federal")
		city2.save(flush:true,failOnError:true)
		def city3 = new City(country:"Argentina",province:"Bs. As.",name:"Lujan")
		city3.save(flush:true,failOnError:true)
		def city4 = new City(country:"Argentina",province:"Bs As",name:"Pergamino")
		city4.save(flush:true,failOnError:true)
		
		//Creo las personas
		p1 = new Person(givenName: new Name(firstName:"Martín", lastName:"Barnech"),
					birthdate: Date.parse( "yyyy-M-d", "1987-01-16" ),
					document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32850137"),
					address: new Address(street:"Constitución",number:"2213",zipCode:"6700",town:"Luján"),
					administrativeSex:"M",
					citizenship:citizenship,
					livingplace:city,
					birthplace:city,
					motherName: new Name(firstName:"Maria Olga Lucia", lastName:"Mannino"),
					fatherName: new Name(firstName:"Pablo Juan", lastName:"Barnech"),
					)
		p1.save(flush:true,failOnError:true)
		//p1 con error en el nombre y en la direccion y el DNI
		p2 = new Person(givenName: new Name(firstName:"Martin Gonzalo", lastName:"Barneche"),
					birthdate: Date.parse( "yyyy-M-d", "1987-01-16" ),
					document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32.850.137"),
					address: new Address(street:"Constitucion",number:"2203",zipCode:"6700",town:"Luján"),
					administrativeSex:"M",
					citizenship:citizenship,
					livingplace:city,
					birthplace:city,
					motherName: new Name(firstName:"Maria Olga", lastName:"Mannino"),
					fatherName: new Name(firstName:"Pablo Juan", lastName:"Barnech"),
					)
		p2.save(flush:true,failOnError:true)
		//p3 con error en fecha nacimiento, doc y ciudades
		p3	= new Person(givenName: new Name(firstName:"Martín", lastName:"Barnech"),
					birthdate: Date.parse( "yyyy-M-d", "1987-02-15" ),
					document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32.850.135"),
					address: new Address(street:"Constitución",number:"2213",zipCode:"6700",town:"Luján"),
					administrativeSex:"M",
					citizenship:citizenship,
					livingplace:city3,
					birthplace:city3,
					motherName: new Name(firstName:"Maria Olga", lastName:"Manino"),
					fatherName: new Name(firstName:"Juan Pablo", lastName:"Barneche"),
			)
		p3.save(flush:true,failOnError:true)
		
		p4	= new Person(givenName: new Name(firstName:"Joaquin Ignacio", lastName:"Magneres"),
				birthdate: Date.parse( "yyyy-M-d", "1987-05-01" ),
				document: new IdentityDocument(type:IdentityDocument.TYPE_DNI,number:"32900250"),
				address: new Address(street:"Zapata",number:"346",floor:"5",depart:"A",town:"Belgrano"),
				administrativeSex:"M",
				citizenship:citizenship,
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
				administrativeSex:"M",
				citizenship:citizenship,
				livingplace:city2,
				birthplace:city4,
				motherName: new Name(firstName:"Lucia", lastName:"Fontela"),
				fatherName: new Name(firstName:"Roque", lastName:"Magneres"),
		)
		p5.save(flush:true,failOnError:true)
	}
	
	/**
	 * Testea que el paciente1 matchee con los paciente 1 y 2
	 */
	void testMatchP1WithP2P3(){
		def matchedPersons = demographicPersonService.matchPerson(p1)
		
		assertTrue(matchedPersons.contains(p1))
		assertTrue(matchedPersons.contains(p2))
	}
	
	
	/**
	 * Testea que matcheen los paciente 2 y 3
	 */
	void testMatcP2P3(){
		fail "implement me!"
	}
	
	/**
	 * Testea que matcheen los paciente 4 y 5
	 */
	void testMatcP4P5(){
		fail "implement me!"
	}
	
	/**
	 * Testea que el paciente1 NO matchee con los paciente 4 y 5
	 */
	void testDontMatchP1WithP4P5(){
		fail "implement me!"
	}
	
	
	
}