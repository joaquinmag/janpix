package com.janpix.rup.pixmanager

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.apache.commons.lang.NotImplementedException
import org.apache.cxf.annotations.WSDLDocumentation;
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.hl7dto.hl7.v3.contracts.*
import com.janpix.hl7dto.hl7.v3.interfaces.PixManagerInterface
import com.janpix.hl7dto.hl7.v3.messages.PatientOperationMessage
import com.janpix.hl7dto.hl7.v3.messages.HL7OperationMessage
import com.janpix.hl7dto.hl7.v3.messages.QueryPatientOperationMessage
import com.janpix.hl7dto.hl7.v3.messages.ack.AddPatientAcknowledgmentMessage
import com.janpix.hl7dto.hl7.v3.messages.ack.QueryAcknowledgmentMessage
import com.janpix.rup.empi.Person
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO;
import com.janpix.rup.infrastructure.dto.PatientDTO;
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.services.contracts.ACKMessage;

@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)
class PIXManagerHL7v3Service implements PixManagerInterface  {
	static transactional = false
	def pixManagerService
	def assigningAuthorityService
	def pixContractMapper
	def mapperDomainDto
	def rupDTOFactory

	@Override
	/**
	 * Add new patients to the PIX. 
	 */								
	public AddPatientAcknowledgmentMessage AddNewPatient(PatientOperationMessage body) {
		pixContractMapper.validateHl7V3AddNewPatientMessage(body)
		
		PersonDTO personDTO = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(body)
		
		def patientId = pixContractMapper.getPatientId(body)
		
		def healthEntityDTO = pixContractMapper.mapSenderToHealthEntity(body)
		
		def ack = pixManagerService.patientRegistryRecordAdded(personDTO, healthEntityDTO, patientId)
		def sender = rupDTOFactory.buildRUPDTO()
		def receiver = healthEntityDTO
		def identifier = pixContractMapper.getMessageIdentifier(body)
		return pixContractMapper.mapACKMessageToHL7AcceptAcknowledgmentMessage(ack, identifier,  receiver,  sender)
	}

	@Override
	/**
	 * Merges two patients that where added as different patients
	 */
	public AddPatientAcknowledgmentMessage MergePatients(HL7OperationMessage body) {
		throw new NotImplementedException("Method not allowed at this RUP implementation.") //TODO utilizar internacionalización.
	}

	@Override
	/**
	 * This method is for updating patient information
	 */
	public AddPatientAcknowledgmentMessage UpdatePatient(PatientOperationMessage body) {
		pixContractMapper.validateHl7V3UpdatePatientMessage(body)
		
		def patientDTO = pixContractMapper.mapPatientFromhl7v3UpdatePatientMessage(body)
		def healthEntity = pixContractMapper.mapSenderToHealthEntity(body)
		def receiver = healthEntity
		def ackMessage = pixManagerService.patientRegistryRecordRevised(patientDTO, healthEntity)
		def identifier = pixContractMapper.getMessageIdentifier(body)
		def sender = rupDTOFactory.buildRUPDTO()
		return pixContractMapper.mapACKMessageToHL7AcceptAcknowledgmentMessage(ackMessage, identifier,  receiver,  sender)
	}

	@Override
	/**
	 * Returns all the identifiers of a patient.
	 */
	public QueryAcknowledgmentMessage GetAllIdentifiersPatient(QueryPatientOperationMessage body) {
		pixContractMapper.validateHl7V3PatientQueryMessage(body)
		def patientIdentifier = pixContractMapper.mapIdentifierFromhl7QueryMessage(body.controlActProcess)

		def healthEntityDTO = pixContractMapper.mapSenderToHealthEntity(body)
		
		AssigningAuthorityDTO rupDTO = rupDTOFactory.buildRUPDTO()
		
		def ack = pixManagerService.patientRegistryGetIdentifiersQuery(patientIdentifier, healthEntityDTO, [ rupDTO ])
		def sender = rupDTO
		def receiver = healthEntityDTO
		def messageIdentifier = pixContractMapper.getMessageIdentifier(body)
		def queryId = pixContractMapper.getQueryId(body)
		return pixContractMapper.mapQueryACKMessageToHL7QueryAcknowledgmentMessage(ack, messageIdentifier,  receiver,  sender, queryId)
	}

	@Override
	@WebMethod(operationName = "PatientRegistryRecordAddedWithoutValidation")
	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
	@WSDLDocumentation("Add new patients to the PIX without validate matching with other patients.")
	public AddPatientAcknowledgmentMessage AddNewPatientWithoutValidation(
			@WebParam(name = "PRPA_IN201302UV02", targetNamespace = "urn:hl7-org:v3", partName = "Body") PatientOperationMessage body) {
		pixContractMapper.validateHl7V3AddNewPatientMessage(body)
		
		PersonDTO personDTO = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(body)
		
		def patientId = pixContractMapper.getPatientId(body)
		
		def healthEntityDTO = pixContractMapper.mapSenderToHealthEntity(body)
		
		def ack = pixManagerService.patientRegistryRecordAddedWithoutMatching(personDTO, healthEntityDTO, patientId)
		def sender = rupDTOFactory.buildRUPDTO()
		def receiver = healthEntityDTO
		def identifier = pixContractMapper.getMessageIdentifier(body)
		return pixContractMapper.mapACKMessageToHL7AcceptAcknowledgmentMessage(ack, identifier,  receiver,  sender)
	}

	@Override
	@WebMethod(operationName = "GetAllPossibleMatchedPatients")
	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
	@WSDLDocumentation("Returns all patient maching with Patient")
	public QueryAcknowledgmentMessage GetAllPossibleMatchedPatients(
			@WebParam(name = "PRPA_IN201302UV02", targetNamespace = "urn:hl7-org:v3", partName = "Body") PatientOperationMessage body) {
		pixContractMapper.validateHl7V3AddNewPatientMessage(body)
		PersonDTO personDTO = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(body)
		def healthEntityDTO = pixContractMapper.mapSenderToHealthEntity(body)
		
		def patients = pixManagerService.getAllPossibleMatchedPatients(personDTO)
		def sender = rupDTOFactory.buildRUPDTO()
		def receiver = healthEntityDTO
		def messageIdentifier = pixContractMapper.getMessageIdentifier(body)
		return pixContractMapper.mapPatientListToHL7QueryAcknowledgmentMessage(patients, messageIdentifier,  receiver,  sender)
	}
}
