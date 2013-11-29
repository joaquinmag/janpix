package com.janpix.exceptions

class StudyDoesNotExistsException extends BusinessRuleException {
	public StudyDoesNotExistsException() {
		super("Study does not exists.")
	}
	
	public StudyDoesNotExistsException(String message) {
		super(message)
	}
}
