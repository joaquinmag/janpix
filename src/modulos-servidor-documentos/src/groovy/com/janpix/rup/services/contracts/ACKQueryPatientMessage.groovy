package com.janpix.rup.services.contracts

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.rup.infrastructure.dto.PatientDTO


@XmlRootElement
class ACKQueryPatientMessage extends ACKMessage {
	@XmlElementWrapper(name = "patients")
	@XmlElement(name = "patient")
	List<PatientDTO> patients
}
