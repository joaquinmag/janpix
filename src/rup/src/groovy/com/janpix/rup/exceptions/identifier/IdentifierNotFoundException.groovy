package com.janpix.rup.exceptions.identifier

class IdentifierNotFoundException extends IdentifierException {
	public IdentifierNotFoundException() {
		super("No existe el identificador")
	}
	
	public IdentifierNotFoundException(String newMessage) {
		super(newMessage)
	}
}
