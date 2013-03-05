package com.janpix.rup.empi

class Name {
	String firstName
	String lastName
	
	static belongsTo = [person:Person]
	
    static constraints = {
    }
	
	String toString(){
		return lastName+", "+firstName
	}
}
