package com.janpix.rup.empi

class PersonName {
	String firstName
	String lastName
	String motherLastName
	String alias
	
	static belongsTo = [person:Person]
	
    static constraints = {
    }
	
	String toString(){
		return lastName+", "+firstName
	}
}
