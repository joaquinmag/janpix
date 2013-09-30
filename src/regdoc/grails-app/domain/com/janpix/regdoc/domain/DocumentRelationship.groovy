package com.janpix.regdoc.domain

enum DocumentRelationshipType {
	Replace,
	Append,
	Sign,
	Transform
}

class DocumentRelationship {

	String type
	ClinicalDocument parentDocument
	ClinicalDocument relatedDocument
	
	static constraints = {
		type (nullable: false, inList: DocumentRelationshipType.values())
		parentDocument(nullable: false, validator: { val, obj ->
			!(obj.relatedDocument == val)
		})
		relatedDocument(nullable: false)
	}
	
	public DocumentRelationship(ClinicalDocument parentDocument, ClinicalDocument relatedDocument, DocumentRelationshipType type) {
		this.parentDocument = parentDocument
		this.relatedDocument = relatedDocument
		this.type = type.toString()
	}
	
	boolean equals(other) {
		if (type == other.type 
			&& parentDocument.uniqueId == other.parentDocument.uniqueId
			&& relatedDocument.uniqueId == other.relatedDocument.uniqueId)
			return true
		return false
	}
	
	int hashCode() {
		int hash = 1
		hash = hash*13 + type.hashCode()
		hash = hash*17 + parentDocument.hashCode()
		hash = hash*31 + relatedDocument.hashCode()
		return hash
	}

}
