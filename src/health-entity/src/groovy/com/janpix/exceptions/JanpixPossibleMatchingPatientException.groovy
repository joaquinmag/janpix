package com.janpix.exceptions

class JanpixPossibleMatchingPatientException extends JanpixException {
		public JanpixPossibleMatchingPatientException() {
			super("Janpix Possible Matching Patient Exception")
		}
		
		public JanpixPossibleMatchingPatientException(String message) {
			super(message)
		}

		public String getMessage() {
			return super.getMessage()
		}
		
}
