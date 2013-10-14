package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.FileAttributes
import com.janpix.servidordocumentos.dto.FileAttributesDTO

class FileAttributesAssembler {

	static FileAttributesDTO toDTO(FileAttributes domainFileAttr) {
		dto.fileAttributes.uuid = domainDocument.file.uuid
		dto.fileAttributes.repositoryId = domainDocument.file.repositoryId
		dto.fileAttributes.mimeType = domainDocument.file.mimeType
		dto.fileAttributes.creationTime = domainDocument.file.creationTime
		dto.fileAttributes.fileHash = domainDocument.file.fileHash
		dto.fileAttributes.size = domainDocument.file.size
	}

	static FileAttributes fromDTO(FileAttributesDTO dtoFileAttr) {
		
	}

}
