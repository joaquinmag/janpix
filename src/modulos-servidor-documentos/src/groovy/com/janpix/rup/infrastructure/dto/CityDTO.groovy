package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement


@XmlRootElement
class CityDTO 
{
	@XmlElement(required=true)
	String nameCity
	
	@XmlElement(required=true)
	String nameProvince
	
	@XmlElement
	String nameCountry;
	
}
