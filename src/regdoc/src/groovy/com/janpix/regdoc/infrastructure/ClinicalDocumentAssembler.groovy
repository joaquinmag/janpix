package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

class ClinicalDocumentAssembler {

	static ClinicalDocumentDTO toDTO(ClinicalDocument domainDocument) {
		ClinicalDocumentDTO dto = new ClinicalDocumentDTO()
		dto.uniqueId = domainDocument.uniqueId
		dto.patientId = domainDocument.patientId
		dto.name = domainDocument.title
	}
	
	static ClinicalDocument fromDTO(ClinicalDocumentDTO dtoDocument) {
		
	}
	
}
