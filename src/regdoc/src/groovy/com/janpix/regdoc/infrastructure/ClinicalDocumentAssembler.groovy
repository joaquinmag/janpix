package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.regdoc.domain.ClinicalDocumentType
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

class ClinicalDocumentAssembler {
	
	def authorAssembler
	def fileAttributesAssembler

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
		clinicalDoc.author = authorAssembler.fromDTO(dtoDocument.author)
		clinicalDoc.file = fileAttributesAssembler.fromDTO(dtoDocument.fileAttributes)
		clinicalDoc.documentCreationEnded = dtoDocument.documentCreationEnded
		clinicalDoc.documentCreationStarted = dtoDocument.documentCreationStarted
		clinicalDoc.comments = dtoDocument.comments
		clinicalDoc.language = dtoDocument.language
		clinicalDoc.documentType = new ClinicalDocumentType(dtoDocument.typeName, null, dtoDocument.typeId)
		clinicalDoc.format = dtoDocument.formatName
		clinicalDoc
	}
	
}
