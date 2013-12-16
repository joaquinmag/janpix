package ar.com.janpix.patient

import org.codehaus.groovy.grails.validation.Validateable;

@Validateable
class PatientCommand {
	// TODO ver de inyectar janpixPixManager para obtener datos de usuario
	
	String cuis
	String user
	String pass
	
	String firstname
	String lastname
	
	String mail
	
	static constraints = {
	}
	
	String toString(){
		return "${firstname}, ${lastname}"
	}
}
