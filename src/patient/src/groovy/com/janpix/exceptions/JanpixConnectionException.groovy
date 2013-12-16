package com.janpix.exceptions

class JanpixConnectionException extends JanpixException {
		public JanpixConnectionException() {
			super("Janpix Connection error. Check your configuration of WS connections")
		}
		
		public JanpixConnectionException(String message) {
			super(message)
		}

		public String getMessage() {
			return super.getMessage()
		}
		
}
