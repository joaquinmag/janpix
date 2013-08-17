package com.janpix.rup.empi

import com.janpix.rup.infrastructure.Mapper
import com.janpix.rup.infrastructure.dto.PhoneNumberDTO

class PhoneNumber 
{
	static final String TYPE_CELL = 'CELL'
	static final String TYPE_HOME = 'HOME'
	static final String TYPE_WORK = 'WORK'
	
	String type
	String number
	
	static belongsTo = Person
	
    static constraints = {
		number(nullable:false)
		type(nullable:false)
    }
	
	void update(PhoneNumber other){
		this.number = other.number 
	}
	
	boolean equals(other){
		return (this.type == other.type && this.number == other.number)
	}
	
	/**
	 * Convierte la clase de dominio en su DTO
	 * @param mapper
	 * @return
	 */
	PhoneNumberDTO convert(Mapper mapper){
		return mapper.convert(this)
	}
}
