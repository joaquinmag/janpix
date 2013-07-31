package com.janpix.rup.empi

import com.janpix.rup.infrastructure.Mapper
import com.janpix.rup.infrastructure.dto.AddressDTO

class Address {
	String unitId
	String street
	String number
	String floor
	String department
	String zipCode
	City city
	Date dateCreated
	
	static belongsTo = Person
	
    static constraints = {
		city(nullable:false)
		zipCode(nullable:true,blank:true)
		floor(nullable:true,blank:true)
		department(nullable:true,blank:true)
		unitId(nullable:true)
    }
	
	boolean equals(other){
		if(	this.street == other.street &&
			this.number == other.number &&
			this.floor	== other.floor &&
			this.zipCode == other.zipCode &&
			this.city == other.city
		)
			return true
			
		return false
	}
	
	void update(Address other){
		if(other.city){
			if(other.street)this.street = other.street
			if(other.number)this.number = other.number
			if(other.floor)this.floor = other.floor
			if(other.department)this.department = other.department
			if(other.zipCode)this.zipCode = other.zipCode
			this.city = other.city
		}
	}
	
	String toString(){
		return "${street} ${number} ${floor}${department}, ${city}"
	}
	
	/**
	 * Convierte la clase de dominio en su DTO
	 * @param mapper
	 * @return
	 */
	AddressDTO convert(Mapper mapper){
		return mapper.convert(this)
	}
}
