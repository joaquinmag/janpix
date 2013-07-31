package com.janpix.rup.services.mappings;

import grails.test.mixin.*

import com.janpix.hl7dto.hl7.v3.datatypes.CE
import com.janpix.hl7dto.hl7.v3.datatypes.PN
import com.janpix.hl7dto.hl7.v3.datatypes.TS
import com.janpix.hl7dto.hl7.v3.messages.AddPatientOperationMessage
import com.janpix.hl7dto.hl7.v3.messages.Patient
import com.janpix.hl7dto.hl7.v3.messages.PatientControlActProcess
import com.janpix.hl7dto.hl7.v3.messages.Person
import com.janpix.hl7dto.hl7.v3.messages.RegistrationEvent
import com.janpix.hl7dto.hl7.v3.messages.Subject1
import com.janpix.hl7dto.hl7.v3.messages.Subject2
import com.janpix.rup.pixmanager.PlaceService

@TestFor(PIXContractMapper)
@Mock([PlaceService])
class PIXContractMapperTest {
	
	public void testWhenHl7AddPatientObjectHasNameThenCheckThatTheMapMethodReturnsAPatientDomainObjectWithTheSameName() {
		def pixContractMapper = new PIXContractMapper()
		
		//BUILD ADD PATIENT MESSAGE WITH JUAN FIRST NAME AND PEREZ GARCIA LAST NAME
		def patientName = new PN()
		patientName.given = "Juan"
		patientName.family = "Perez García"
		def patientPerson = new Person()
		patientPerson.name.add(patientName)
		patientPerson.administrativeGenderCode = new CE(code: "M") 
		def subjectControlActProcess = new Subject1()
		subjectControlActProcess.registrationEvent = new RegistrationEvent()
		subjectControlActProcess.registrationEvent.subject1 = new Subject2()
		subjectControlActProcess.registrationEvent.subject1.patient = new Patient()
		subjectControlActProcess.registrationEvent.subject1.patient.patientPerson = patientPerson
		def inPatientMessage = new AddPatientOperationMessage()
		inPatientMessage.controlActProcess = new PatientControlActProcess()
		inPatientMessage.controlActProcess.subject.add(subjectControlActProcess)
		
		def patient = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(inPatientMessage)

		assert patient.givenName.firstName == "Juan"
		assert patient.givenName.lastName == "Perez García"
	}
	
	public void testWhenHl7AddPatientObjectHasNoFirstNameThenCheckThatTheMapMethodReturnsAPatientDomainObjectWithFirstNameAsNull() {
		def pixContractMapper = new PIXContractMapper()
		
		//BUILD ADD PATIENT MESSAGE WITH JUAN FIRST NAME AND PEREZ GARCIA LAST NAME
		def patientName = new PN()
		patientName.family = "Perez García"
		def patientPerson = new Person()
		patientPerson.name.add(patientName)
		patientPerson.administrativeGenderCode = new CE(code: "M")
		def subjectControlActProcess = new Subject1()
		subjectControlActProcess.registrationEvent = new RegistrationEvent()
		subjectControlActProcess.registrationEvent.subject1 = new Subject2()
		subjectControlActProcess.registrationEvent.subject1.patient = new Patient()
		subjectControlActProcess.registrationEvent.subject1.patient.patientPerson = patientPerson
		def inPatientMessage = new AddPatientOperationMessage()
		inPatientMessage.controlActProcess = new PatientControlActProcess()
		inPatientMessage.controlActProcess.subject.add(subjectControlActProcess)
		
		def patient = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(inPatientMessage)

		assert patient.givenName.firstName == null
		assert patient.givenName.lastName == "Perez García"
	}
	
	public void testWhenHl7AddPatientObjectHasBirthTimeCompleteCheckThePersonHasDayPrecissionAndCorrectDate() {
		def pixContractMapper = new PIXContractMapper()
		
		def patientName = new PN()
		patientName.family = "Perez García"
		def patientPerson = new Person()
		patientPerson.name.add(patientName)
		patientPerson.administrativeGenderCode = new CE(code: "M")
		patientPerson.birthTime = new TS(value: "19850523")
		def subjectControlActProcess = new Subject1()
		subjectControlActProcess.registrationEvent = new RegistrationEvent()
		subjectControlActProcess.registrationEvent.subject1 = new Subject2()
		subjectControlActProcess.registrationEvent.subject1.patient = new Patient()
		subjectControlActProcess.registrationEvent.subject1.patient.patientPerson = patientPerson
		def inPatientMessage = new AddPatientOperationMessage()
		inPatientMessage.controlActProcess = new PatientControlActProcess()
		inPatientMessage.controlActProcess.subject.add(subjectControlActProcess)
		
		def patient = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(inPatientMessage)

		assert patient.birthdate.date == new GregorianCalendar(1985,5,23).getTime()
	}

}
