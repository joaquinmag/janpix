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
		patientPerson.name.each { PN it ->
			def givenNameJaxb = it.content.find() { 
				def jaxbElement = it as JAXBElement<Serializable>
				return jaxbElement.name.localPart == "given" 
			} as JAXBElement<Serializable>
			person.givenName = new PersonName(firstName: "${givenNameJaxb.value}") 
		}
		return person
	}
	
	private static void validateHl7V3AddNewPatientMessage(PRPAIN201301UV02 inPatientMessage) {
		if (inPatientMessage.getControlActProcess().getSubject().isEmpty())
			throw new MessageMappingException("PRPAIN201301UV02 message must contain one subject")
		if (inPatientMessage.getControlActProcess().getSubject().size() > 1)
			throw new MessageMappingException("PRPAIN201301UV02 message can't contain more than one subject at this implementation")
	}
	
}
