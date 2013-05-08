package com.janpix.rup.empi

class AssigningAuthority {
	String name
	String oid
	
    static constraints = {
		oid(unique: true, nullable: false)
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
	
	static AssigningAuthority rupAuthority(){
		//FIXME cargar el RUP en el Bootstrap y buscar por nombre/id/loquesea
		return AuthorityRUP.read(0)
	}
}
