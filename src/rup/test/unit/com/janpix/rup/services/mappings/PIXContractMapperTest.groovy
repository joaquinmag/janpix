package com.janpix.rup.services.mappings;

import grails.test.mixin.*

import javax.xml.bind.JAXBElement
import javax.xml.namespace.QName

import org.hl7.v3.EnGiven
import org.hl7.v3.PN
import org.hl7.v3.PRPAIN201301UV02
import org.hl7.v3.PRPAIN201301UV02MFMIMT700701UV01ControlActProcess
import org.hl7.v3.PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent
import org.hl7.v3.PRPAIN201301UV02MFMIMT700701UV01Subject1
import org.hl7.v3.PRPAIN201301UV02MFMIMT700701UV01Subject2
import org.hl7.v3.PRPAMT201301UV02Patient
import org.hl7.v3.PRPAMT201301UV02Person

class PIXContractMapperTest {

	@Test
	public void whenHl7AddPatientObjectHasNameThenCheckItReturnsAPatientDomainObjectWithTheSameName() {
		
		//BUILD ADD PATIENT MESSAGE WITH JUAN FIRST NAME AND PEREZ GARCIA LAST NAME
		def patientName = new PN()
		patientName.content.add(new JAXBElement<EnGiven>(new QName("given"), JAXBElement.class, "Juan"))
		patientName.content.add(new JAXBElement<EnGiven>(new QName("family"), JAXBElement.class, "Perez García"))
		def patientPerson = new PRPAMT201301UV02Person()
		patientPerson.name.add(patientName)
		def subjectControlActProcess = new PRPAIN201301UV02MFMIMT700701UV01Subject1()
		subjectControlActProcess.registrationEvent = new PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent()
		subjectControlActProcess.registrationEvent.subject1 = new PRPAIN201301UV02MFMIMT700701UV01Subject2()
		subjectControlActProcess.registrationEvent.subject1.patient = new PRPAMT201301UV02Patient()
		subjectControlActProcess.registrationEvent.subject1.patient.patientPerson = new JAXBElement<PRPAMT201301UV02Person>(new QName("patientPerson"), JAXBElement.class, patientPerson)
		def inPatientMessage = new PRPAIN201301UV02()
		inPatientMessage.controlActProcess = new PRPAIN201301UV02MFMIMT700701UV01ControlActProcess()
		inPatientMessage.controlActProcess.subject.add(subjectControlActProcess)
		
		def patient = PIXContractMapper.mapPersonFromhl7v3AddNewPatientMessage(inPatientMessage)

		assert patient.givenName.firstName == "Juan"
	}

}
