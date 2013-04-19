package com.janpix.rup.empi

class Country {
	String name
	
    static constraints = {
    }
	
	boolean equals(other){
		return (this.name == other.name)
	}
}
