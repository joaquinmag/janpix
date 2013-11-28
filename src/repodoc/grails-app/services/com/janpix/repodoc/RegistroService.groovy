package com.janpix.repodoc

import grails.transaction.Transactional
import grails.validation.ValidationException

import org.bson.types.ObjectId

import com.janpix.repodoc.assemblers.ClinicalDocumentAssembler
import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.repodoc.exceptions.BussinessRuleException
import com.janpix.repodoc.exceptions.MetadataException
import com.janpix.repodoc.exceptions.RegistrationServiceException
import com.janpix.servidordocumentos.FileUtils
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.message.*
import com.janpix.servidordocumentos.dto.message.ACKMessage.TypeCode

class RegistroService {
	
	def janpixRegdocServiceClient
	
	def registerDocument(ClinicalDocumentDTO documentDTO){
		try{
			log.debug("Llamando WS para registrar documento..")
			RegisterDocumentRequest requestMessage = new RegisterDocumentRequest(clinicalDocument:documentDTO)
			
			ACKMessage ack =  janpixRegdocServiceClient.registerDocument(requestMessage)
			
			// Se valida el mensaje
			if(ack.typeCode != ACKMessage.TypeCode.SuccededRegistration){
				String messageError = "Error al registrar el documento. Error:"+ack.typeCode.toString();
				log.error(messageError)
				throw new BussinessRuleException(messageError)
			}
			 
			log.debug("Documento registrado correctamente")
		}
		catch(Exception ex){
			String message ="Error de conexión contra el Registro de Documentos: "+ex.message
			log.error(message)
			throw new BussinessRuleException(message);
		}
	}
	
}

