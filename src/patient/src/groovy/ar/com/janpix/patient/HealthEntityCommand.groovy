package ar.com.janpix.patient

import org.codehaus.groovy.grails.validation.Validateable;

@Validateable
class HealthEntityCommand {
	
	String name
	String oid
	
	String toString(){
		return "${name}"
	}
}
