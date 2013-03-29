package com.janpix.rup.empi

class Patient extends Person {
	
	PatientIdentifier uniqueId 

    static constraints = {
		uniqueId(nullable:false)
    }
	
	/**
	 * Antes de grabar genero el UniqueId del paciente
	 * @return
	 */
	def beforeValidate(){
		//TODO ver como genero los UUID y quien los genera
		if(!uniqueId){
			def random = 5
			uniqueId = new PatientIdentifier(mainId:random)
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
