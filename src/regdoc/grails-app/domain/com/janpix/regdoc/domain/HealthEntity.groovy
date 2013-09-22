package com.janpix.regdoc.domain

class HealthEntity {
	String healthcareFacilityTypeCode
	String name
	String oid
	
	static constraints = {
		healthcareFacilityTypeCode(nullable: false)
		name(nullable: false)
		oid(nullable: false)
	}
}
