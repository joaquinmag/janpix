package com.janpix.regdoc.domain

class ClinicalDocumentType {
	String name
	ClinicalDocumentType father
	
	static hasMany = [ children: ClinicalDocumentType ]
	
	static constraints = {
		name (nullable: false, unique: true)
		father (nullable: true)
	}
	
	ClinicalDocumentType getRootType() {
		if (father) 
			return this
		else
			return father.getRootType()
	}	
}
