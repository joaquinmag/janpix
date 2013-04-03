package com.janpix.rup.exceptions.identifier

import com.janpix.rup.empi.Patient


class IdentifierException extends Exception {
	public IdentifierException(String message) {
		super(message)
	}
	
	public String getMessage() {
		return super.getMessage()
	}
}
