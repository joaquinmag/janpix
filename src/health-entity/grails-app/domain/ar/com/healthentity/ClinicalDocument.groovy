package ar.com.healthentity

class ClinicalDocument {
	
	String filename
	Date dateCreated
	Date lastUpdated
	
	String mimeType
	long size
	
	//FormatType formatType
	
	byte[] binaryData
	
	
    static constraints = {
		filename(nullable:false,blank:false)
		mimeType(nullable:false,blank:false)
		size(nullable:false)
		binaryData(nullable:false,blank:false)
    }
}
