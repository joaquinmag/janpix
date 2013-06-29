package com.janpix.rup.empi

class PersonName {
	String firstName
	String lastName
	
	static belongsTo = [Person]
	
    static constraints = {
		firstName(nullable:false)
		lastName(nullable:false)
    }
	
	String toString(){
		return lastName+", "+firstName
	}
}
