package com.janpix.rup.empi

class Address {
	String street
	String number
	
	String floor
	String depart
	
	String zipCode
	String town
	
	Long latitude
	Long longitude
	
	static belongsTo = Person
	
    static constraints = {
		zipCode(nullable:true,blank:true)
		town(nullable:true,blank:true)
		floor(nullable:true,blank:true)
		depart(nullable:true,blank:true)
		latitude(nullable:true)
		longitude(nullable:true)
    }
	
	
}
