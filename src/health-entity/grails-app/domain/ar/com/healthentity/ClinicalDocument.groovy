package ar.com.healthentity

class ClinicalDocument {
	
	String filename
	Date dateCreated
	Date lastUpdated
	
	String mimeType
	long size
	
	//FormatType formatType
	
	String fileLocation
	
	
    static constraints = {
		filename(nullable:false,blank:false)
		mimeType(nullable:false,blank:false)
		size(nullable:false)
		fileLocation(nullable:false,blank:false)
    }
}
