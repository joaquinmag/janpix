package com.janpix.regdoc.domain

enum DocumentRelationshipType {
	Replace,
	Append
}

class DocumentRelationship {

	String type
	ClinicalDocument parentDocument
	ClinicalDocument relatedDocument
	
	static constraints = {
		type (nullable: false, inList: DocumentRelationshipType.values())
		parentDocument(nullable: true, validator: { val, obj ->
			!(obj.relatedDocument == val)
		})
		relatedDocument(nullable: false)
	}

}
