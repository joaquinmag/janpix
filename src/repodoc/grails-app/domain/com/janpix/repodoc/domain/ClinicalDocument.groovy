package com.janpix.repodoc.domain

//import org.hibernate.lob.BlobImpl;
import org.bson.types.ObjectId

class ClinicalDocument {
	ObjectId id //Id asignado por el sistema compatible con mongo 
	String name
	byte[] binaryData
	Date dateCreated
	
	String uuid //Identificador asignado por la Entidad Sanitaria
	String mimeType
	Date dateAssigned // Fecha asignada por la Enitdad Sanitaria
	String hash
	Long size
	
	static mapWith = "mongo"
	
	static constraints = {
		id(unique:true)
		name(nullable:false)
		binaryData(nullable:false)
		uuid(nullable:false)
		mimeType(nullable:false)
		dateAssigned(nullable:false)
		hash(nullable:false)
		size(nullable:false)
	}

}
