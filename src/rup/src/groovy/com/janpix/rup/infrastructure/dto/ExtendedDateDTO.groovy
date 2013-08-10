package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.rup.infrastructure.Mapper

@XmlRootElement
class ExtendedDateDTO 
{
	@XmlElement(required=true)
	String date
	
	@XmlElement
	String precission
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
