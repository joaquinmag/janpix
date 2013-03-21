package com.janpix.rup.empi

class Province {
	String name
	Country country
	
	static belongsTo = [country:Country]
	
    static constraints = {
    }
}
