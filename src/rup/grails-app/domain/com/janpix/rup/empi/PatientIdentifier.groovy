package com.janpix.rup.empi


class PatientIdentifer {
	String value 
	static belongsTo = [patient:Patient]
	
	static constraints = {
		value(nullable:false,unique:true)
	}
	
	/**
	 * Compara 2 identificadores
	 */
	boolean equals(other){
		if(this.value == other.value){
			return true
		}
		return false
	}
}
