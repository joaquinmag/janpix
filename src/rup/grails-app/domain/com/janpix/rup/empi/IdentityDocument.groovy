package com.janpix.rup.empi

class IdentityDocument {
	static String TYPE_DNI = "DNI"
	static String TYPE_LC = "LC"
	static String TYPE_LE = "LE"
	
	String type
	String number
	
	
	static belongsTo = Person
	
    static constraints = {

    }
	
	
	boolean equals(other){
		return (this.type == other.type && this.number == other.number)
	}
	
	String toString(){
		return "${type}${number}"	
	}
	
	
}
