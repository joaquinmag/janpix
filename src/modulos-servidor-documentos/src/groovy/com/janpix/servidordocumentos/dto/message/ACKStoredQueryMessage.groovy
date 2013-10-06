package com.janpix.servidordocumentos.dto.message

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

@XmlRootElement
class ACKStoredQueryMessage {
	@XmlElementWrapper(name = "clinicalDocuments")
	@XmlElement(name = "clinicalDocument")
	List<ClinicalDocumentDTO> documents
}
