package ar.com.janpix.patient

import org.codehaus.groovy.grails.validation.Validateable;

@Validateable
class StudyCommand {
	String uniqueId // Id asignado por el RegDoc
	String name // Nombre del estudio
	Date date
	String comments
	String state // TODO hacer un enum
	String type // TODO ver como implementar
	
	//PatientCommand patient
	//FileAttributesCommand fileAttributes
	AuthorCommand author
	
	
}
