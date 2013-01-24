package com.janpix.rup.empi

class Address {
	String street
	String number
	
	String zipCode
	String town
	
	Long latitude
	Long longitude
	
	static belognsTo = Person
	
    static constraints = {
		zipCode(nullable:true)
		town(nullable:true)
		latitude(nullable:true)
		longitude(nullable:true)
    }
	
	
}
