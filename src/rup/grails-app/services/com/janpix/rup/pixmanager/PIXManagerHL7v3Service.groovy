package com.janpix.rup.pixmanager

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.hl7dto.hl7.v3.contracts.*;
import com.janpix.hl7dto.hl7.v3.interfaces.PixManagerInterface;

@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)
class PIXManagerHL7v3Service implements PixManagerInterface  {
	
	def pixManagerService
	def assigningAuthorityService
	def pixContractMapper
		
	
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

//		
//	/**
//	 * Merges two patients that where added as different patients
//	 * Patient Registry Duplicates Resolved (IHE_ITI Vol 2b - Seccion: 3.44.4)
//	 * @param body PRPA_IN201304UV02 (ej: ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/05_PatientRegistryDuplicatesResolved.xml )
//	 * @return ACK : MCCI_IN000002UV01 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/02_PatientRegistryRecordAdded1Ack.xml>link</a>
//	 */
//	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
//	@Action(input = "urn:hl7-org:v3:PRPA_IN201304UV02", output = "urn:hl7-org:v3:MCCI_IN000002UV01")
//	@WebMethod(operationName = "PIXManager_PRPA_IN201304UV02", action = "urn:hl7-org:v3:PRPA_IN201304UV02")
//	@WSDLDocumentation("Merges two patients that where added as different patients. Patient Registry Duplicates Resolved (IHE_ITI Vol 2b - Seccion: 3.44.4)")
//	public org.hl7.v3.MCCIIN000002UV01 pixManagerPRPAIN201304UV02(
//		@WebParam(partName = "Body", name = "PRPA_IN201304UV02", targetNamespace = "urn:hl7-org:v3")
//		org.hl7.v3.PRPAIN201304UV02 body) {
//	}
//
//	/**
//	 * This method is for updating patient information
//	 * Patient Registry Record Revised(IHE_ITI Vol 2b - Seccion: 3.44.4.1)
//	 * @param body PRPA_IN201302UV02 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/04_PatientRegistryRecordRevised2.xml">link</a>
//	 * @return ACK : MCCI_IN000002UV01 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/02_PatientRegistryRecordAdded1Ack.xml">link</a>
//	 */
//	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
//	@Action(input = "urn:hl7-org:v3:PRPA_IN201302UV02", output = "urn:hl7-org:v3:MCCI_IN000002UV01")
//	@WebMethod(operationName = "PIXManager_PRPA_IN201302UV02", action = "urn:hl7-org:v3:PRPA_IN201302UV02")
//	@WSDLDocumentation("This method is for updating patient information. Patient Registry Record Revised(IHE_ITI Vol 2b - Seccion: 3.44.4.1)")
//	public org.hl7.v3.MCCIIN000002UV01 pixManagerPRPAIN201302UV02 (
//		@WebParam(partName = "Body", name = "PRPA_IN201302UV02", targetNamespace = "urn:hl7-org:v3")
//		org.hl7.v3.PRPAIN201302UV02 body) {
//	}
//
//	/**
//	 * Returns all the identifiers of a patient.
//	 * Patient Registry Get Identifiers Query (IHE_ITI Vol 2b - Seccion: 3.45.4)
//	 * @param body PRPA_IN201309UV02 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/06_PIXQuery1.xml">link</a>
//	 * @return PRPA_IN201310UV02 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/07_PIXQuery1Response.xml">link</a>
//	 */
//	@WebResult(name = "PRPA_IN201310UV02", targetNamespace = "urn:hl7-org:v3", partName = "Body")
//	@Action(input = "urn:hl7-org:v3:PRPA_IN201309UV02", output = "urn:hl7-org:v3:PRPA_IN201310UV02")
//	@WebMethod(operationName = "PIXManager_PRPA_IN201309UV02", action = "urn:hl7-org:v3:PRPA_IN201309UV02")
//	@WSDLDocumentation("Returns all the identifiers of a patient. Patient Registry Get Identifiers Query (IHE_ITI Vol 2b - Seccion: 3.45.4)")
//	public org.hl7.v3.PRPAIN201310UV02 pixManagerPRPAIN201309UV02(
//		@WebParam(partName = "Body", name = "PRPA_IN201309UV02", targetNamespace = "urn:hl7-org:v3")
//		org.hl7.v3.PRPAIN201309UV02 body) {
//		
//	}
}
