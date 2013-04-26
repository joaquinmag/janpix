package com.janpix.rup.services.contracts

class ACKMessage {
	public enum TypeCode {
		SuccededCreation,
		SuccededInsertion,
		PossibleMatchingPatientsError,
		ShortDemographicError,
		IdentifierError,
		InternalError
	}
	
	TypeCode typeCode
	String text	
	
}
