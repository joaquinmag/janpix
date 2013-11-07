package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import com.janpix.rup.infrastructure.Mapper

@XmlRootElement
class PhoneNumberDTO 
{
	@XmlElement(required=true)
	String type
	
	@XmlElement(required=true)
	String number
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
