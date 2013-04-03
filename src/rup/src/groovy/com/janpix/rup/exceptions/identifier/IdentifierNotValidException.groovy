package com.janpix.rup.exceptions.identifier

class IdentifierNotValidException extends IdentifierException {
	public IdentifierNotValidException() {
		super("Identificador no válido")
	}
	
	public IdentifierNotValidException(String newMessage) {
		super(newMessage)
	}
}
