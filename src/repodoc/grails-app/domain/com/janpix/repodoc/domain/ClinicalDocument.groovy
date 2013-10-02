package com.janpix.repodoc.domain

//import org.hibernate.lob.BlobImpl;
//import org.bson.types.ObjectId;

class ClinicalDocument {
	//ObjectId id //Id asignado por el sistema compatible con mongo 
	String name
	byte[] binaryData
	
	String uuid //Identificador asignado por la Entidad Sanitaria
	String mimeType
	Date dateCreated
	String hash
	Long size
	
	static constraints = {
		
	}
	/*def setData(InputStream is, long length) {
		binaryDataSize = length
		  binaryData = new BlobImpl(is, (int)length)
	  }
	 
	  InputStream getData() {
		  return binaryData?.binaryStream
	  }*/
}
