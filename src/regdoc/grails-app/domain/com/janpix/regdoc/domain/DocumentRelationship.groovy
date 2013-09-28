package com.janpix.regdoc.domain

enum DocumentRelationshipType {
	Replace,
	Append,
	Sign
}

class DocumentRelationship {

	DocumentRelationshipType type
	ClinicalDocument parentDocument
	ClinicalDocument relatedDocument

}
