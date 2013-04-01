package com.janpix.rup.empi

class Patient extends Person {
	
	PatientIdentifier uniqueId 
	def uuidGenerator

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
		//TODO ver si tengo que comparar algo mas
		if(this.uniqueId == other.uniqueId){
			return true
		}
		return false
	}
	
	/**
	 * Agrega un identificador al paciente
	 * @param identifier
	 * @return
	 */
	Boolean addIdentifier(Identifier identifier){
		
	}
	
}
