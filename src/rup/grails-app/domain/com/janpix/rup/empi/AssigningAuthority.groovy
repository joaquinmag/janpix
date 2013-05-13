package com.janpix.rup.empi

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
		//TODO ver que otros datos comparar
		return (this.name == other?.name)
	}
	
	String toString(){
		return name
	}
}
