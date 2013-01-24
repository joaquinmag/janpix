package com.janpix.rup.empi

class Identifiers {
	//Basado en HL7 Version 2.5, Chapter 2A, Section 2A.14.5
	static OPTIONS_TYPES = [
		"BC":"Bank card number",
		"BR":"Birth Certificate number",
		"DL":"Driver License",
		"PI":"Patient Internal Identifier",
		"PPN":"Passport number",
		"SS":"Social Security number",
		]
	
	String number
	String type
	
	AssigningAuthority assigningAuthority 
	
	static belongsTo = Patient
	
    static constraints = {
    }
}
