package com.janpix.rup.services.helpers

import org.hl7.v3.CS
import org.hl7.v3.II
import org.hl7.v3.TS

class Hl7v3MessageHelper {
	def uuidGenerator
	
	II generateUniqueII() {
		return new II(root: uuidGenerator())
	}
	
	TS buildHl7DateTime(Date date) {
		return new TS(value: "${date.getAt(Calendar.YEAR)}${date.getAt(Calendar.MONTH)}${date.getAt(Calendar.DAY_OF_MONTH)}${date.getAt(Calendar.HOUR_OF_DAY)}${date.getAt(Calendar.MINUTE)}${date.getAt(Calendar.SECOND)}")
	}
	
	II buildInteractionId(String messageRootName) {
		return new II(root: "2.16.840.1.113883.1.6", extension: messageRootName)
	}
	
	CS buildProcessingCode() {
		//TODO tomar valor Produccion, Testing y Desarrollo de la config de grails
		return new CS(code:"P")
	}
	
	CS buildProcessingModeCode() {
		//TODO tomar valor de processingmodecode de la config de grails
		return new CS(code: "T")
	}
	
	CS buildAcceptAckCode() {
		//TODO tomar valor de acceptAckCode de la config de grails
		return new CS(code: "NE")
	}

}
