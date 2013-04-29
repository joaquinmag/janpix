package com.janpix.rup.exceptions.identifier


class DuplicateAuthorityIdentifierException extends IdentifierException {
		
	public DuplicateAuthorityIdentifierException() {
		super("Ya se encuentra agregado un identificador para la entidad sanitaria")
	}
	
	public DuplicateAuthorityIdentifierException(String newMessage) {
		super(newMessage)
	}
}
