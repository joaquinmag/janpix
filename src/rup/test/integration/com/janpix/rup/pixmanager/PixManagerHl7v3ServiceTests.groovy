package com.janpix.rup.pixmanager;

import static org.junit.Assert.*

import org.junit.Test

import com.janpix.hl7dto.hl7.v3.contracts.PRPAIN201301UV02
import com.janpix.hl7dto.hl7.v3.datatypes.AD
import com.janpix.hl7dto.hl7.v3.datatypes.CE
import com.janpix.hl7dto.hl7.v3.datatypes.CS
import com.janpix.hl7dto.hl7.v3.datatypes.II
import com.janpix.hl7dto.hl7.v3.datatypes.PN
import com.janpix.hl7dto.hl7.v3.datatypes.TEL
import com.janpix.hl7dto.hl7.v3.datatypes.TS
import com.janpix.hl7dto.hl7.v3.datatypes.enums.ActClassControlAct
import com.janpix.hl7dto.hl7.v3.datatypes.enums.CommunicationFunctionType
import com.janpix.hl7dto.hl7.v3.datatypes.enums.ParticipationTargetSubject
import com.janpix.hl7dto.hl7.v3.datatypes.enums.XActMoodIntentEvent
import com.janpix.hl7dto.hl7.v3.messages.ControlActProcess
import com.janpix.hl7dto.hl7.v3.messages.Device
import com.janpix.hl7dto.hl7.v3.messages.HL7MessageReceiver
import com.janpix.hl7dto.hl7.v3.messages.HL7MessageSender
import com.janpix.hl7dto.hl7.v3.messages.Organization
import com.janpix.hl7dto.hl7.v3.messages.OtherIDs
import com.janpix.hl7dto.hl7.v3.messages.Patient
import com.janpix.hl7dto.hl7.v3.messages.Person
import com.janpix.hl7dto.hl7.v3.messages.RegistrationEvent
import com.janpix.hl7dto.hl7.v3.messages.Subject1
import com.janpix.hl7dto.hl7.v3.messages.Subject2
import com.janpix.rup.empi.HealthEntity

class PixManagerHl7v3ServiceTests {

	def PIXManagerHL7v3Service
	
	public void testWhenPixManagerPRPAIN201309UV02IsCalledShouldReturnACKCorrectly() {
		def testAuthorityOID = "1.2.840.114350.1.13.99998.8734"
		def testAuthorityName = "Good Health Clinic"
		PRPAIN201301UV02 body = new PRPAIN201301UV02()
		body.itsVersion = "XML_1.0"
		II messageId = new II()
		messageId.root = "22a0f9e0-4454-11dc-a6be-3603d6866807"
		body.id = messageId
		body.creationTime = new TS(value: "20070803130624")
		body.interactionId = new II(root: "2.16.840.1.113883.1.6", extension: "PRPA_IN201301UV02")
		body.processingCode = new CS(code: "P")
		body.processingModeCode = new CS(code: "R")
		body.acceptAckCode = new CS(code: "AL")
		HL7MessageReceiver recver = new HL7MessageReceiver()
		recver.typeCode = CommunicationFunctionType.RCV
		recver.device = new Device()
		recver.device.determinerCode = "INSTANCE"
		recver.device.id.add(new II(root: "1.2.840.114350.1.13.99999.4567"))
		recver.device.telecom.add(new TEL(value: "https://example.org/PatientFeed"))
		body.receiver.add(recver)
		body.sender = new HL7MessageSender()
		body.sender.typeCode = CommunicationFunctionType.SND
		body.sender.device = new Device()
		body.sender.device.determinerCode = "INSTANCE"
		body.sender.device.id.add(new II(root: testAuthorityOID))
		body.controlActProcess = new ControlActProcess(classCode: ActClassControlAct.CACT, moodCode: XActMoodIntentEvent.EVN)
		Subject1 subject = new Subject1()
		subject.typeCode.add("SUBJ")
		subject.registrationEvent = new RegistrationEvent()
		subject.registrationEvent.classCode.add("REG")
		subject.registrationEvent.moodCode.add("EVN")
		subject.registrationEvent.statusCode = new CS(code: "active")
		subject.registrationEvent.subject1 = new Subject2()
		subject.registrationEvent.subject1.typeCode = ParticipationTargetSubject.SBJ
		subject.registrationEvent.subject1.patient = new Patient()
		subject.registrationEvent.subject1.patient.classCode.add("PAT")
		subject.registrationEvent.subject1.patient.id.add(new II(root:testAuthorityOID, extension:"34827G234", assigningAuthorityName: testAuthorityName))
		subject.registrationEvent.subject1.patient.statusCode = new CS(code:"active")
		Person person = new Person()
		PN patientName = new PN()
		patientName.given = "Juan"
		patientName.family = "Perez García"
		person.name.add(patientName)
		person.administrativeGenderCode = new CE(code:"M")
		person.birthTime = new TS(value: "19570323")
		AD ad = new AD()
		ad.streetAddressLine = "3443 S Beach Ave"
		ad.city = "Venado Tuerto"
		ad.province = "AR-S"
		ad.country = "AR"
		person.addr.add(ad)
		OtherIDs otherId = new OtherIDs()
		otherId.classCode.add("PAT")
		otherId.id.add(new II(root: "1.2.840.114350.1.13.99997.2.3412", extension: "38273N237"))
		otherId.scopingOrganization = new Organization()
		otherId.scopingOrganization.classCode = "ORG"
		otherId.scopingOrganization.determinerCode = "INSTANCE"
		otherId.scopingOrganization.id.add( new II(root: "1.2.840.114350.1.13.99997.2.3412"))
		person.asOtherIDs.add(otherId)
		subject.registrationEvent.subject1.patient.patientPerson = person
		body.controlActProcess.subject.add(subject)
		def ack = PIXManagerHL7v3Service.AddNewPatient(body)
		
		assert ack.acknowledgement[0].typeCode.code == "CA"
	}

}
