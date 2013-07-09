package com.janpix.rup.empi



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
		def document = p.identityDocument()
		def address1  = p.principalAddress() 
		
		
		identity.name 			= "${p.givenName?.firstName} ${p.givenName?.lastName}" 
		identity.sex 			= p.administrativeSex
		identity.birthdate 		= p.birthdate
		identity.multipleBirthIndicator = p.multipleBirthIndicator
		identity.livingplace	= (!address1)?"":"${address1.city?.province?.country?.name},${address1.city?.province?.name},${address1.city?.name}"
		identity.address		= (!address1)?"":"${address1.street} ${address1.number}"
		identity.document		= "${document}"
		
		return identity
	}
	

}
