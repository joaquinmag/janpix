package com.janpix.regdoc.services

import grails.transaction.Transactional
import grails.validation.ValidationException

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
			log.info("Grabando Entidad Sanitaria: ")
			def healthEntity = healthEntityAssembler.fromDTO(clinicalDocDTO.author.healthEntity).save()
			
			log.info("Grabando autor "+clinicalDocDTO.author)
			def person = authorAssembler.personFromDTO(clinicalDocDTO.author).save()
			def author = authorAssembler.fromDTO(clinicalDocDTO.author, healthEntity, person).save()
			
			log.info("Grabando atributos del archivo ")
			def fileAttr = fileAttributesAssembler.fromDTO(clinicalDocDTO.fileAttributes).save()
			
			log.info("Registrando documento ")
			def clinicalDoc = clinicalDocumentAssembler.fromDTO(clinicalDocDTO, author, fileAttr).save()
			
			log.info("Validando documento ")
			validateClinicalDocument(clinicalDoc, clinicalDocDTO)
			
			log.info("Documento registrado correctamente")
			return new ACKMessage(typeCode: TypeCode.SuccededRegistration, clinicalDocument: clinicalDocDTO)
		}
		catch(ValidationException ve){
			log.error("Error de validación en una de las entidades. Error:"+ve.message)
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:ve.message, clinicalDocument: clinicalDocDTO)
		}
		catch (Exception e) {
			log.error("Error inesperado. Error:"+e.message)
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:e.message, clinicalDocument: clinicalDocDTO)
		}
	}

	private void validateClinicalDocument(ClinicalDocument document, ClinicalDocumentDTO dto) {
		document.documentType = ClinicalDocumentType.findByIdDocumentType(dto.typeId)
	}

}
