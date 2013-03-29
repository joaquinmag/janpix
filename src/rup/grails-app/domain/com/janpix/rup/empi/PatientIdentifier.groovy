package com.janpix.rup.empi

class PatientIdentifier {
	
	String mainId

	static belongsTo = [patient:Patient]
	
    static constraints = {
		mainId(nullable:false,unique:true)
    }
	
	/**
 	 * Compara 2 identificadores
 	 */
 	boolean equals(PatientIdentifier other){
 		if(this.mainId == other.mainId){
 			return true
 		}
 		return false
 	}
}
