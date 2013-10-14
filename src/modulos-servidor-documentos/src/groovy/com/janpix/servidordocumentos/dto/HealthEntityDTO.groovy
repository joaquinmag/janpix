package com.janpix.servidordocumentos.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlMimeType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class HealthEntityDTO {

	@XmlElement
	String healthcareFacilityTypeCode

	@XmlElement
	String name

	@XmlElement
	String oid

}
