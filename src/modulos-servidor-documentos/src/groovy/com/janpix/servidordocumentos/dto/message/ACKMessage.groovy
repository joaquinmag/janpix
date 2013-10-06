package com.janpix.servidordocumentos.dto.message

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

@XmlRootElement
class ACKMessage {

	@XmlRootElement
	public enum TypeCode {
		SuccededCreation(1),
		// TODO definir
		
		TypeCode(int exceptionCode) { this.exceptionCode = exceptionCode }
		private final int exceptionCode
		public int exceptionCode() { return this.exceptionCode }
	}
	
	@XmlElement
	TypeCode typeCode
	
	@XmlElement(required=true)
	ClinicalDocumentDTO clinicalDocument
	
	
}
