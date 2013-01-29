package com.janpix.rup.exceptions

import com.janpix.rup.empi.Person


class ShortDemographicDataException extends Exception {
	String message
	Person person

}
