package com.janpix.rup.services.contracts

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.PatientDTO

@XmlRootElement
class UpdatePatientRequestMessage {
	@XmlElement(required=true)
	PatientDTO patient
	@XmlElement(required=true)
	AssigningAuthorityDTO healthEntity

}
