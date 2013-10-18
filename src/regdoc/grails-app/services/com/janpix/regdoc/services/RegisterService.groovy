package com.janpix.regdoc.services

import grails.transaction.Transactional

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.regdoc.domain.ClinicalDocumentType
import com.janpix.regdoc.infrastructure.*
import com.janpix.servidordocumentos.dto.*
import com.janpix.servidordocumentos.dto.message.*
import com.janpix.servidordocumentos.dto.message.ACKMessage.TypeCode

@Transactional
class RegisterService {

	def clinicalDocumentAssembler
	def authorAssembler
	def fileAttributesAssembler
	def healthEntityAssembler

	public ACKMessage registerDocument(RegisterDocumentRequest registerDocumentRequestMessage) {
		def clinicalDocDTO = registerDocumentRequestMessage.clinicalDocument
		try {
			def healthEntity = healthEntityAssembler.fromDTO(clinicalDocDTO.author.healthEntity).save()
			def person = authorAssembler.personFromDTO(clinicalDocDTO.author).save()
			def author = authorAssembler.fromDTO(clinicalDocDTO.author, healthEntity, person).save()
			def fileAttr = fileAttributesAssembler.fromDTO(clinicalDocDTO.fileAttributes).save()  
			def clinicalDoc = clinicalDocumentAssembler.fromDTO(clinicalDocDTO, author, fileAttr).save()
			validateClinicalDocument(clinicalDoc, clinicalDocDTO)
			return new ACKMessage(typeCode: TypeCode.SuccededRegistration, clinicalDocument: clinicalDocDTO)
		}
		catch (Exception e) {
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:e.message, clinicalDocument: clinicalDocDTO)
		}
	}

	private void validateClinicalDocument(ClinicalDocument document, ClinicalDocumentDTO dto) {
		document.documentType = ClinicalDocumentType.findByIdDocumentType(dto.typeId)
	}

}
