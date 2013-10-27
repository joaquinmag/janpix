package com.janpix.regdoc.services

import grails.transaction.Transactional

import com.janpix.regdoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.message.ACKStoredQueryMessage
import com.janpix.servidordocumentos.dto.message.QueryDocumentRequest

@Transactional
class QueryDocumentService {

    ACKStoredQueryMessage queryDocument(QueryDocumentRequest queryDocumentRequestMessage) {
		final def results = ClinicalDocument.createCriteria().list {
			if (queryDocumentRequestMessage.titleCriteria?.valueLookup != null)
				eq("title", queryDocumentRequestMessage.titleCriteria.valueLookup)
			if (queryDocumentRequestMessage.dateCreationCriteria?.searchDate != null)
				file {
					eq("creationTime", queryDocumentRequestMessage.dateCreationCriteria.searchDate)
				}
		}
		new ACKStoredQueryMessage(documents: results)
    }
}
