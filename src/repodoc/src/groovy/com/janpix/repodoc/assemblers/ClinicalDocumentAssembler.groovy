package com.janpix.repodoc.assemblers

import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.FileAttributesDTO;

class ClinicalDocumentAssembler {

	public static ClinicalDocumentDTO toDTO(ClinicalDocument domain){
		ClinicalDocumentDTO dto = new ClinicalDocumentDTO()
		dto.fileAttributes = new FileAttributesDTO()
		
		if(domain != null){
			//dto.uniqueId = dto.id //TODO VER
			dto.name = domain.name
			dto.documentCreationStarted = domain.dateCreated
			dto.documentCreationEnded = domain.dateCreated
			dto.fileAttributes.repositoryId = "PonerUUIDDelRepositorio"
			dto.fileAttributes.uuid = domain.uuid
			dto.fileAttributes.mimeType = domain.mimeType
			dto.fileAttributes.fileHash = domain.hash 
			dto.fileAttributes.size  = domain.size
			
			dto.binaryData = domain.binaryData
		}

		return dto
	}
	
	public static ClinicalDocument fromDTO(ClinicalDocumentDTO dto){
		return null;
	}
}
