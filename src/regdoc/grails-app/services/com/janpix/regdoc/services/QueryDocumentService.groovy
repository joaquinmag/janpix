package com.janpix.regdoc.services

import grails.transaction.Transactional

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.message.ACKStoredQueryMessage
import com.janpix.servidordocumentos.dto.message.QueryDocumentRequest

@Transactional
class QueryDocumentService {
	
    ACKStoredQueryMessage queryDocument(QueryDocumentRequest queryDocumentRequestMessage) {
		def results
		log.info("Consultando documentos para la entidad "+queryDocumentRequestMessage?.healthEntityFinder?.name+"...")
		try {
			 results = ClinicalDocument.createCriteria().list {
				if (queryDocumentRequestMessage.titleCriteria?.valueLookup != null)
					eq("title", queryDocumentRequestMessage.titleCriteria.valueLookup)
				if (queryDocumentRequestMessage.dateCreationCriteria?.searchDate != null)
					file {
						eq("creationTime", queryDocumentRequestMessage.dateCreationCriteria.searchDate)
					}
				if (queryDocumentRequestMessage.patientId != null)
					eq("patientId", queryDocumentRequestMessage.patientId)
			}
			 
			log.info("Se obtuvieron "+results.size()+" documentos")
		}catch(Exception ex){
			log.error("Error al consultar los documentos en Registro de Documentos. Error: "+ex.message)
			results = []
		}
		
		return new ACKStoredQueryMessage(documents: results)
    }
}
