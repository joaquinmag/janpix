package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
class PersonNameDTO 
{
	@XmlElement(required=true)
	String firstName
	
	@XmlElement(required=true)
	String lastName
	
}
