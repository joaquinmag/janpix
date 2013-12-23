package ar.com.janpix.patient

import org.codehaus.groovy.grails.validation.Validateable;

@Validateable
class UserCommand {
	
	String user
	String pass
	
	static constraints = {
	}
	
	String toString(){
		return "${user}"
	}
}
