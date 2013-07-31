package com.janpix.rup.infrastructure.dto

import com.janpix.rup.infrastructure.Mapper;

class PersonNameDTO {
	String firstName
	String lastName
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
