package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

class ClinicalDocumentAssembler {

	ClinicalDocumentDTO toDTO(ClinicalDocument domainDocument) {
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

	ClinicalDocument fromDTO(ClinicalDocumentDTO dtoDocument) {
		def clinicalDoc = new ClinicalDocument()
		clinicalDoc.uniqueId = dtoDocument.uniqueId
		clinicalDoc.title = dtoDocument.name
		clinicalDoc.patientId = dtoDocument.patientId
		clinicalDoc.author = AuthorAssembler.fromDTO(dtoDocument.author)
		clinicalDoc.file = FileAttributesAssembler.fromDTO(dtoDocument.fileAttributes)
		clinicalDoc.documentCreationEnded = dtoDocument.documentCreationEnded
		clinicalDoc.documentCreationStarted = dtoDocument.documentCreationStarted
		clinicalDoc.comments = dtoDocument.comments
		clinicalDoc.language = dtoDocument.language
		clinicalDoc.documentType = new ClinicalDocumentType(dtoDocument.typeName, null, typeId)
		clinicalDoc.format = dtoDocument.formatName
		clinicalDoc
	}
	
}
