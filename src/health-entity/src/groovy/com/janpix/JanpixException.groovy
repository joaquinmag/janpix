package com.janpix

class JanpixException extends RuntimeException {
		public JanpixException() {
			super("General Janpix exception throwed.")
		}
		
		public JanpixException(String message) {
			super(message)
		}
		
		@Override
		public String getMessage() {
			return super.getMessage()
		}
}
