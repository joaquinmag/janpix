package com.janpix.rup.empi

class Identifier {
	
	/*enum TypeIdentifier{
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
	
	TypeIdentifier type*/
	//Basado en HL7 Version 2.5, Chapter 2A, Section 2A.14.5
	static final String TYPE_IDENTIFIER_BC 	= 'BC'
	static final String TYPE_IDENTIFIER_BR 	= 'BR'
	static final String TYPE_IDENTIFIER_DL 	= 'DL'
	static final String TYPE_IDENTIFIER_PI 	= 'PI'
	static final String TYPE_IDENTIFIER_PPN 	= 'PPN'
	static final String TYPE_IDENTIFIER_SS 	= 'SS'
	
	static final String TYPE_IDENTIFIER_DNI 	= 'DNI'
	static final String TYPE_IDENTIFIER_LC 	= 'LC'
	static final String TYPE_IDENTIFIER_LE 	= 'LE'
	
	String type
	String number
	AssigningAuthority assigningAuthority 
	
	static belongsTo = [person:Person]
	
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
