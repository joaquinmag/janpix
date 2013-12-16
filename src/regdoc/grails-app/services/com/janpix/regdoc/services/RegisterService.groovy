package com.janpix.regdoc.services

import grails.transaction.Transactional
import grails.validation.ValidationException

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.regdoc.domain.ClinicalDocumentType
import com.janpix.regdoc.domain.DocumentStateTypes
import com.janpix.regdoc.exceptions.CantChangeDocumentState
import com.janpix.regdoc.exceptions.InvalidDocumentTypeException
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
		try {
			def clinicalDocDTO = registerDocumentRequestMessage.clinicalDocument
			log.info("Grabando Entidad Sanitaria: ")
			def healthEntity = healthEntityAssembler.fromDTO(clinicalDocDTO.author.healthEntity).save()
			
			log.info("Grabando autor "+clinicalDocDTO.author)
			def person = authorAssembler.personFromDTO(clinicalDocDTO.author).save()
			def author = authorAssembler.fromDTO(clinicalDocDTO.author, healthEntity, person).save()
			
			log.info("Grabando atributos del archivo ")
			def fileAttr = fileAttributesAssembler.fromDTO(clinicalDocDTO.fileAttributes).save()
			
			log.info("Registrando documento ")
			log.info("clinicalDoc typeId=${clinicalDocDTO.typeId}")
			def clinicalDoc = clinicalDocumentAssembler.fromDTO(clinicalDocDTO, author, fileAttr).save()
			
			log.info("Validando documento ")
			validateClinicalDocument(clinicalDoc, clinicalDocDTO)
			
			log.info("Documento registrado correctamente")
			return new ACKMessage(typeCode: TypeCode.SuccededRegistration, clinicalDocument: clinicalDocDTO)
		}
		catch(ValidationException ve){
			log.error("Error de validación en una de las entidades. Error:"+ve.message)
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:ve.message)
		}
		catch (Exception e) {
			log.error("Error inesperado. Error:"+e.message)
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:e.message)
		}
	}

	public ACKMessage updateStateDocument(UpdateStateDocumentRequest updateRequest) {
		try{
			// Se valida que sea la autoridad habilitada la que realice la peticion
			if(!validateAuthority(updateRequest.authority)){
				String message = "La autoridad "+updateRequest?.authority?.name+" enviada es incorrecta"
				log.error(message)
				return new ACKMessage(typeCode: TypeCode.RegistrationError, text:message)
			}
			
			// Se obtiene el documento
			ClinicalDocument document = ClinicalDocument.findByUniqueId(updateRequest.documentUniqueId)
			if(!document){
				String message = "El documento solicitado NO existe"
				log.error(message)
				return new ACKMessage(typeCode: TypeCode.RegistrationError, text:message)
			}
			
			// Se modifica el estado
			this.updateDocumentStateFromString(document,updateRequest.stateDescription)

			return new ACKMessage(typeCode: TypeCode.SuccededInsertion, clinicalDocument: null)
			
		}
		catch(CantChangeDocumentState ex){
			String msg = "No es posible modificar el estado del documento"
			log.error(msg)
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:msg)
		}
		catch(ValidationException ve){
			log.error("Error de validación en una de las entidades. Error:"+ve.message)
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:ve.message)
		}
		catch (Exception e) {
			log.error("Error inesperado. Error:"+e.message)
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:e.message)
		}
	}
	private void validateClinicalDocument(ClinicalDocument document, ClinicalDocumentDTO dto) {
		def docType = ClinicalDocumentType.findByIdDocumentType(dto.typeId)
		if (docType == null)
			throw new InvalidDocumentTypeException("Document Type Id = ${dto.typeId} doesn't exist")
		document.documentType = docType
	}
	
	private Boolean validateAuthority(HealthEntityDTO authority){
		// TODO hacer
		return true
	}
	
	private updateDocumentStateFromString(ClinicalDocument document,String state){
		switch(state){
			case DocumentStateTypes.Submitted.toString() :
				document.state.submit() 
				break;
				
			case DocumentStateTypes.Approved.toString() :
				document.state.approve()
				break;
			
			case DocumentStateTypes.Deleted.toString() :
				document.state.erase()
				break;
				
			case DocumentStateTypes.Deprecated.toString() :
				document.state.deprecate()
				break;
				
			default:
				throw new Exception("Estado "+state+" inexistente")
				break;
				
		}
			
	}

}
