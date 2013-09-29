package com.janpix.regdoc.domain

class Author {
	HealthEntity institution
	Person person
	
	static constraints = {
		institution(nullable: false)
		person(nullable: false)
	}
}
