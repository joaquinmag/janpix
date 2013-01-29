package com.janpix.rup.exceptions

import com.janpix.rup.empi.Patient


class ExistingPatientException extends Exception {
	String message
	Patient patient

}
