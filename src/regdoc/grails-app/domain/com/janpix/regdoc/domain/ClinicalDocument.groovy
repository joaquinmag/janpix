package com.janpix.regdoc.domain

class ClinicalDocument {
	String uniqueId // utilizado para referenciar otros docs
	String patientId // el paciente al que representa
	Author author // el autor del documento
	FileAttributes file // atributos que pertenecen unicamente al archivo
	DocumentState state // estado asignado por los auditores
	
	String comments // comentarios sobre el documento
	String formatCode
	String language
	
	ClinicalDocumentType type // tipo de documento
}
