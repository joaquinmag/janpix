package com.janpix.regdoc.domain

import com.janpix.regdoc.infrastructure.DocumentStateUserType
import com.janpix.regdoc.exceptions.*

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
	FormatType format // Indica el formato del documento. Tiene algo que ver con el formato del archivo, pero también tiene que ver con el tipo de documento.

	DocumentRelationship relatedFrom
	static hasMany = [
		relatedTo: DocumentRelationship
	]
	boolean isSigned = false
	boolean isVersionUpdated = false
	
	static mappedBy = [ relatedTo: 'parentDocument', relatedFrom: 'relatedDocument' ]

	static mapping = {
		state type: DocumentStateUserType, {
			column name: "state"
		}
		format enumType: "values"
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
		format(nullable: false)
		relatedFrom(nullable: true, validator: { val, obj ->
			( val == null || !obj.relatedTo.contains(val) )
		})
	}
	
	def sign(document) {
		if (document.state.isDeleted())
			throw new InvalidDocumentStateException()
			
		if (isSigned)
			throw new DocumentAlreadySignedException()
		
		// solo se permite una firma por documento.
		
		relate(document, DocumentRelationshipType.Sign)
		
		isSigned = true
	}
	
	def append(document) {
		if (document.state.isDeprecated() || document.state.isDeleted())
			throw new InvalidDocumentStateException()
		
		// todos los documentos pueden tener muchos append, por lo que no se valida contra otras relaciones existentes.
		
		relate(document, DocumentRelationshipType.Append)
	}
	
	/***
	 * Actualiza la versión del documento generando una relación entre documentos.
	 * Por defecto la relación entre documentos es de transformación. Se puede indicar como una relación de reemplazo indicando el segundo parámetro en true.
	 * Solo se admite una nueva relación de actualización de versión por documento.
	 */
	def updateVersion(document, isReplaced = false) {
		if (!isReplaced && document.state.isDeleted())
			throw new InvalidDocumentStateException()

		if (isVersionUpdated)
			throw new DocumentAlreadyUpdatedVersionException()
		
		relate(document, (isReplaced) ? DocumentRelationshipType.Replace : DocumentRelationshipType.Transform) 
		
		isVersionUpdated = true
	}
	
	private void relate(document, relationType) {
		if (document.relatedFrom)
			throw new DocumentAlreadyRelatedException()
		
		def documentRelation = new DocumentRelationship(this, document, relationType)
		relatedTo.add(documentRelation)
		document.relatedFrom = documentRelation
	}
	
	boolean equals(other) {
		if (this.uniqueId == other.uniqueId)
			return true
		return false
	}
	
	int hashCode() {
		int hash = 1
		hash = hash*17 + uniqueId.hashCode()
		hash = hash*31 + patientId.hashCode()
		return hash
	}
}
