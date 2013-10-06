package com.janpix.servidordocumentos.dto

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class FileAttributesDTO {
	
	@XmlElement(required=true)
	String uuid //Identificador asignado por una Entidad Sanitaria
	
	@XmlElement
	String repositoryId
	
	@XmlElement(required=true)
	String mimeType
	
	@XmlElement
	Date creationTime
	
	@XmlElement
	String fileHash
	
	@XmlElement
	Long size
}
