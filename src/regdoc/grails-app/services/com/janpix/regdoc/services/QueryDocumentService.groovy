package com.janpix.regdoc.services

import grails.transaction.Transactional

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.message.ACKStoredQueryMessage
import com.janpix.servidordocumentos.dto.message.QueryDocumentRequest

@Transactional
class QueryDocumentService {

    ACKStoredQueryMessage queryDocument(QueryDocumentRequest queryDocumentRequestMessage) {
		final def results = ClinicalDocument.createCriteria().list {
			if (queryDocumentRequestMessage.titleCriteria != null && queryDocumentRequestMessage.titleCriteria.size())
				eq("title", queryDocumentRequestMessage.titleCriteria)
			if (queryDocumentRequestMessage.dateCreationCriteria != null)
				file {
					eq("creationTime", queryDocumentRequestMessage.dateCreationCriteria)
				}
		}
		new ACKStoredQueryMessage(documents: results)
    }
}
