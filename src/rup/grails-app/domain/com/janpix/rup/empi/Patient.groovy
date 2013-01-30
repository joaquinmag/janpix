package com.janpix.rup.empi

import java.util.Date;

class Patient extends Person {
	String uniqueId //TODO definir si uso otro ademas del que otorga grails
	Set<Identifier> identifiers = []
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [identifiers:Identifier]
	
    static constraints = {
    }
	
	/**
	 * Antes de grabar genero el UniqueId del paciente
	 * @return
	 */
	def beforeValidate(){
		//TODO ver como genero los UUID
		if(!uniqueId){
			def random = 5
			uniqueId = "UUID-"+random
		}
	}
	
	/**
	 * Compara 2 pacientes
	 */
	boolean equals(other){
		//TODO ver si tengo que comparar algo mas
		if(this.uniqueId == other.uniqueId){
			return true
		}
		return false
	}
}
