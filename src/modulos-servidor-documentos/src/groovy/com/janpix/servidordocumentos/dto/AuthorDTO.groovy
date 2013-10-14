package com.janpix.servidordocumentos.dto

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class AuthorDTO {
	
	@XmlElement(required=true)
	HealthEntityDTO healthEntity
	
	@XmlElement
	String authorPerson
	
	@XmlElement
	String authorRole
	
	@XmlElement
	String authorSpecialty
}
