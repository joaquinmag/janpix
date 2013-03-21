package com.janpix.rup.empi

class Identifier {
	//Basado en HL7 Version 2.5, Chapter 2A, Section 2A.14.5
	enum TypeIdentifier{
		BC("BC"), //Bank card number
		BR("BR"), //Birth Certificate number
		DL("DL"), //Driver License
		PI("PI"), //Patient Internal Identifier
		PPN("PPN"), //Passport number
		SS("SS"), //Social Security number
		
		//Agregados mas alla del estandar
		DNI("DNI"), 
		LC("LC"),
		LE("LE")
		private final String value;
		public String value(){return value;}
	}
	
	TypeIdentifier type
	String number
	AssigningAuthority assigningAuthority 
	
	static belongsTo = [patient:Patient]
	
    static constraints = {
		type(nullable:false)
		number(nullable:false)
		assigningAuthority(nullable:false)
    }
	
	/**
	 * Compara 2 identificadores
	 */
	boolean equals(other){
		return (this.type == other.type && 
				this.number == other.number && 
				this.assigningAuthority == other.assigningAuthority
				)
	}
}
