package com.janpix.regdoc.domain

class Person {
	String name
	String specialty
	String role
	String email
	String phone
	
	static constraints = {
		name(nullable: false, blank: false)
		specialty(nullable: true, blank: false)
		role(nullable: true, blank: false)
		phone(nullable: true, blank: false)
		email(nullable: true, email: true, blank: false)
	}
}
