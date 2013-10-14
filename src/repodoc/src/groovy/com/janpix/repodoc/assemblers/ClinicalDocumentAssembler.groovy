package com.janpix.repodoc.assemblers

import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.FileUtils
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.FileAttributesDTO

class ClinicalDocumentAssembler {

	public static ClinicalDocumentDTO toDTO(ClinicalDocument domain){
		if(domain == null)
			return null
			
		ClinicalDocumentDTO dto = new ClinicalDocumentDTO()
		dto.fileAttributes = new FileAttributesDTO()
		
		
		dto.uniqueId = domain.id.toString()
		dto.name = domain.name
		dto.documentCreationStarted = domain.dateCreated
		dto.documentCreationEnded = domain.dateCreated
		dto.fileAttributes.repositoryId = "PonerUUIDDelRepositorio"
		dto.fileAttributes.uuid = domain.uuid
		dto.fileAttributes.mimeType = domain.mimeType
		dto.fileAttributes.fileHash = domain.hash 
		dto.fileAttributes.size  = domain.size
			
		dto.binaryData = FileUtils.ByteArrayToDataHandler(domain.binaryData,"application/octet-stream")
		

		return dto
	}
	
	public static ClinicalDocument fromDTO(ClinicalDocumentDTO dto) {
		if(dto == null)
			return null
		
		ClinicalDocument document = new ClinicalDocument()
		document.name = dto.name
		document.binaryData = FileUtils.DataHandlerToByteArray(dto.binaryData);
		document.dateCreated = dto.documentCreationStarted
		document.uuid = dto.fileAttributes?.uuid
		document.mimeType = dto.fileAttributes?.mimeType
		document.hash = dto.fileAttributes?.fileHash
		document.size = dto.fileAttributes?.size
		
		return document
	}
	
	
}
