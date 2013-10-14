package com.janpix.repodoc.exceptions

class MetadataException extends RuntimeException {
	
	public MetadataException() {
		super("General Metadata exception throwed.")
	}
	
	public MetadataException(String message) {
		super(message)
	}
	
}