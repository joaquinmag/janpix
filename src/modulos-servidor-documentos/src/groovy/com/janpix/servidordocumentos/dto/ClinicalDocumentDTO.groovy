package com.janpix.servidordocumentos.dto

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class ClinicalDocumentDTO {
	//Utilizados por RegDoc y RepoDoc
	@XmlElement //Interno
	String uniqueId
	
	@XmlElement(required = true)
	String name
	
	@XmlElement(required = true)
	FileAttributesDTO fileAttributes
	
	@XmlElement(required = true)
	Date documentCreationStarted
	
	@XmlElement
	Date documentCreationEnded
	
	//Utilizados por RegDoc
	@XmlElement(required = true)
	String patientId
	
	@XmlElement(required = true)
	AuthorDTO author
	
	@XmlElement
	String stateDescription
	
	@XmlElement
	String comments
	
	@XmlElement
	String language
	
	@XmlElement
	Long typeId
	
	@XmlElement
	String typeName
	
	@XmlElement
	String formatName  
	
	@XmlElement
	byte[] binaryData //RepoDoc
}
