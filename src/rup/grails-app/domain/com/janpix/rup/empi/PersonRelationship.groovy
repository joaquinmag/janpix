package com.janpix.rup.empi

class PersonRelationship {
	String type //Hermano, madre, marido, hijo, etc
	String status //Divorciado, Terminada
	Person relatedPerson
	
	static belongsTo = [person:Person]
	
	static constraints = {
	}
}
