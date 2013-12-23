package com.janpix.exceptions

class LoginException extends RuntimeException {
		public LoginException() {
			super("General Login exception throwed.")
		}
		
		public LoginException(String message) {
			super(message)
		}

		public String getMessage() {
			return super.getMessage()
		}
		
}