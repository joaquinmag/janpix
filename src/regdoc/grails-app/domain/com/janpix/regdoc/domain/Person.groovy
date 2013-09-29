package com.janpix.regdoc.domain

class Person {
	String identifier // matricula
	String name
	String specialty
	String role
	String email
	String phone
	
	static constraints = {
		identifier(nullable: false, blank: false)
		name(nullable: false, blank: false)
		specialty(nullable: true, blank: false)
		role(nullable: true, blank: false)
		phone(nullable: true, blank: false)
		email(nullable: true, email: true, blank: false)
	}
}
