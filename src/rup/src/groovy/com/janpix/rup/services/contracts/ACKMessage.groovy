package com.janpix.rup.services.contracts

import org.springframework.aop.aspectj.RuntimeTestWalker.ThisInstanceOfResidueTestVisitor;

class ACKMessage {
	public enum TypeCode {
		SuccededCreation(1),
		SuccededInsertion(2),
		PossibleMatchingPatientsError(3),
		ShortDemographicError(4),
		IdentifierError(5),
		InternalError(6)
		
		TypeCode(int exceptionCode) { this.exceptionCode = exceptionCode }
		private final int exceptionCode
		public int exceptionCode() { return this.exceptionCode }
	}
	
	TypeCode typeCode
	String text	
	
}
