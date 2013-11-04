package com.janpix.exceptions

class JanpixException extends RuntimeException {
		public JanpixException() {
			super("General Janpix exception throwed.")
		}
		
		public JanpixException(String message) {
			super(message)
		}

		public String getMessage() {
			return super.getMessage()
		}
		
}
