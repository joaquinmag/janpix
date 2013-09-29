package com.janpix.regdoc.domain

import com.janpix.regdoc.infrastructure.DocumentStateUserType

enum FormatType {
	PDF,
	ScannedLab
}

class ClinicalDocument {
	String uniqueId // Utilizado para referenciar otros docs
	String patientId // El paciente al que representa
	Author author // El autor del documento
	FileAttributes file // Atributos que pertenecen unicamente al archivo
	DocumentState state // Estado asignado por los auditores

	String comments // Comentarios sobre el documento
	String language

	Date documentCreationStarted
	Date documentCreationEnded

	ClinicalDocumentType documentType // Tipo de documento
	String format // Indica el formato del documento. Tiene algo que ver con el formato del archivo, pero también tiene que ver con el tipo de documento.

	DocumentRelationship relatedFrom
	static hasMany = [
		relatedTo: DocumentRelationship
	]
	
	static mappedBy = [ relatedTo: 'parentDocument', relatedFrom: 'relatedDocument' ]

	static mapping = {
		state type: DocumentStateUserType, {
			column name: "state"
		}
	}
	
	static constraints = {
		uniqueId(nullable: false, unique: true, blank: false)
		patientId(nullable: false, blank: false)
		author(nullable: false)
		file(nullable: false)
		state(nullable: false)
		comments(nullable: true, blank: false)
		language(nullable: false, blank: false)
		documentCreationStarted(nullable: true, validator: { val, obj ->
			obj.documentCreationEnded >= val
		})
		documentCreationEnded(nullable: true)
		documentType(nullable: false)
		format(nullable: false, inList: FormatType.values())
	}
	
	def relate(ClinicalDocument document, DocumentRelationshipType relationType) {
		//def documentRelation = new DocumentRelationship(
	}
	
	boolean equals(other) {
		if (this.uniqueId == other.uniqueId)
			return true
		return false
	}
}
