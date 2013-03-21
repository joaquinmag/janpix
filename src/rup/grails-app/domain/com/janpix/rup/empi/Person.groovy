package com.janpix.rup.empi

import java.util.Set;

class Person {
	//Basado en HL7 User definition table
	enum Sex{
		FEMALE('F'),MALE('M'),OTHER('O'),
		UNKNOWN('U'),AMBIGUOS('A'),NOT_APPLICABLE('N')
		private final String value;
		public String value(){return value;}
	}
	
	enum MaritalStatus{
		MARRIED('M'),SINGLE('S'),WIDOWED('W'),
		private final String value;
		public String value(){return value;}
	}
	
	PersonName givenName
	ExtendedDate birthdate
	Sex administrativeSex
	MaritalStatus maritalStatus
	City birthplace
	Boolean multipleBirthIndicator 
	ExtendedDate deathDate
	String tribalCitizenship //Tribu
	
	Date lastUpdated
	Date dateCreated
		
	List<Address> addresses	= []
	List<PhoneNumber> phoneNumbers = []
	Set<Identifier> identifiers = []
	
	static hasMany = [
			identifiers:Identifier,
			addresses:Address,
			phoneNumbers:PhoneNumber
		]
	
    static constraints = {
		givenName(nullable:false)
		administrativeSex(nullable:false)
		birthdate(nullable:false)
    }
	
	String toString(){
		return "${givenName}-${document}-${birthdate}"
	}
	
	/**
	 * Agrega un identificador a la persona
	 * @param id
	 * @return
	 */
	Boolean addIdentifier(Identifier id){
		
	}
}
