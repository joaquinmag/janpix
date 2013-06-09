package com.janpix.rup.pixmanager;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.namespace.QName

import org.hl7.v3.AD
import org.hl7.v3.ActClassControlAct;
import org.hl7.v3.AdxpCity
import org.hl7.v3.AdxpCountry;
import org.hl7.v3.AdxpState
import org.hl7.v3.AdxpStreetAddressLine
import org.hl7.v3.CE
import org.hl7.v3.COCTMT150002UV01Organization
import org.hl7.v3.COCTMT150003UV03Organization
import org.hl7.v3.CS
import org.hl7.v3.CommunicationFunctionType;
import org.hl7.v3.EnGiven
import org.hl7.v3.EntityClassDevice;
import org.hl7.v3.II
import org.hl7.v3.MCCIMT000100UV01Device
import org.hl7.v3.MCCIMT000100UV01Receiver
import org.hl7.v3.MCCIMT000100UV01Sender
import org.hl7.v3.ON
import org.hl7.v3.ObjectFactory;
import org.hl7.v3.PN
import org.hl7.v3.PRPAIN201301UV02
import org.hl7.v3.PRPAIN201301UV02MFMIMT700701UV01ControlActProcess;
import org.hl7.v3.PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent;
import org.hl7.v3.PRPAIN201301UV02MFMIMT700701UV01Subject1;
import org.hl7.v3.PRPAIN201301UV02MFMIMT700701UV01Subject2;
import org.hl7.v3.PRPAMT201301UV02Patient
import org.hl7.v3.PRPAMT201301UV02OtherIDs
import org.hl7.v3.PRPAMT201301UV02Person
import org.hl7.v3.ParticipationTargetSubject;
import org.hl7.v3.TEL
import org.hl7.v3.TS
import org.hl7.v3.XActMoodIntentEvent;
import org.junit.Test;

class PixManagerHl7v3ServiceTests {

	def PIXManagerHL7v3Service
	
	public void testWhenPixManagerPRPAIN201309UV02IsCalledShouldReturnACKCorrectly() {
		
		
		ObjectFactory objectFactory = new ObjectFactory()
		
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
		MCCIMT000100UV01Receiver recver = new MCCIMT000100UV01Receiver()
		recver.typeCode = CommunicationFunctionType.RCV
		recver.device = new MCCIMT000100UV01Device()
		recver.device.classCode = EntityClassDevice.DEV
		recver.device.determinerCode = "INSTANCE"
		recver.device.id.add(new II(root: "1.2.840.114350.1.13.99999.4567"))
		recver.device.telecom.add(new TEL(value: "https://example.org/PatientFeed"))
		body.receiver.add(recver)
		body.sender = new MCCIMT000100UV01Sender()
		body.sender.typeCode = CommunicationFunctionType.SND
		body.sender.device = new MCCIMT000100UV01Device()
		body.sender.device.classCode = EntityClassDevice.DEV
		body.sender.device.determinerCode = "INSTANCE"
		body.sender.device.id.add(new II(root: "1.2.840.114350.1.13.99998.8734"))
		body.controlActProcess = new PRPAIN201301UV02MFMIMT700701UV01ControlActProcess(classCode: ActClassControlAct.CACT, moodCode: XActMoodIntentEvent.EVN)
		PRPAIN201301UV02MFMIMT700701UV01Subject1 subject = new PRPAIN201301UV02MFMIMT700701UV01Subject1()
		subject.typeCode.add("SUBJ")
		subject.registrationEvent = new PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent()
		subject.registrationEvent.classCode.add("REG")
		subject.registrationEvent.moodCode.add("EVN")
		subject.registrationEvent.id.add(new II(nullFlavor: [ "NA" ]))
		subject.registrationEvent.statusCode = new CS(code: "active")
		subject.registrationEvent.subject1 = new PRPAIN201301UV02MFMIMT700701UV01Subject2()
		subject.registrationEvent.subject1.typeCode = ParticipationTargetSubject.SBJ
		subject.registrationEvent.subject1.patient = new PRPAMT201301UV02Patient()
		subject.registrationEvent.subject1.patient.classCode.add("PAT")
		subject.registrationEvent.subject1.patient.id.add(new II(root:"1.2.840.114350.1.13.99998.8734", extension:"34827G234"))
		subject.registrationEvent.subject1.patient.statusCode = new CS(code:"active")
		PRPAMT201301UV02Person person = new PRPAMT201301UV02Person()
		PN patientName = new PN()
		patientName.content.add(new JAXBElement<EnGiven>(new QName("given"), JAXBElement.class, "Juan"))
		patientName.content.add(new JAXBElement<EnGiven>(new QName("family"), JAXBElement.class, "Perez García"))
		person.name.add(patientName)
		person.administrativeGenderCode = new CE(code:"M")
		person.birthTime = new TS(value: "19570323")
		AD ad = new AD()
		ad.content.add(new JAXBElement<AdxpStreetAddressLine>(new QName("urn:hl7-org:v3", "streetAddressLine"), AdxpStreetAddressLine.class, AD.class, "3443 S Beach Ave"))
		ad.content.add(new JAXBElement<AdxpCity>(new QName("urn:hl7-org:v3", "city"), AdxpCity.class, AD.class, "Venado Tuerto"))
		ad.content.add(new JAXBElement<AdxpState>(new QName("urn:hl7-org:v3", "state"), AdxpState.class, AD.class, "AR-S"))
		ad.content.add(new JAXBElement<AdxpCountry>(ObjectFactory._ADCountry_QNAME, AdxpCountry.class, AD.class, "AR"))
		person.addr.add(ad)
		PRPAMT201301UV02OtherIDs otherId = new PRPAMT201301UV02OtherIDs()
		otherId.classCode.add("PAT")
		otherId.id.add(new II(root: "1.2.840.114350.1.13.99997.2.3412", extension: "38273N237"))
		otherId.scopingOrganization = new COCTMT150002UV01Organization()
		otherId.scopingOrganization.classCode = "ORG"
		otherId.scopingOrganization.determinerCode = "INSTANCE"
		otherId.scopingOrganization.id.add( new II(root: "1.2.840.114350.1.13.99997.2.3412"))
		person.asOtherIDs.add(otherId)
		subject.registrationEvent.subject1.patient.patientPerson = person
		subject.registrationEvent.subject1.patient.providerOrganization = new COCTMT150003UV03Organization()
		subject.registrationEvent.subject1.patient.providerOrganization.classCode = "ORG"
		subject.registrationEvent.subject1.patient.providerOrganization.determinerCode = "INSTANCE"
		subject.registrationEvent.subject1.patient.providerOrganization.id.add( new II(root: "1.2.840.114350.1.13.99998.8734"))
		subject.registrationEvent.subject1.patient.providerOrganization.name.add(new ON().content.add("Good Health Clinic"))
		body.controlActProcess.subject.add(subject)
		def ack = PIXManagerHL7v3Service.pixManagerPRPAIN201309UV02(body)
	}

	@Test
	public void testPixManagerPRPAIN201304UV02() {
		fail("Not yet implemented");
	}

	@Test
	public void testPixManagerPRPAIN201302UV02() {
		fail("Not yet implemented");
	}

	@Test
	public void testPixManagerPRPAIN201309UV02PRPAIN201309UV02() {
		fail("Not yet implemented");
	}

}
