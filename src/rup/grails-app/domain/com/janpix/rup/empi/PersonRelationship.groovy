package com.janpix.rup.empi

class PersonRelationship {
	enum RelationType{
		FATHER_SUN,MOTHER_SUN //TODO Ver todas las posibilidades
	}
	RelationType type
	Person leftPerson
	Person rightPerson
	
	
	
	static constraints = {
		type(nullable:false)
		leftPerson(nullable:false)
		rightPerson(nullable:false)
	}
}
