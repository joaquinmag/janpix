package com.janpix.rup.pixmanager

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;

import org.apache.cxf.annotations.WSDLDocumentation;
import org.grails.cxf.utils.EndpointType;
import org.grails.cxf.utils.GrailsCxfEndpoint;
import org.hl7.v3.II;
import org.hl7.v3.MCCIMT000200UV01Receiver;
import org.hl7.v3.MCCIMT000200UV01Sender;

import com.janpix.rup.empi.AssigningAuthority;
import com.janpix.rup.empi.HealthEntity;
import com.janpix.rup.services.contracts.ACKMessage;

@WebService(targetNamespace = "urn:ihe:iti:pixv3:2007", name = "PIXManagerServiceHL7v3")
@XmlSeeAlso([org.hl7.v3.ObjectFactory.class])
@GrailsCxfEndpoint(expose = EndpointType.SIMPLE)
class PIXManagerHL7v3Service {
	
	def pixManagerService
	def pixContractMapper
											
	/**
	 * Add new patients to the PIX. 
	 * Patient Registry Record Added(IHE_ITI Vol 2b - Seccion: 3.44.4.1
	 * @param body PRPA_IN201301UV02 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/01_PatientRegistryRecordAdded1.xml">link</a> )
	 * @return ACK : MCCI_IN000002UV01 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/02_PatientRegistryRecordAdded1Ack.xml>link</a>
	 */
	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
	@Action(input = "urn:hl7-org:v3:PRPA_IN201301UV02", output = "urn:hl7-org:v3:MCCI_IN000002UV01")
	@WebMethod(operationName = "PIXManager_PRPA_IN201301UV02", action = "urn:hl7-org:v3:PRPA_IN201301UV02")
	@WSDLDocumentation("Add new patients to the PIX. Patient Registry Record Added(IHE_ITI Vol 2b - Seccion: 3.44.4.1")
	public org.hl7.v3.MCCIIN000002UV01 pixManagerPRPAIN201309UV02(
		@WebParam(partName = "Body", name = "PRPA_IN201301UV02", targetNamespace = "urn:hl7-org:v3")
		org.hl7.v3.PRPAIN201301UV02 body) {
		pixContractMapper.validateHl7V3AddNewPatientMessage(body)
		def person = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(body)
		def ack = pixManagerService.patientRegistryRecordAdded(person)
		def sender = AssigningAuthority.rupAuthority()
		def receiver = pixContractMapper.mapSenderToHealthEntity(body)
		def identifier = pixContractMapper.getMessageIdentifier(body)
		return pixContractMapper.mapACKMessageToHL7AcceptAcknowledgmentMessage(ack, identifier,  receiver,  sender)
	}

		
	/**
	 * Merges two patients that where added as different patients
	 * Patient Registry Duplicates Resolved (IHE_ITI Vol 2b - Seccion: 3.44.4)
	 * @param body PRPA_IN201304UV02 (ej: ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/05_PatientRegistryDuplicatesResolved.xml )
	 * @return ACK : MCCI_IN000002UV01 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/02_PatientRegistryRecordAdded1Ack.xml>link</a>
	 */
	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
	@Action(input = "urn:hl7-org:v3:PRPA_IN201304UV02", output = "urn:hl7-org:v3:MCCI_IN000002UV01")
	@WebMethod(operationName = "PIXManager_PRPA_IN201304UV02", action = "urn:hl7-org:v3:PRPA_IN201304UV02")
	@WSDLDocumentation("Merges two patients that where added as different patients. Patient Registry Duplicates Resolved (IHE_ITI Vol 2b - Seccion: 3.44.4)")
	public org.hl7.v3.MCCIIN000002UV01 pixManagerPRPAIN201304UV02(
		@WebParam(partName = "Body", name = "PRPA_IN201304UV02", targetNamespace = "urn:hl7-org:v3")
		org.hl7.v3.PRPAIN201304UV02 body) {
	}

	/**
	 * This method is for updating patient information
	 * Patient Registry Record Revised(IHE_ITI Vol 2b - Seccion: 3.44.4.1)
	 * @param body PRPA_IN201302UV02 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/04_PatientRegistryRecordRevised2.xml">link</a>
	 * @return ACK : MCCI_IN000002UV01 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/02_PatientRegistryRecordAdded1Ack.xml">link</a>
	 */
	@WebResult(name = "MCCI_IN000002UV01", targetNamespace = "urn:hl7-org:v3", partName = "Body")
	@Action(input = "urn:hl7-org:v3:PRPA_IN201302UV02", output = "urn:hl7-org:v3:MCCI_IN000002UV01")
	@WebMethod(operationName = "PIXManager_PRPA_IN201302UV02", action = "urn:hl7-org:v3:PRPA_IN201302UV02")
	@WSDLDocumentation("This method is for updating patient information. Patient Registry Record Revised(IHE_ITI Vol 2b - Seccion: 3.44.4.1)")
	public org.hl7.v3.MCCIIN000002UV01 pixManagerPRPAIN201302UV02 (
		@WebParam(partName = "Body", name = "PRPA_IN201302UV02", targetNamespace = "urn:hl7-org:v3")
		org.hl7.v3.PRPAIN201302UV02 body) {
	}

	/**
	 * Returns all the identifiers of a patient.
	 * Patient Registry Get Identifiers Query (IHE_ITI Vol 2b - Seccion: 3.45.4)
	 * @param body PRPA_IN201309UV02 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/06_PIXQuery1.xml">link</a>
	 * @return PRPA_IN201310UV02 @see <a href="ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/07_PIXQuery1Response.xml">link</a>
	 */
	@WebResult(name = "PRPA_IN201310UV02", targetNamespace = "urn:hl7-org:v3", partName = "Body")
	@Action(input = "urn:hl7-org:v3:PRPA_IN201309UV02", output = "urn:hl7-org:v3:PRPA_IN201310UV02")
	@WebMethod(operationName = "PIXManager_PRPA_IN201309UV02", action = "urn:hl7-org:v3:PRPA_IN201309UV02")
	@WSDLDocumentation("Returns all the identifiers of a patient. Patient Registry Get Identifiers Query (IHE_ITI Vol 2b - Seccion: 3.45.4)")
	public org.hl7.v3.PRPAIN201310UV02 pixManagerPRPAIN201309UV02(
		@WebParam(partName = "Body", name = "PRPA_IN201309UV02", targetNamespace = "urn:hl7-org:v3")
		org.hl7.v3.PRPAIN201309UV02 body) {
		
	}
}
