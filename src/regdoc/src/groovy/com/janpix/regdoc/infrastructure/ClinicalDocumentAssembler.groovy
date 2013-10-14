package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

class ClinicalDocumentAssembler {

	static ClinicalDocumentDTO toDTO(ClinicalDocument domainDocument) {
		ClinicalDocumentDTO dto = new ClinicalDocumentDTO()
		dto.uniqueId = domainDocument.uniqueId
		dto.patientId = domainDocument.patientId
		dto.name = domainDocument.title
		dto.author = AuthorAssembler.toDTO(domainDocument.author)
		dto.fileAttributes = FileAttributesAssembler.toDTO(domainDocument.file)
		dto.documentCreationStarted = domainDocument.documentCreationStarted
		dto.documentCreationEnded = domainDocument.documentCreationEnded
		dto.stateDescription = domainDocument.state.name
		dto.comments = domainDocument.comments
		dto.language = domainDocument.language
		dto.typeId = domainDocument.documentType.idDocumentType
		dto.typeName = domainDocument.documentType.name
		dto.formatName = domainDocument.format.value()
	}

	static ClinicalDocument fromDTO(ClinicalDocumentDTO dtoDocument) {
		
	}
	
}
