package com.janpix.servidordocumentos.dto.message

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

@XmlRootElement
class ACKMessage {

	@XmlRootElement
	public enum TypeCode {
		SuccededInsertion(1),
		SuccededRegistration(2),
		SuccededQuery(3),
		SuccededRetrive(4),
		MetadataError(5),
		ValidationError(6),
		InternalError(7),
		RegistrationError(8)
		// TODO definir los faltantes
		
		TypeCode(int exceptionCode) { this.exceptionCode = exceptionCode }
		private final int exceptionCode
		public int exceptionCode() { return this.exceptionCode }
	}
	
	@XmlElement
	TypeCode typeCode
	
	@XmlElement
	String text
	
	@XmlElement(required=true)
	ClinicalDocumentDTO clinicalDocument
	
	
}
