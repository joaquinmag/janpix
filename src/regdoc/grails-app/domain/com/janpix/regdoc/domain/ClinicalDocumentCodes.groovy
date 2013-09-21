package com.janpix.regdoc.domain

class ClinicalDocumentType {
	String name
	ClinicalDocumentType father
	
	static hasMany = [ children: ClinicalDocumentType ]
	
	static constraints = {
		name (nullable: false, unique: true)
		father (nullable: true)
	}
	
	ClinicalDocumentType(String name, ClinicalDocumentType documentType) {
		this.name = name
		this.documentType = documentType
	}
	
	ClinicalDocumentType getRootType() {
		if (father) 
			return this
		else
			return father.getRootType()
	}
}


enum FormatType {
	PDF,
	ScannedLab
}
