package com.janpix.rup.empi

class Address {
	String street
	String number
	
	String floor
	String department
	
	String zipCode
	String neighborhood
	
	City city
	
	Long latitude
	Long longitude
	
	Date dateCreated
	
	static belongsTo = Person
	
    static constraints = {
		city(nullable:false)
		zipCode(nullable:true,blank:true)
		neighborhood(nullable:true,blank:true)
		floor(nullable:true,blank:true)
		department(nullable:true,blank:true)
		latitude(nullable:true)
		longitude(nullable:true)
    }
	
	
}
