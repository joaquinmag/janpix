package com.janpix.rup.empi

import com.janpix.rup.infrastructure.Mapper
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO

class AssigningAuthority {
	String name
	String oid
	
    static constraints = {
		oid(unique: true, nullable: false)
    }
	
	AssigningAuthority(String oid, String name) {
		this.oid = oid
		this.name = name
	}
	
	/**
	 * Compara 2 autoridades de asignacion
	 */
	boolean equals(other){
		return (this.oid == other?.oid)
	}
	
	String toString(){
		return name
	}
	
	/**
	 * Convierte la clase de dominio en su DTO
	 * @param mapper
	 * @return
	 */
	AssigningAuthorityDTO convert(Mapper mapper){
		return mapper.convert(this)
	}
}
