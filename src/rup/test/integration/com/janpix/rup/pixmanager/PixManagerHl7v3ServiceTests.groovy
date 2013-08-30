package com.janpix.rup.pixmanager;

import static org.junit.Assert.*

import javax.xml.bind.annotation.XmlElement;

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
import com.janpix.hl7dto.hl7.v3.messages.AssignedEntity;
import com.janpix.hl7dto.hl7.v3.messages.Custodian
import com.janpix.hl7dto.hl7.v3.messages.PatientOperationMessage
import com.janpix.hl7dto.hl7.v3.messages.Device
import com.janpix.hl7dto.hl7.v3.messages.HL7MessageReceiver
import com.janpix.hl7dto.hl7.v3.messages.HL7MessageSender
import com.janpix.hl7dto.hl7.v3.messages.Organization
import com.janpix.hl7dto.hl7.v3.messages.OtherIDs
import com.janpix.hl7dto.hl7.v3.messages.Patient
import com.janpix.hl7dto.hl7.v3.messages.PatientControlActProcess
import com.janpix.hl7dto.hl7.v3.messages.Person
import com.janpix.hl7dto.hl7.v3.messages.QueryControlActProcess
import com.janpix.hl7dto.hl7.v3.messages.QueryPatientOperationMessage
import com.janpix.hl7dto.hl7.v3.messages.RegistrationEvent
import com.janpix.hl7dto.hl7.v3.messages.Subject1
import com.janpix.hl7dto.hl7.v3.messages.Subject2
import com.janpix.hl7dto.hl7.v3.messages.ack.AddPatientAcknowledgmentMessage;
import com.janpix.hl7dto.hl7.v3.messages.query.QueryByParameter
import com.janpix.hl7dto.hl7.v3.messages.query.QueryParameter
import com.janpix.hl7dto.hl7.v3.messages.query.QueryParameterList
import com.janpix.rup.services.contracts.ACKMessage;

class PixManagerHl7v3ServiceTests {

	def PIXManagerHL7v3Service
	
	public void testWhenPixManagerPRPAIN201309UV02IsCalledShouldReturnACKCorrectly() {
		def testAuthorityOID = "1.2.840.114350.1.13.99998.8734"
		def testAuthorityName = "Good Health Clinic"
		
		II messageId = new II()
		messageId.root = "22a0f9e0-4454-11dc-a6be-3603d6866807"
		HL7MessageReceiver recver = new HL7MessageReceiver()
		recver.typeCode = CommunicationFunctionType.RCV
		recver.device = new Device()
		recver.device.determinerCode = "INSTANCE"
		recver.device.id.add(new II(root: "1.2.840.114350.1.13.99999.4567"))
		recver.device.telecom.add(new TEL(value: "https://example.org/PatientFeed"))
		def sender= new HL7MessageSender()
		sender.typeCode = CommunicationFunctionType.SND
		sender.device = new Device()
		sender.device.determinerCode = "INSTANCE"
		sender.device.id.add(new II(root: testAuthorityOID))
		
		PatientOperationMessage body = new PatientOperationMessage()
		body = buildAddNewPatientMessage(messageId, recver, sender, testAuthorityOID, testAuthorityName, body)
		def ack = PIXManagerHL7v3Service.AddNewPatient(body)
		
		assert ack.acknowledgement[0].typeCode.code == "CA"
	}


	public void testWhenGetIdsIsCalledShouldReturnACKCorrectly() {
		def testAuthorityOID = "1.2.840.114350.1.13.99998.8734"
		def testAuthorityName = "Good Health Clinic"
		
		II messageId = new II()
		messageId.root = "22a0f9e0-4454-11dc-a6be-3603d6866807"
		HL7MessageReceiver recver = new HL7MessageReceiver()
		recver.typeCode = CommunicationFunctionType.RCV
		recver.device = new Device()
		recver.device.determinerCode = "INSTANCE"
		recver.device.id.add(new II(root: "1.2.840.114350.1.13.99999.4567"))
		recver.device.telecom.add(new TEL(value: "https://example.org/PatientFeed"))
		def sender= new HL7MessageSender()
		sender.typeCode = CommunicationFunctionType.SND
		sender.device = new Device()
		sender.device.determinerCode = "INSTANCE"
		sender.device.id.add(new II(root: testAuthorityOID))
		

		PatientOperationMessage body = new PatientOperationMessage()
		body = buildAddNewPatientMessage(messageId, recver, sender, testAuthorityOID, testAuthorityName, body)
		def ack = PIXManagerHL7v3Service.AddNewPatient(body)
		
		assert ack.acknowledgement[0].typeCode.code == "CA"

		def query = buildQueryOperationMessage(messageId, recver, sender, testAuthorityOID)
		
		def ackQuery = PIXManagerHL7v3Service.GetAllIdentifiersPatient(query)
		
		ackQuery.controlActProcess.subject[0].registrationEvent.id.each { II id ->
			println "id: ${id}"
		}
		ackQuery.controlActProcess.subject[0].registrationEvent.subject1.patient.id.find { II id ->
			println "id: ${id}"
		}
		
		def rupId =  ackQuery.controlActProcess.subject[0].registrationEvent.subject1.patient.id.find { II id ->
			id.assigningAuthorityName == "RUP"
		}
		assert rupId
	}

	public void testWhenUpdatePatientShouldNotThrowException() {
		def testAuthorityOID = "1.2.840.114350.1.13.99998.8734"
		def testAuthorityName = "Good Health Clinic"
		
		II messageId = new II()
		messageId.root = "22a0f9e0-4454-11dc-a6be-3603d6866807"
		HL7MessageReceiver recver = new HL7MessageReceiver()
		recver.typeCode = CommunicationFunctionType.RCV
		recver.device = new Device()
		recver.device.determinerCode = "INSTANCE"
		recver.device.id.add(new II(root: "1.2.840.114350.1.13.99999.4567"))
		recver.device.telecom.add(new TEL(value: "https://example.org/PatientFeed"))
		def sender= new HL7MessageSender()
		sender.typeCode = CommunicationFunctionType.SND
		sender.device = new Device()
		sender.device.determinerCode = "INSTANCE"
		sender.device.id.add(new II(root: testAuthorityOID))
		
		PatientOperationMessage body = new PatientOperationMessage()
		body = buildAddNewPatientMessage(messageId, recver, sender, testAuthorityOID, testAuthorityName, body)
		def ack = PIXManagerHL7v3Service.AddNewPatient(body)
		
		assert ack.acknowledgement[0].typeCode.code == "CA"
		
		def query = buildQueryOperationMessage(messageId, recver, sender, testAuthorityOID)
		
		ack = PIXManagerHL7v3Service.GetAllIdentifiersPatient(query)
		
		// Preparo el mensaje para hacer update
		ack.controlActProcess.subject[0].registrationEvent.subject1.patient.id.each { II id ->
			//Agrego el id asignado por el RUP a este paciente
			if (id.assigningAuthorityName == "RUP") // TODO usar config
				body.controlActProcess.subject[0].registrationEvent.subject1.patient.id.add(new II(root: id.root, extension: id.extension, assigningAuthorityName: id.assigningAuthorityName))
		}
		body.controlActProcess.subject[0].registrationEvent.subject1.patient.patientPerson.name[0].family = "Gómez"
		Custodian custodian = new Custodian()
		custodian.typeCode = "CST"
		body.controlActProcess.subject[0].registrationEvent.custodian = custodian
		ack = PIXManagerHL7v3Service.UpdatePatient(body)
		
		assert ack.acknowledgement[0].typeCode.code == "CA"
		
		//TODO testear con get all posible matched patients que este paciente se haya actualizado
	}
	
	public void testWhenAddNewPatientWithoutValidationIsCalledShouldNotThrowException() {
		def testAuthorityOID = "1.2.840.114350.1.13.99998.8734"
		def testAuthorityName = "Good Health Clinic"
		
		II messageId = new II()
		messageId.root = "22a0f9e0-4454-11dc-a6be-3603d6866807"
		HL7MessageReceiver recver = new HL7MessageReceiver()
		recver.typeCode = CommunicationFunctionType.RCV
		recver.device = new Device()
		recver.device.determinerCode = "INSTANCE"
		recver.device.id.add(new II(root: "1.2.840.114350.1.13.99999.4567"))
		recver.device.telecom.add(new TEL(value: "https://example.org/PatientFeed"))
		def sender= new HL7MessageSender()
		sender.typeCode = CommunicationFunctionType.SND
		sender.device = new Device()
		sender.device.determinerCode = "INSTANCE"
		sender.device.id.add(new II(root: testAuthorityOID))
		
		PatientOperationMessage body = new PatientOperationMessage()
		body = buildAddNewPatientMessage(messageId, recver, sender, testAuthorityOID, testAuthorityName, body)
		def ack = PIXManagerHL7v3Service.AddNewPatientWithoutValidation(body)
		
		assert ack.acknowledgement[0].typeCode.code == "CA"
	}
	
	
	public void testWhenGetAllPossibleMatchedPatientsIsCalledShouldNotThrowException() {
		def testAuthorityOID = "1.2.840.114350.1.13.99998.8734"
		def testAuthorityName = "Good Health Clinic"
		
		II messageId = new II()
		messageId.root = "22a0f9e0-4454-11dc-a6be-3603d6866807"
		HL7MessageReceiver recver = new HL7MessageReceiver()
		recver.typeCode = CommunicationFunctionType.RCV
		recver.device = new Device()
		recver.device.determinerCode = "INSTANCE"
		recver.device.id.add(new II(root: "1.2.840.114350.1.13.99999.4567"))
		recver.device.telecom.add(new TEL(value: "https://example.org/PatientFeed"))
		def sender= new HL7MessageSender()
		sender.typeCode = CommunicationFunctionType.SND
		sender.device = new Device()
		sender.device.determinerCode = "INSTANCE"
		sender.device.id.add(new II(root: testAuthorityOID))
		
		PatientOperationMessage body = new PatientOperationMessage()
		body = buildAddNewPatientMessage(messageId, recver, sender, testAuthorityOID, testAuthorityName, body)
		def ack = PIXManagerHL7v3Service.AddNewPatient(body)
		
		assert ack.acknowledgement[0].typeCode.code == "CA"
		
		ack = PIXManagerHL7v3Service.GetAllPossibleMatchedPatients(body)
		
		assert ack.acknowledgement[0].typeCode.code == "CA"
	}

	private PatientOperationMessage buildAddNewPatientMessage(II messageId, HL7MessageReceiver recver, HL7MessageSender sender, String testAuthorityOID, String testAuthorityName, PatientOperationMessage body) {
		body.itsVersion = "XML_1.0"
		body.id = messageId
		body.creationTime = new TS(value: "20070803130624")
		body.interactionId = new II(root: "2.16.840.1.113883.1.6", extension: "PRPA_IN201301UV02")
		body.processingCode = new CS(code: "P")
		body.processingModeCode = new CS(code: "R")
		body.acceptAckCode = new CS(code: "AL")
		body.receiver.add(recver)
		body.sender = sender
		body.controlActProcess = new PatientControlActProcess(classCode: ActClassControlAct.CACT, moodCode: XActMoodIntentEvent.EVN)
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
		return body
	}
	
	private QueryPatientOperationMessage buildQueryOperationMessage(II messageId, HL7MessageReceiver recver, HL7MessageSender sender, String testAuthorityOID) {
		QueryPatientOperationMessage query = new QueryPatientOperationMessage()
		query.id = messageId
		query.creationTime = new TS(value: "20070803130624")
		query.interactionId = new II(root: "2.16.840.1.113883.1.6", extension:"PRPA_IN201309UV02")
		query.processingCode = new CS(code: "P")
		query.processingModeCode = new CS(code: "T")
		query.receiver.add(recver)
		query.sender = sender
		query.acceptAckCode = new CS(code: "AL")
		query.controlActProcess = new QueryControlActProcess()
		query.controlActProcess.classCode = ActClassControlAct.CACT
		query.controlActProcess.moodCode = XActMoodIntentEvent.EVN
		query.controlActProcess.code = new CS(code: "PRPA_TE201309UV02", codeSystem: "2.16.840.1.113883.1.6")
		query.controlActProcess.queryByParameter = new QueryByParameter()
		query.controlActProcess.queryByParameter.queryId = new II(root:"1.2.840.114350.1.13.99999.4567.34", extension:"33452")
		query.controlActProcess.queryByParameter.statusCode = new CS(code: "new")
		query.controlActProcess.queryByParameter.parameterList = new QueryParameterList()
		query.controlActProcess.queryByParameter.parameterList.patientIdentifier = new ArrayList<QueryParameter>()
		def queryParameter = new QueryParameter()
		queryParameter.value = new ArrayList<II>()
		queryParameter.value.add(new II(root: testAuthorityOID, extension: "34827G234"))
		query.controlActProcess.queryByParameter.parameterList.patientIdentifier.add(queryParameter)
		return query
	}

}
