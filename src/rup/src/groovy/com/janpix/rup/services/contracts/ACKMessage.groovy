package com.janpix.rup.services.contracts

import com.janpix.rup.empi.Patient

class ACKMessage {
	public enum TypeCode {
		SuccededCreation(1),
		SuccededInsertion(2),
		SuccededUpdated(3),
		PossibleMatchingPatientsError(4),
		ShortDemographicError(5),
		IdentifierError(6),
		InternalError(7),
		DuplicatePatientError(8),
		DontExistingPatientError(9),
		SuccededQuery(10)
		
		TypeCode(int exceptionCode) { this.exceptionCode = exceptionCode }
		private final int exceptionCode
		public int exceptionCode() { return this.exceptionCode }
	}
	
	TypeCode typeCode
	String text
	Patient patient	
	
}
