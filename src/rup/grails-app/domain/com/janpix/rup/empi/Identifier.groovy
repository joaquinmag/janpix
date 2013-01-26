package com.janpix.rup.empi

class Identifier {
	//Basado en HL7 Version 2.5, Chapter 2A, Section 2A.14.5
	static TYPE_BC = "BC" //Bank card number
	static TYPE_BR = "BR" //Birth Certificate number
	static TYPE_DL = "DL" //Driver License
	static TYPE_PI = "PI" //Patient Internal Identifier
	static TYPE_PPN = "PPN" //Passport number
	static TYPE_SS = "SS" //Social Security number
	
	String number
	String type
	
	AssigningAuthority assigningAuthority 
	
	static belongsTo = Patient
	
    static constraints = {
    }
}
