package com.janpix.rup.empi

import com.janpix.rup.infrastructure.Mapper
import com.janpix.rup.infrastructure.dto.CityDTO

class City {
	String name
	Province province
	
	static belongsTo = [province:Province]
	
    static constraints = {
    }
	
	boolean equals(other){
		return (this.province == other.province && this.name == other.name)
	}
	
	/**
	 * Convierte la clase de dominio en su DTO
	 * @param mapper
	 * @return
	 */
	CityDTO convert(Mapper mapper){
		return mapper.convert(this)
	}
	
}
