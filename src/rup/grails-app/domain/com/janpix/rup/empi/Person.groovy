package com.janpix.rup.empi

class Person {
	//Basado en HL7 User definition table
	static OPTIONS_ADMINISTRATIVESEX = [
			"F":"Female",
			"M":"Male",
			"O":"Other",
			"U":"Unknown",
			"A":"Ambiguos",
			"N":"Not Applicable",
			]
	
	Name givenName
	Date birthdate
	String administrativeSex
	
	Address address
	List<PhoneNumber> phoneNumbers
	
	Citizenship citizenship
	City livingplace
	City birthplace
	
	Date deaddate
	
	Name motherName
	Name fatherName
	
	Set<PersonRelationship> relationships
	
	static hasMany = [relationships:PersonRelationship]
	static mappedBy = [relationships:'person']
	
    static constraints = {
    }
}
