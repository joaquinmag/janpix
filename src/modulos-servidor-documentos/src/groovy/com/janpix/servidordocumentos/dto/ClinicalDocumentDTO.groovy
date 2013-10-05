package com.janpix.servidordocumentos.dto

class ClinicalDocumentDTO {
	//Utilizados por RegDoc y RepoDoc
	String uniqueId
	String name 
	FileAttributesDTO fileAttributes
	Date documentCreationStarted
	Date documentCreationEnded
	
	//Utilizados por RegDoc
	String patientId  
	AuthorDTO author 
	String stateDescription 
	String comments
	String language 
	Long typeId 
	String typeName 
	String formatName  
	
	
	byte[] binaryData //RepoDoc
}
