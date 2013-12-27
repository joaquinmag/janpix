package ar.com.healthentity

enum FormatType {
	PDF,
	ScannedLab,
	ODT,
	Picture,
	XML
}

class ClinicalDocument {
	
	String filename
	Date dateCreated
	Date lastUpdated
	
	String mimeType
	long size
	FormatType format
	String fileLocation
	
	static mapping = {
		format enumType: "values"
	}
	
    static constraints = {
		filename(nullable:false,blank:false)
		mimeType(nullable:false,blank:false)
		size(nullable:false)
		fileLocation(nullable:false,blank:false)
		format(nullable: true)
    }
}
