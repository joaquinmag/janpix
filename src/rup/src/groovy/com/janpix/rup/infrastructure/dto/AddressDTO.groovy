package com.janpix.rup.infrastructure.dto

import com.janpix.rup.infrastructure.Mapper;


class AddressDTO {
	String unitId
	String street
	String number
	String floor
	String department
	String zipCode
	CityDTO city
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
