package com.janpix.repodoc.exceptions

class RegistrationServiceException extends RuntimeException {
	
	public RegistrationServiceException() {
		super("General RegistrationService exception throwed.")
	}
	
	public RegistrationServiceException(String message) {
		super(message)
	}
	
}