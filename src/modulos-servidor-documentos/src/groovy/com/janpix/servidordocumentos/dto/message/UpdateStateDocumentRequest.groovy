package com.janpix.servidordocumentos.dto.message

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.servidordocumentos.dto.HealthEntityDTO

@XmlRootElement
class UpdateStateDocumentRequest {
	
	@XmlElement(required=true)
	HealthEntityDTO authority
	
	@XmlElement(required=true)
	String documentUniqueId
	
	@XmlElement(required=true)
	String stateDescription

}
