package com.janpix.healthentity

import grails.transaction.Transactional
import com.janpix.servidordocumentos.dto.message.ACKMessage;

@Transactional
class JanpixService {
	
	def janpixRepodocServiceClient
	
	/**
	 * Retorna el documento del Repositorio de Documentos
	 * que contiene el uuid pasado
	 */
	ACKMessage getDocumentByUUID(String uuid){
		
	}
	
    
}
