package com.janpix.rup.empi

class Province {
	String name
	Country country
	
	static belongsTo = [country:Country]
	
    static constraints = {
    }
	
	boolean equals(other){
		return (this.country == other.country && this.name == other.name)
	}
	
	String toString(){
		return "${name}"
	}
}
