package com.janpix.rup.services.contracts

import org.springframework.aop.aspectj.RuntimeTestWalker.ThisInstanceOfResidueTestVisitor;

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
		DontExistingPatientError(9)
		
		TypeCode(int exceptionCode) { this.exceptionCode = exceptionCode }
		private final int exceptionCode
		public int exceptionCode() { return this.exceptionCode }
	}
	
	TypeCode typeCode
	String text	
	
}
