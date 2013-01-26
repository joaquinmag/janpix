package com.janpix.rup.empi

import java.util.Date;

class Patient extends Person {
	String uniqueId //TODO definir si uso otro ademas del que otorga grails
	Set<Identifier> identifiers = []
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [identifiers:Identifier]
	
    static constraints = {
    }
}
