package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement


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

}
