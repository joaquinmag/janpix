package com.janpix.rup.empi

import com.janpix.rup.empi.Identifier.TypeIdentifier;

/**
 * Representa la identidad de una persona
 * Posee un constructor que construye Identity a partir de un Person 
 * estandarizando y filtrando los datos necesarios
 *
 */
class Identity {
	String name //lastName, firstName secondName
	ExtendedDate birthdate //aaaa-mm-dd
	String sex 
	//String secondLastName 
	
	String livingplace //country,province,city
	String address //street number
	
	String document //TypeNumberCountryEmitter
	
	Boolean multipleBirthIndicator;
	
	
	
	/**
	 * Construye la identidad a partir de una person
	 * @param Person p
	 * @return
	 */
	static Identity buildFromPerson(Person p){
		def identity = new Identity()
		//TODO ver si uso findAll
		def document = p.identifiers.find {it.type == Identifier.TYPE_IDENTIFIER_DNI || it.type == Identifier.TYPE_IDENTIFIER_LC || it.type == Identifier.TYPE_IDENTIFIER_LE}
		def address = p.addresses.get(0)//TODO ver que criterio uso para obtener la address 
		
		
		identity.name 			= "${p.givenName?.firstName} ${p.givenName?.lastName} ${p.givenName?.motherLastName}" 
		identity.sex 			= p.administrativeSex
		identity.birthdate 		= p.birthdate
		identity.multipleBirthIndicator = p.multipleBirthIndicator
		identity.livingplace	= (!address)?"":"${address.city?.province?.country?.name},${address.city?.province?.name},${address.city?.name}"
		identity.address		= (!address)?"":"${address.street} ${address.number}"
		identity.document		= (!document)?"":"${document.type}${document.number}${document.assigningAuthority}"
		
		return identity
	}
	

}
