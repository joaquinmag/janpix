package ar.com.janpix.patient

import org.codehaus.groovy.grails.validation.Validateable;

@Validateable
class AuthorCommand {
	
	HealthEntityCommand healthEntity
	
	String toString(){
		return "${healthEntity}"
	}
}
