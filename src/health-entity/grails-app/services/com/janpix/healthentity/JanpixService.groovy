package com.janpix.healthentity

import grails.transaction.Transactional

import com.janpix.JanpixException
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.message.ACKMessage
import com.janpix.servidordocumentos.dto.message.RetrieveDocumentRequest

@Transactional
class JanpixService {
	
	def janpixRepodocServiceClient
	
	/**
	 * Retorna el documento del Repositorio de Documentos
	 * que contiene el uuid pasado
	 */
	ClinicalDocumentDTO getDocumentByUUID(String uuid){
		try{
			RetrieveDocumentRequest requestMessage = new RetrieveDocumentRequest(uuid:uuid)
			
			log.info("Consultando Repositorio de documentos por el uuid:"+uuid)
			ACKMessage ack =  janpixRepodocServiceClient.retrieveDocument(requestMessage)
			if(ack.typeCode != ACKMessage.TypeCode.SuccededRetrieve){
				log.error("Error al obtener el documento. Error:"+ack.typeCode.toString()+". Mensaje:"+ack.text)
				return null;
			}
			
			log.info("Documento obtenido correctamente")
			
			return ack.clinicalDocument;
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
    ACKMessage UploadDocument(ClinicalDocumentDTO clinicalDocument){
    
	}
}
