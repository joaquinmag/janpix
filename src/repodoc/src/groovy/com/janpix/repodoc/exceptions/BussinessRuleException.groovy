package com.janpix.repodoc.exceptions

class BussinessRuleException extends RuntimeException {
	
	public BussinessRuleException() {
		super("General BussinessRule exception throwed.")
	}
	
	public BussinessRuleException(String message) {
		super(message)
	}
	
	@Override
	public String getMessage() {
		return super.getMessage()
	}
}