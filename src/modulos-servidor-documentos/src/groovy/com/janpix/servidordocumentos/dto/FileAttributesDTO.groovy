package com.janpix.servidordocumentos.dto

class FileAttributesDTO {
	String uuid //Identificador asignado por una Entidad Sanitaria
	String repositoryId
	String mimeType
	Date creationTime
	String fileHash
	Long size
}
