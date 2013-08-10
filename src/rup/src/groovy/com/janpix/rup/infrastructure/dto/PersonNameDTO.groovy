package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.janpix.rup.infrastructure.Mapper;

@XmlRootElement
class PersonNameDTO 
{
	@XmlElement(required=true)
	String firstName
	
	@XmlElement
	String lastName
	
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
