package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.rup.infrastructure.Mapper

@XmlRootElement
class AddressDTO {
	@XmlElement(required=true)
	String type
	
	@XmlElement (required=true)
	String street
	
	@XmlElement
	String number
	
	@XmlElement
	String floor
	
	@XmlElement
	String department
	
	@XmlElement
	String zipCode
	
	@XmlElement(required=true)
	CityDTO city
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
