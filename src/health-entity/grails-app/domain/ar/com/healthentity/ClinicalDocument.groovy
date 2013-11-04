package ar.com.healthentity

class ClinicalDocument {
	
	String name
	Date dateCreated
	Date lastUpdated
	
	String mimeType
	long size
	
	byte[] binaryData
	
	
    static constraints = {
		name(nullable:false,blank:false)
		mimeType(nullable:false,blank:false)
		size(nullable:false)
		binaryData(nullable:false,blank:false)
    }
}
