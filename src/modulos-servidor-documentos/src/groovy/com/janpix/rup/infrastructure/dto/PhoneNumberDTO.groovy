package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class PhoneNumberDTO 
{
	@XmlElement(required=true)
	String type
	
	@XmlElement(required=true)
	String number
	
}
