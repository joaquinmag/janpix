package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.rup.infrastructure.Mapper

@XmlRootElement
class CityDTO 
{
	@XmlElement(required=true)
	String nameCity
	
	@XmlElement
	String nameProvince
	
	@XmlElement
	String nameCountry;
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
