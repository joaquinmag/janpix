package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement


@XmlRootElement
class ExtendedDateDTO 
{
	@XmlElement
	String date
	
	@XmlElement(required=true)
	String precission

}
