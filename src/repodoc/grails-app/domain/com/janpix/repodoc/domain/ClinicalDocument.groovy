package com.janpix.repodoc.domain

//import org.hibernate.lob.BlobImpl;
import org.bson.types.ObjectId

class ClinicalDocument {
	ObjectId id //Id asignado por el sistema compatible con mongo 
	String name
	byte[] binaryData
	
	String uuid //Identificador asignado por la Entidad Sanitaria
	String mimeType
	Date dateCreated
	String hash
	Long size
	
	static mapWith = "mongo"
	
	static constraints = {
		id(unique:true)
		name(nullable:false)
		binaryData(nullable:false)
		uuid(unique:true,nullable:false)
		mimeType(nullable:false)
		dateCreated(nullable:false)
		hash(nullable:false)
		size(nullable:false)
	}

}
