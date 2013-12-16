package com.janpix.regdoc.services

import grails.transaction.Transactional

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.regdoc.domain.DocumentStateTypes
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.message.ACKStoredQueryMessage
import com.janpix.servidordocumentos.dto.message.QueryDocumentRequest

@Transactional
class QueryDocumentService {
	
	def clinicalDocumentAssembler
	
    ACKStoredQueryMessage queryDocument(QueryDocumentRequest queryDocumentRequestMessage) {
		List<ClinicalDocumentDTO> documents = []
		log.info("Consultando documentos para la entidad "+queryDocumentRequestMessage?.healthEntityFinder?.name+"...")
		try {
			 def results = ClinicalDocument.createCriteria().list {
				if (queryDocumentRequestMessage.titleCriteria?.valueLookup != null)
					eq("title", queryDocumentRequestMessage.titleCriteria.valueLookup)
				if (queryDocumentRequestMessage.dateCreationCriteria?.searchDate != null)
					file {
						eq("creationTime", queryDocumentRequestMessage.dateCreationCriteria.searchDate)
					}
				if (queryDocumentRequestMessage.patientId != null)
					eq("patientId", queryDocumentRequestMessage.patientId)
					
				eq("state",DocumentStateTypes.Approved)
			}

			log.info("Se obtuvieron "+results.size()+" documentos")

			log.info("Armando respuesta")
			results.each { ClinicalDocument doc->
				documents.add(clinicalDocumentAssembler.toDTO(doc))
			}

		}catch(Exception ex){
			log.error("Error al consultar los documentos en Registro de Documentos. Error: "+ex.message)
		}

		return new ACKStoredQueryMessage(documents: documents)
    }
}
