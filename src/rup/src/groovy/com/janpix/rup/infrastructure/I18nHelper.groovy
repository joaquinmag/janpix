package com.janpix.rup.infrastructure
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.codehaus.groovy.grails.commons.ApplicationHolder as AH

class I18nHelper {
	static public def getDefaultMessageResolver() {
		return {label, Object[] args ->
			def messageSource = AH.application.mainContext.messageSource
			//String label = (String)arguments[0]
			messageSource.getMessage(new DefaultMessageSourceResolvable(label),null)
		}
	} 
}
