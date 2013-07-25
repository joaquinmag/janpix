package com.janpix.rup.pixmanager

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.apache.cxf.annotations.WSDLDocumentation;
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.hl7dto.hl7.v3.contracts.*
import com.janpix.hl7dto.hl7.v3.interfaces.PixManagerInterface
import com.janpix.hl7dto.hl7.v3.messages.HL7OperationMessage
import com.janpix.hl7dto.hl7.v3.messages.AddPatientOperationMessage;
import com.janpix.hl7dto.hl7.v3.messages.QueryPatientOperationMessage;
import com.janpix.hl7dto.hl7.v3.messages.ack.AcknowledgmentMessage
import com.janpix.hl7dto.hl7.v3.messages.ack.AddPatientAcknowledgmentMessage;
import com.janpix.hl7dto.hl7.v3.messages.ack.QueryAcknowledgmentMessage

@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)
class PIXManagerHL7v3Service implements PixManagerInterface  {
	static transactional = false
	def pixManagerService
	def assigningAuthorityService
	def pixContractMapper

	@Override
	/**
	 * Add new patients to the PIX. 
	 */								
	public AddPatientAcknowledgmentMessage AddNewPatient(AddPatientOperationMessage body) {
		pixContractMapper.validateHl7V3AddNewPatientMessage(body)
		def person = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(body)
		def patientId = pixContractMapper.getPatientId(body)
		def healthEntity = pixContractMapper.mapSenderToHealthEntity(body)
		def ack = pixManagerService.patientRegistryRecordAdded(person, healthEntity, patientId)
		def sender = assigningAuthorityService.rupAuthority() // TODO asignar utilizando una constante. No acceder a servicio.
		def receiver = healthEntity
		def identifier = pixContractMapper.getMessageIdentifier(body)
		return pixContractMapper.mapACKMessageToHL7AcceptAcknowledgmentMessage(ack, identifier,  receiver,  sender)
	}

	@Override
	/**
	 * Merges two patients that where added as different patients
	 */
	public AddPatientAcknowledgmentMessage MergePatients(HL7OperationMessage body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * This method is for updating patient information
	 */
	public AddPatientAcknowledgmentMessage UpdatePatient(HL7OperationMessage body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Returns all the identifiers of a patient.
	 */
	public QueryAcknowledgmentMessage GetAllIdentifiersPatient(QueryPatientOperationMessage body) {
		pixContractMapper.validateHl7V3PatientQueryMessage(body)
		def patientIdentifier = pixContractMapper.mapIdentifierFromhl7QueryMessage(body.controlActProcess)
		def healthEntity = pixContractMapper.mapSenderToHealthEntity(body)
		def rup = assigningAuthorityService.rupAuthority() // TODO asignar utilizando una constante. No acceder a servicio.
		def ack = pixManagerService.patientRegistryGetIdentifiersQuery(patientIdentifier, healthEntity, [ rup ])
		def sender = rup
		def receiver = healthEntity
		def messageIdentifier = pixContractMapper.getMessageIdentifier(body)
		def queryId = pixContractMapper.getQueryId(body)
		return pixContractMapper.mapQueryACKMessageToHL7QueryAcknowledgmentMessage(ack, messageIdentifier,  receiver,  sender, queryId)
	}

	@Override
	@WebMethod(operationName = "PatientRegistryRecordAddedWithoutValidation")
	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
	@WSDLDocumentation("Add new patients to the PIX without validate matching with other patients.")
	public AddPatientAcknowledgmentMessage AddNewPatientWithoutValidation(
			@WebParam(name = "PRPA_IN201302UV02", targetNamespace = "urn:hl7-org:v3", partName = "Body") AddPatientOperationMessage body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "GetAllPossibleMatchedPatients")
	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
	@WSDLDocumentation("Returns all patient maching with Patient")
	public QueryAcknowledgmentMessage GetAllPossibleMatchedPatients(
			@WebParam(name = "PRPA_IN201302UV02", targetNamespace = "urn:hl7-org:v3", partName = "Body") AddPatientOperationMessage body) {
		// TODO Auto-generated method stub
		return null;
	}
}
