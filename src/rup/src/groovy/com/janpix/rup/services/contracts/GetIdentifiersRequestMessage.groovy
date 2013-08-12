package com.janpix.rup.services.contracts

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO


@XmlRootElement
class GetIdentifiersRequestMessage 
{
	@XmlElement(required=true)
	String patientIdentifier
	
	@XmlElement(required=true)
	AssigningAuthorityDTO assigningAuthority
	
	@XmlElementWrapper(name = "othersDomain")
	@XmlElement(name = "domain")
	List<AssigningAuthorityDTO> othersDomain

}
