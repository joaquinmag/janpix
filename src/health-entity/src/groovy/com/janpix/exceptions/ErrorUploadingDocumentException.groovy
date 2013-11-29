package com.janpix.exceptions

import com.janpix.webclient.repodoc.TypeCode

class ErrorUploadingDocumentException extends BusinessRuleException {
	ErrorUploadingDocumentException(TypeCode typeCode, String typeCodeMsg) {
		super("Error subiendo el documento. typeCode: ${typeCode}, msg: ${typeCodeMsg}.")
	}
	ErrorUploadingDocumentException(Throwable th) {
		super("Excepcion subiendo el documento.", th)
	}
}
