package com.janpix.rup.infrastructure.dto

import com.janpix.rup.infrastructure.Mapper;

class IdentifierDTO {
	String type
	String number
	AssigningAuthorityDTO assigningAuthority
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
