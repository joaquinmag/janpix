package com.janpix.rup.infrastructure.dto

import com.janpix.rup.infrastructure.Mapper;

class ExtendedDateDTO {
	String date
	String precission
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
