package com.janpix.rup.services.contracts

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.rup.infrastructure.dto.PersonDTO


@XmlRootElement
class GetAllPossibleMatchedPatientsRequestMessage 
{
	@XmlElement(required=true)
	PersonDTO person

}
