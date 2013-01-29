package com.janpix.rup.exceptions

import com.janpix.rup.empi.Patient


class IdentifierException extends Exception {
	static Integer TYPE_ENTITY_DUPLICATE = 1
	Integer type
	String message
}
