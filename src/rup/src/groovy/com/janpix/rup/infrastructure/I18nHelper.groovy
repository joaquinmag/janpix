package com.janpix.rup.infrastructure
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.codehaus.groovy.grails.commons.ApplicationHolder as AH

class I18nHelper {
	static public def getDefaultMessageResolver() {
		return { String label ->
			def messageSource = AH.application.mainContext.messageSource
			messageSource.getMessage(new DefaultMessageSourceResolvable(label),null)
		}
	} 
}
