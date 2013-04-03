package com.janpix.rup.exceptions

import com.janpix.rup.empi.Patient


class IdentifierException extends Exception {
	static Integer TYPE_VALIDATE_ERROR = 2
	Integer type
	String message
	
	public IdentifierException(int type, String message) {
		super(message)
		this.type = new Integer(type)
		this.message = message
	}
	
	public IdentifierException(String message) {
		super(message)
	}
}
