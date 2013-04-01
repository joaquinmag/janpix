package com.janpix.rup.empi

class PersonRelationship {
	String type
	Person leftPerson
	Person rightPerson
	
	//workaround para utilizar enums en la clase de dominio
	RelationType getRelationType() { type ? RelationType.byId(type) : null }
	void setRelationType(RelationType relationType) { type = relationType.id }
 
	static transients = ['type']
 
	static mapping = {
	   type sqlType: 'char(2)'
	}

	static constraints = {
		type inList: RelationType.values()*.id
		leftPerson(nullable:false)
		rightPerson(nullable:false)
	}
}

public enum RelationType {
	FATHER_SUN('FS'),
	MOTHER_SUN('MS') //TODO Ver todas las posibilidades
	
	private RelationType(String id) { this.id = id }
	final String id
 
	static RelationType byId(String id) {
	   values().find { it.id == id }
	}
}