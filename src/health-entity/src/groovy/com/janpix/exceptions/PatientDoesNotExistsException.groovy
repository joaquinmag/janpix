package com.janpix.exceptions

class PatientDoesNotExistsException extends BusinessRuleException {
	public PatientDoesNotExistsException() {
		super("Patient does not registered.")
	}
	
	public PatientDoesNotExistsException(String message) {
		super(message)
	}
}
