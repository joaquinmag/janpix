package com.janpix.regdoc.services

import grails.transaction.Transactional

import org.hibernate.criterion.Example

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.regdoc.domain.DocumentState
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.HealthEntityDTO
import com.janpix.servidordocumentos.dto.message.ACKStoredQueryMessage
import com.janpix.servidordocumentos.dto.message.QueryDocumentRequest

@Transactional
class QueryDocumentService {
	
	def clinicalDocumentAssembler
	def grailsApplication
	
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
				
				if(!this.isPatientAuthority(queryDocumentRequestMessage?.healthEntityFinder))
					eq("state",DocumentState.approvedState())
					
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
	
	private Boolean isPatientAuthority(HealthEntityDTO healthEntity){
		log.info("Verificando Autoridad. OID:"+healthEntity.oid+", Name:"+healthEntity.name)
		if(healthEntity == null)
			return false
			
		Boolean isAuthority = (healthEntity.name == grailsApplication.config.patients.name &&
				healthEntity.oid == grailsApplication.config.patients.oid)
		
		log.info("EsAutoridad="+isAuthority)
		
		return isAuthority
	}
}
