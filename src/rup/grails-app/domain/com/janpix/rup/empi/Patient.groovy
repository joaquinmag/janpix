package com.janpix.rup.empi

import com.janpix.rup.infrastructure.Mapper
import com.janpix.rup.infrastructure.dto.PatientDTO

class Patient extends Person {
	def uuidGenerator
	
	PatientIdentifier uniqueId

    static constraints = {
		uniqueId(nullable:false)
    }
	
	/**
	 * Construye un paciente a partir de una persona
	 * @param Person p
	 */
	Patient(Person p){
		this.properties = p.properties
	}
	
	/**
	 * Antes de grabar genero el UniqueId del paciente
	 * @return
	 */
	def beforeValidate() {
		if(!uniqueId){
			def uuid = uuidGenerator()
			uniqueId = new PatientIdentifier(mainId:uuid)
		}
	}
	
	/**
	 * Compara 2 pacientes
	 */
	boolean equals(Patient other){
		if(this.uniqueId == other.uniqueId){
			return true
		}
		return false
	}
	
	/**
	 * Devuelve el identificador de la entidad sanitaria pasada
	 * @return
	 */
	Identifier healthEntityIdentifier(HealthEntity healthEntity){
		return this.identifiers.find{it.assigningAuthority == healthEntity}
	}
	
	
	/**
	 * Convierte la clase de dominio en su DTO
	 * @param mapper
	 * @return
	 */
	PatientDTO convert(Mapper mapper){
		return mapper.convert(this)
	}
	
}
