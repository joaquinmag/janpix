package com.janpix.rup.empi

/**
 * Representa la identidad de una persona
 * Posee un constructor que construye Identity a partir de un Person 
 * estandarizando y filtrando los datos necesarios
 *
 */
class Identity {
	String name //lastName, firstName secondName
	Date birthdate //aaaa-mm-dd
	String sex 
	String secondLastName 
	
	String livingplace //country,province,city
	String address //street number
	
	String document //type:number
	
	Boolean hasTwin;
	String maritalStatus;
	
	
	
	/**
	 * Construye la identidad a partir de una person
	 * @param Person p
	 * @return
	 */
	static Identity buildFromPerson(Person p){
		def identity = new Identity()
		identity.name 			= p.givenName?.lastName + ', ' + p.givenName?.firstName
		identity.birthdate 		= p.birthdate
		identity.sex 			= p.administrativeSex
		identity.secondLastName	= p.motherName?.lastName
		identity.livingplace	= (!p.livingplace)?"":p.livingplace.country+","+p.livingplace.province+","+p.livingplace.name
		identity.address		= p.address?.street+" "+p.address?.number
		identity.document		= p.document.toString()
		
		return identity
	}
	

}
