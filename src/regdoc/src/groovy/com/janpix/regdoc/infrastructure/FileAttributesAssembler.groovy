package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.FileAttributes
import com.janpix.servidordocumentos.dto.FileAttributesDTO

class FileAttributesAssembler {

	static FileAttributesDTO toDTO(FileAttributes domainFileAttr) {
		def dto = new FileAttributesDTO()
		dto.uuid = domainFileAttr.uuid
		dto.repositoryId = domainFileAttr.repositoryId
		dto.mimeType = domainFileAttr.mimeType
		dto.creationTime = domainFileAttr.creationTime
		dto.fileHash = domainFileAttr.fileHash
		dto.size = domainFileAttr.size
		dto.filename = domainFileAttr.filename
		
		return dto
	}

	static FileAttributes fromDTO(FileAttributesDTO dto) {
		def attr = new FileAttributes()
		attr.uuid = dto.uuid
		attr.filename = dto.filename
		attr.repositoryId = dto.repositoryId
		attr.mimeType = dto.mimeType
		attr.creationTime = dto.creationTime
		attr.size = dto.size
		attr.fileHash = dto.fileHash
		
		return attr
	}

}
