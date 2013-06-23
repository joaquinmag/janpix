package com.janpix.rup.pixmanager

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.hl7dto.hl7.v3.contracts.*;
import com.janpix.hl7dto.hl7.v3.interfaces.PixManagerInterface;
import com.janpix.hl7dto.hl7.v3.messages.HL7OperationMessage;

@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)
class PIXManagerHL7v3Service implements PixManagerInterface  {
	
	def pixManagerService
	def assigningAuthorityService
	def pixContractMapper
		
	
	@Override
	/**
	 * Add new patients to the PIX. 
	 */								
	public MCCIIN000002UV01 AddNewPatient(PRPAIN201301UV02 body) {
		pixContractMapper.validateHl7V3AddNewPatientMessage(body)
		def person = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(body)
		def patientId = pixContractMapper.getPatientId(body)
		def healthEntity = pixContractMapper.mapSenderToHealthEntity(body)
		def ack = pixManagerService.patientRegistryRecordAdded(person, healthEntity, patientId)
		def sender = assigningAuthorityService.rupAuthority()
		def receiver = healthEntity
		def identifier = pixContractMapper.getMessageIdentifier(body)
		return pixContractMapper.mapACKMessageToHL7AcceptAcknowledgmentMessage(ack, identifier,  receiver,  sender)
	}

	@Override
	/**
	 * Merges two patients that where added as different patients
	 */
	public MCCIIN000002UV01 MergePatients(HL7OperationMessage body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * This method is for updating patient information
	 */
	public MCCIIN000002UV01 UpdatePatient(HL7OperationMessage body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Returns all the identifiers of a patient.
	 */
	public HL7OperationMessage GetAllIdentifiersPatient(HL7OperationMessage body) {
		// TODO Auto-generated method stub
		return new HL7OperationMessage();
	}


}
