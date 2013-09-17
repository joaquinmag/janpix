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
	
	PracticeSetting practiceSetting
	KindOfDocument kindOfDocument // tipo de documento
	Confidentiality confidentiality // estado de confidencialidad
}
