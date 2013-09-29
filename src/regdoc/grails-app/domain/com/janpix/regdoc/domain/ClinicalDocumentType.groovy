package com.janpix.regdoc.domain

class ClinicalDocumentType {
	String name
	ClinicalDocumentType father
	
	static hasMany = [ children: ClinicalDocumentType ]
	static mappedBy = [ children: 'father' ]
	
	static constraints = {
		name (nullable: false, blank: false)
		father (nullable: true)
	}
	
	static transients = [ 'rootType' ]
	
	ClinicalDocumentType(String name, ClinicalDocumentType documentType) {
		this.name = name
		this.father = documentType
	}
	
	ClinicalDocumentType getRootType() {
		if (father) 
			return this
		else
			return father.getRootType()
	}
}
