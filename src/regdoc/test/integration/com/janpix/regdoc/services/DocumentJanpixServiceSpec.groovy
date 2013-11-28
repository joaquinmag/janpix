package com.janpix.regdoc.services

import com.janpix.servidordocumentos.dto.AuthorDTO
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.FileAttributesDTO
import com.janpix.servidordocumentos.dto.HealthEntityDTO
import com.janpix.servidordocumentos.dto.TitleCriteriaDTO
import com.janpix.servidordocumentos.dto.message.ACKMessage;
import com.janpix.servidordocumentos.dto.message.QueryDocumentRequest
import com.janpix.servidordocumentos.dto.message.RegisterDocumentRequest;

import spock.lang.*



/**
 *
 */
class DocumentJanpixServiceSpec extends Specification {
	
	//private static final log = LogFactory.getLog(this)
	
	def documentJanpixService
	
	def actualDate
	def registerRequestMsg
	
	def setup() {
		
		actualDate = new Date()
		registerRequestMsg = new RegisterDocumentRequest()
		def fileAttr = new FileAttributesDTO(
			uuid: "uuid de entidad sanitaria",
			repositoryId: "2",
			mimeType: "application/pdf",
			creationTime: actualDate,
			filename: "document.pdf",
			fileHash: "uncommonHash",
			size: 15236
		)
		def healthEntity = new HealthEntityDTO(
			healthcareFacilityTypeCode: "1",
			name: "Entidad Sanitaria",
			oid: "1"
		)
		def author = new AuthorDTO(
			healthEntity: healthEntity,
			authorPerson: "Jorge Gomez",
			authorRole: "Radiólogo",
			authorSpecialty: "Radiólogo"
		)
		def clinicalDocument = new ClinicalDocumentDTO(
			uniqueId: "123123",
			name: "Titulo doc",
			fileAttributes: fileAttr,
			documentCreationStarted: actualDate,
			documentCreationEnded: actualDate,
			patientId: "2",
			author: author,
			comments: "no comments",
			language: "es-AR",
			typeId: 1,
			typeName: "Pediatría",
			formatName: "PDF"
		)
		registerRequestMsg.clinicalDocument = clinicalDocument
	}

    void "register a document"() {
		when:
			def ack = documentJanpixService.registerDocument(registerRequestMsg)
		then:
			ack != null
			println ack.text
			ack.typeCode == ACKMessage.TypeCode.SuccededRegistration
    }
	
	void "query a registered document"() {
		setup:
			def titleCriteria = new TitleCriteriaDTO(valueLookup: "Titulo doc")
			def queryMsg = new QueryDocumentRequest(
				titleCriteria: titleCriteria
			)
		when:
			def ack = documentJanpixService.registerDocument(registerRequestMsg)
			def result = documentJanpixService.queryDocument(queryMsg)
		then:
			result.documents.size() == 1
			result.documents[0].title == "Titulo doc"
	}
	
	void "query a registered document by idpatient and title"() {
		setup:
			def titleCriteria = new TitleCriteriaDTO(valueLookup: "Titulo doc")
			def patientId = "2"
			def queryMsg = new QueryDocumentRequest(
				titleCriteria: titleCriteria,
				patientId: patientId
			)
		when:
			def ack = documentJanpixService.registerDocument(registerRequestMsg)
			def result = documentJanpixService.queryDocument(queryMsg)
		then:
			result.documents.size() == 1
			result.documents[0].title == "Titulo doc"
	}
}
