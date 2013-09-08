package com.janpix.rup.empi

//import com.janpix.rup.Sex
import com.janpix.rup.infrastructure.Mapper
import com.janpix.rup.infrastructure.dto.PersonDTO

class Person {
	
	//Basado en HL7 User definition table
	static final String TYPE_SEX_FEMALE 			= 'F'
	static final String TYPE_SEX_MALE 				= 'M'
	static final String TYPE_SEX_OTHER 			= 'O'
	static final String TYPE_SEX_UNKNOWN 			= 'U'
	static final String TYPE_SEX_AMBIGUOS 			= 'A'
	static final String TYPE_SEX_NOT_APPLICABLE 	= 'N'
	
	static final String TYPE_MARITALSTATUS_MARRIED	= 'M'
	static final String TYPE_MARITALSTATUS_SINGLE	= 'S'
	static final String TYPE_MARITALSTATUS_WIDOWED	= 'W'

	
	PersonName givenName
	ExtendedDate birthdate
	String administrativeSex
	String maritalStatus
	City birthplace
	Boolean multipleBirthIndicator 
	Boolean organDonorIndicator
	ExtendedDate deathdate

	
	Date lastUpdated
	Date dateCreated
		
	List<Address> addresses	= []
	List<PhoneNumber> phoneNumbers = []
	Set<Identifier> identifiers = []
	
	static hasMany = [
			identifiers:Identifier,
			addresses:Address,
			phoneNumbers:PhoneNumber
		]
	
    static constraints = {
		givenName(nullable:false)
		administrativeSex(nullable:false)
		birthdate(nullable:false)
		addresses(
			nullable:true,
			validator:{ val, obj->
				return !obj.addresses.empty
			}
			)
		
		maritalStatus(nullable:true)
		deathdate(nullable:true)
		birthplace(nullable:true)
		multipleBirthIndicator(nullable:true)
		organDonorIndicator(nullable: true)
		
		identifiers(nullable:true)
		phoneNumbers(nullable:true)
		
		
    }
	
	String toString(){
		return "${givenName}-${birthdate}"
	}

	/**
	 * Devuelve la dirección principal de la persona
	 * La dirección principal es en orden decreciente CIVIL, LEGAL, FISCAL
	 * @return Address si tiene direcciones cargadas, NULL de lo contrario
	 */
	Address principalAddress()
	{
		if(!this.addresses.empty){
			def civilAddress = this.addresses.find{it.type == Address.TYPE_CIVIL}
			if(civilAddress)
				return civilAddress
			
			def legalAddress = this.addresses.find{it.type == Address.TYPE_LEGAL}
			if(legalAddress)
				return legalAddress
				
			def fiscalAddress = this.addresses.find{it.type == Address.TYPE_FISCAL}
			if(fiscalAddress)
				return fiscalAddress
		}
		return null
	}
	
	/**
	 * Devuelve el documento que representa la identidad de una persona
	 * Si tiene DNI devuelve ese documento
	 * Sino revisa si tiene LE o LC para devolverlo
	 * Sino revisa si contiene pasaporte
	 * Sino tiene ninguno de estos devuelve NULL
	 * @return
	 */
	Identifier identityDocument(){
		def dni = this.identifiers.find {it.type == Identifier.TYPE_IDENTIFIER_DNI}
		if(dni){
			return dni
		}else{
			def otherId = this.identifiers.find {it.type == Identifier.TYPE_IDENTIFIER_LC || it.type == Identifier.TYPE_IDENTIFIER_LE}
			if(otherId){
				return otherId
			}else{
				def passport = this.identifiers.find{it.type == Identifier.TYPE_IDENTIFIER_PPN}
				return passport
			}
		}
		return null
	}
	

	/**
	 * Actualiza la dirección basandose en si tiene que agregar una nueva
	 * o actualizar la existente segun el tipo de dirección
	 * @param newAddress
	 */
	void updateAddresses(ArrayList<Address> newAddresses){
		newAddresses.each{Address address->
			if(address.validate()){
				if(this.addresses*.type.contains(address.type)){
					def actualAddress = this.addresses.find{it.type ==address.type }
					actualAddress?.update(address) 
				}else{
					this.addToAddresses(address)
				}
			}
		}
	}
	
	/**
	 * Actualiza algun identificador que no sea el de la Entidad Sanitaria
	 * Verifica que actualmente no tenga un identificador del mismo tipo para la misma autoridad de asignacion.
	 * Si lo tiene lo actualiza
	 * @param newIdentifier
	 */
	void updateIdentifiers(newIdentifiers){
		def uniqueIdentifiers = [
				Identifier.TYPE_IDENTIFIER_DNI,Identifier.TYPE_IDENTIFIER_LC,Identifier.TYPE_IDENTIFIER_LE,
				Identifier.TYPE_IDENTIFIER_PPN,Identifier.TYPE_IDENTIFIER_DL
			]
		newIdentifiers.each{ Identifier id->
			if(id.type != Identifier.TYPE_IDENTIFIER_PI && id.validate() && !this.identifiers.contains(id)){
				if(uniqueIdentifiers.contains(id.type)){
					def actualIdentifier = this.identifiers.find{it.type == id.type && it.assigningAuthority == id.assigningAuthority}
					if(actualIdentifier)
						this.removeFromIdentifiers(actualIdentifier) 
				}
				this.addToIdentifiers(id) //Puede ser una tarjeta de credito u otra cosa que pueden ser varias
			}
		}
	}
	
	/**
	 * Actualiza el nombre sin modificar el objeto
	 * @param PersonName newName
	 */
	void updateGivenName(PersonName newName){
		newName.properties.each{prop,val->
			if(val)
				this.givenName[prop] = val
		}
	}
	
	void updatePhoneNumbers(ArrayList<PhoneNumber> newPhoneNumbers){
		newPhoneNumbers.each{PhoneNumber phone->
			if(phone.validate()){
				if(this.phoneNumbers*.type.contains(phone.type)){
					def actualPhone = this.phoneNumbers.find{it.type == phone.type }
					actualPhone?.update(phone)
				}else{
					this.addToPhoneNumbers(phone)
				}
			}
		}
	}
	
	/**
	 * Convierte la clase de dominio en su DTO
	 * @param mapper
	 * @return
	 */
	PersonDTO convert(Mapper mapper){
		return mapper.convert(this)
	}
	

}
