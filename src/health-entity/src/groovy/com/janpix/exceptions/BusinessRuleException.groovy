package com.janpix.exceptions

class BusinessRuleException extends RuntimeException {

	public BusinessRuleException() {
		super("Health Entity exception throwed.")
	}
	
	public BusinessRuleException(String message) {
		super(message)
	}
	
	public BusinessRuleException(String message, Throwable th) {
		super(message, th)
	}
	
	public String getMessage() {
		return super.getMessage()
	}
	
}
