package com.janpix.rup.empi

class Address {
	String unitId
	String street
	String number
	String floor
	String department
	String zipCode
	City city
	Date dateCreated
	
	static belongsTo = Person
	
    static constraints = {
		city(nullable:false)
		zipCode(nullable:true,blank:true)
		floor(nullable:true,blank:true)
		department(nullable:true,blank:true)
		unitId(nullable:true)
    }
	
	boolean equals(other){
		if(	this.street == other.street &&
			this.number == other.number &&
			this.floor	== other.floor &&
			this.zipCode == other.zipCode &&
			this.city == other.city
		)
			return true
			
		return false
	}
	
	String toString(){
		return "${street} ${number} ${floor}${department}, ${city}"
	}
}
