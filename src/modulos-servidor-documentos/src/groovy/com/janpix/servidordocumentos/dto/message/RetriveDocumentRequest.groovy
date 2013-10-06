package com.janpix.servidordocumentos.dto.message

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

@XmlRootElement
class RetriveDocumentRequest {
	
	@XmlElement(required=true)
	ClinicalDocumentDTO clinicalDocument
	
}
