package com.janpix.regdoc.domain

class ClinicalDocumentType {
	String name
	ClinicalDocumentType father
	Long idDocumentType
	
	static hasMany = [ children: ClinicalDocumentType ]
	static mappedBy = [ children: 'father' ]
	
	static constraints = {
		name (nullable: false, blank: false)
		father (nullable: true)
		idDocumentType (nullable: false, blank: false, unique: true)
	}
	
	static transients = [ 'rootType' ]
	
	ClinicalDocumentType(String name, ClinicalDocumentType documentType, Long idDocumentType) {
		this.name = name
		this.father = documentType
		this.idDocumentType = idDocumentType
	}
	
	ClinicalDocumentType getRootType() {
		if (father) 
			return this
		else
			return father.getRootType()
	}
}
