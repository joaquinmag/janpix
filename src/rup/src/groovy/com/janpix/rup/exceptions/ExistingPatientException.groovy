package com.janpix.rup.exceptions

import com.janpix.rup.empi.Patient


class ExistingPatientException extends RUPException {
	String message
	Patient patient

}
