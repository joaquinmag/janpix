package ar.com.janpix.patient

import org.codehaus.groovy.grails.validation.Validateable;

@Validateable
class PatientCommand extends UserCommand {
		
	String firstname
	String lastname
	
	String mail
	
	static constraints = {
	}
	
	String getCuis(){
		return this.user
	}
	
	String toString(){
		return "${firstname}, ${lastname}"
	}
}
