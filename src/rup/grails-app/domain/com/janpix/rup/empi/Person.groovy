package com.janpix.rup.empi

class Person {
	//Basado en HL7 User definition table
	static String TYPE_SEX_FEMALE 			= "Female"
	static String TYPE_SEX_MALE 			= "Male"
	static String TYPE_SEX_OTHER 			= "Other"
	static String TYPE_SEX_UNKNOWN			= "Unknown"
	static String TYPE_SEX_AMBIGUOS			= "Ambiguos"
	static String TYPE_SEX_NOT_APPLICABLE	= "Not Applicable"
	
	
	Name givenName
	Date birthdate
	String administrativeSex
	IdentityDocument document
	
	//TODO agregar estado civil
	
	Address address
	List<PhoneNumber> phoneNumbers
	
	Citizenship citizenship //TODO borrar
	City livingplace
	City birthplace
	
	Date deaddate
	
	Name motherName
	Name fatherName
	
	Set<PersonRelationship> relationships
	
	static hasMany = [relationships:PersonRelationship]
	static mappedBy = [relationships:'person']
	
    static constraints = {
		address(nullable:true)
		deaddate(nullable:true)
		document(nullable:true)//TODO ver si es obligatorio o no
		birthdate(nullable:true)
		motherName(nullable:true)
		fatherName(nullable:true)
		citizenship(nullable:true)
		birthplace(nullable:true)
    }
	
	String toString(){
		return givenName.toString()+"-"+document.toString()+"-"+birthdate
	}
}
