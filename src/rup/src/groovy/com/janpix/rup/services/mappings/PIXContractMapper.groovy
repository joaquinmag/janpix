package com.janpix.rup.services.mappings

import javax.xml.bind.JAXBElement;

import org.hl7.v3.*

import com.janpix.rup.empi.Person
import com.janpix.rup.empi.PersonName
import com.janpix.rup.exceptions.MessageMappingException

/**
 * Maps domain objects to PIX Web service
 */
class PIXContractMapper {

	/**
	 * maps PRPAIN201301UV02 message to Person
	 * @param inPatientMessage {@link org.hl7.v3.PRPAIN201301UV02 PRPAIN201301UV02} object
	 * @return {@link com.janpix.rup.empi.Person Person} mapped from PRPAIN201301UV02.
	 */
	static Person mapPersonFromhl7v3AddNewPatientMessage(PRPAIN201301UV02 inPatientMessage) {
		validateHl7V3AddNewPatientMessage(inPatientMessage)
		def person = new Person()
		PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent regEvent = inPatientMessage.controlActProcess.subject[0].registrationEvent
		PRPAMT201301UV02Person patientPerson = regEvent.subject1.patient.patientPerson.getValue()
		person.givenName = new PersonName()
		person.givenName.firstName = getNameFromPatient(patientPerson, "given")?.toString()
		person.givenName.lastName = getNameFromPatient(patientPerson, "family")?.toString()
		return person
	}
	
	/**
	 * Gets first value from PN parameter
	 * @param patientPerson PRPAMT201301UV02Person
	 * @param xmlName
	 * @return
	 */
	private static String getNameFromPatient(PRPAMT201301UV02Person patientPerson, String xmlName) {
		def givenNameJaxb = null
		patientPerson.name.find { PN it ->
			givenNameJaxb = it.content.find() {
				def jaxbElement = it as JAXBElement<Serializable>
				return jaxbElement.name.localPart == xmlName
			} as JAXBElement<Serializable>
			if (givenNameJaxb != null)
				true
			else
				false
		}
		return givenNameJaxb?.value
	}
	
	private static void validateHl7V3AddNewPatientMessage(PRPAIN201301UV02 inPatientMessage) {
		if (inPatientMessage.getControlActProcess().getSubject().isEmpty())
			throw new MessageMappingException("PRPAIN201301UV02 message must contain one subject")
		if (inPatientMessage.getControlActProcess().getSubject().size() > 1)
			throw new MessageMappingException("PRPAIN201301UV02 message can't contain more than one subject at this implementation")
	}
	
}
