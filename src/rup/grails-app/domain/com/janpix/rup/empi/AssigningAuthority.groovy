package com.janpix.rup.empi

class AssigningAuthority {
	String name
	
    static constraints = {
    }
	
	/**
	 * Compara 2 autoridades de asignacion
	 */
	boolean equals(other){
		//TODO ver que otros datos comparar
		return (this.name == other.name)
	}
	
	String toString(){
		return name
	}
}
