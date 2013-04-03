package com.janpix.rup.exceptions

class DuplicateIdentifierException extends IdentifierException {
	public DuplicateIdentifierException() {
		super("Ya se encuentra agregado un identificador para la entidad sanitaria")
	}
	
	public DuplicateIdentifierException(String newMessage) { 
		super(newMessage)
	}
}
