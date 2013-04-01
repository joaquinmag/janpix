package com.janpix.rup.exceptions

import com.janpix.rup.empi.Patient


class IdentifierException extends Exception {
	static Integer TYPE_ENTITY_DUPLICATE = 1
	static Integer TYPE_VALIDATE_ERROR = 2
	static Integer TYPE_NOTFOUND = 3	
	Integer type
	String message
}
