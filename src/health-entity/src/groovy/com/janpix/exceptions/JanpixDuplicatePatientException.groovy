package com.janpix.exceptions

class JanpixDuplicatePatientException extends JanpixException {
		public JanpixDuplicatePatientException() {
			super("Janpix Duplicate Patient Exception")
		}
		
		public JanpixDuplicatePatientException(String message) {
			super(message)
		}

		public String getMessage() {
			return super.getMessage()
		}
		
}
