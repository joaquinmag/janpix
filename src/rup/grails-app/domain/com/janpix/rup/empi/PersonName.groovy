package com.janpix.rup.empi

import com.janpix.rup.infrastructure.Mapper
import com.janpix.rup.infrastructure.dto.PersonNameDTO

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
	
	boolean equals(other){
		return (this.firstName == other.firstName && this.lastName == other.lastName)	
	}
	
	/**
	 * Convierte la clase de dominio en su DTO
	 * @param mapper
	 * @return
	 */
	PersonNameDTO convert(Mapper mapper){
		return mapper.convert(this)
	}
}
