package com.janpix.healthentity

import grails.transaction.Transactional
import ar.com.healthentity.ClinicalDocument
import ar.com.healthentity.Patient
import ar.com.healthentity.janpix.JanpixAssembler

import com.janpix.exceptions.JanpixConnectionException
import com.janpix.exceptions.JanpixException
import com.janpix.exceptions.JanpixPossibleMatchingPatientException
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.message.ACKMessage
import com.janpix.servidordocumentos.dto.message.RetrieveDocumentRequest

import com.janpix.rup.services.contracts.AddPatientRequestMessage
import com.janpix.rup.services.contracts.ACKMessage

@Transactional
class JanpixService {
	
	def janpixRepodocServiceClient
	def janpixPixManagerServiceClient
	
	/**
	 * Agrega un nuevo paciente en el RUP
	 * @param patient
	 * @return String con el CUIS del paciente agregado
	 */
	String addNewPatient(Patient patient){
		try{
			//throw new JanpixPossibleMatchingPatientException("Metodo no implementado");
			AddPatientRequestMessage requestMessage = new AddPatientRequestMessage()
			
			log.info("Agregando paciente ["+patient+"] al Registro Unico de Pacientes")
			ACKMessage ack = janpixPixManagerServiceClient.AddNewPatient(requestMessage)
			if(ack.typeCode != ACKMessage.TypeCode.SuccededRetrieve){
				log.error("Error al agregar al paciente. Error:"+ack.typeCode.toString()+". Mensaje:"+ack.text)
				return null;
			}
			
			//TODO procesar lo que falta
		}
		catch(Exception ex){
			String message ="Error de conexión contra el RUP: "+ex.message
			log.error(message)
			throw new JanpixConnectionException(message);
		}
	}
	
	/**
	 * Retorna el documento del Repositorio de Documentos
	 * que contiene el uuid pasado
	 */
	ClinicalDocument getDocumentByUUID(String uuid){
		try{
			RetrieveDocumentRequest requestMessage = new RetrieveDocumentRequest(uuid:uuid)
			
			log.info("Consultando Repositorio de documentos por el uuid:"+uuid)
			ACKMessage ack =  janpixRepodocServiceClient.retrieveDocument(requestMessage)
			if(ack.typeCode != ACKMessage.TypeCode.SuccededRetrieve){
				log.error("Error al obtener el documento. Error:"+ack.typeCode.toString()+". Mensaje:"+ack.text)
				return null;
			}
			
			log.info("Documento obtenido correctamente")
			ClinicalDocument clinicalDocument = JanpixAssembler.fromDocument(ack.clinicalDocument)
			return clinicalDocument;
		}
		catch(Exception ex){
			String message ="Error de conexión contra el repositorio: "+ex.message 
			log.error(message)
			throw new JanpixException(message);
		}
	}
	
	/**
	 * Sube un documento clinico asociado a un paciente
	 * @param clinicalDocument
	 * @return
	 */
    Boolean UploadDocument(ClinicalDocumentDTO clinicalDocument){
    
	}
}
