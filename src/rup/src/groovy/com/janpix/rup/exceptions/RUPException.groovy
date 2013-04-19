package com.janpix.rup.exceptions

class RUPException extends RuntimeException {
	
	public RUPException() {
		super("General RUP exception throwed.")
	}
	
	public RUPException(String message) {
		super(message)
	}
	
	@Override
	public String getMessage() {
		return super.getMessage()
	}
}
