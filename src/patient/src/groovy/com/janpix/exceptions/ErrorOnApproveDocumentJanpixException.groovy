package com.janpix.exceptions

class ErrorOnApproveDocumentJanpixException extends JanpixException {
		public ErrorOnApproveDocumentJanpixException() {
			super("ErrorOnApproveDocumentJanpixException")
		}
		
		public ErrorOnApproveDocumentJanpixException(String message) {
			super(message)
		}

		public String getMessage() {
			return super.getMessage()
		}
		
}
