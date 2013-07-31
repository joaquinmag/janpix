package com.janpix.rup.infrastructure.dto

import com.janpix.rup.infrastructure.Mapper;

class CityDTO {
	String nameCity
	String nameProvince
	String nameCountry;
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
