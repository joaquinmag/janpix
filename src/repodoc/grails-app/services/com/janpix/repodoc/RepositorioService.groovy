package com.janpix.repodoc

import grails.transaction.Transactional

import com.janpix.repodoc.assemblers.ClinicalDocumentAssembler
import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

@Transactional
class RepositorioService {

	/**
	 * Ingresa un documento en el repositorio y lo registra en el registro
	 * @return
	 */
    def provideAndRegisterDocument(ClinicalDocumentDTO document) {

    }
	
	/**
	 * Retorna el documento que contenga el uuid pasado
	 * @param uniqueId
	 * @return
	 */
	ClinicalDocumentDTO retrieveDocumentByUUID(String uuid){
		// Se obtiene el documento
		ClinicalDocument document = ClinicalDocument.findByUuid(uuid)
		
		// Se transforma el documento
		return ClinicalDocumentAssembler.toDTO(document)
	}
	
	/**
	 * Registra un documento en el registro de documentos
	 * @param document
	 */
	private def registerDocument(ClinicalDocumentDTO document){
		
	}
}

