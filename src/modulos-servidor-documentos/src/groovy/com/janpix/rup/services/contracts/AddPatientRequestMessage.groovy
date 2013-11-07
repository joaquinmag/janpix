package com.janpix.rup.services.contracts

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO;
import com.janpix.rup.infrastructure.dto.PatientDTO
import com.janpix.rup.infrastructure.dto.PersonDTO;


@XmlRootElement
class AddPatientRequestMessage {
	
	@XmlElement(required=true)
	PersonDTO person
	
	@XmlElement(required=true)
	AssigningAuthorityDTO healthEntity
	
	@XmlElement(required=true)
	String organizationId
}
