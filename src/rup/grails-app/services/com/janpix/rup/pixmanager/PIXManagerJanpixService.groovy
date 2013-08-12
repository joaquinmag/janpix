package com.janpix.rup.pixmanager

import java.util.List;

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.NotImplementedException
import org.apache.cxf.annotations.WSDLDocumentation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.hl7dto.hl7.v3.contracts.*
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.PatientDTO
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.services.contracts.ACKMessage
import com.janpix.rup.services.contracts.ACKMessage.TypeCode
import com.janpix.rup.services.contracts.AddPatientRequestMessage;
import com.janpix.rup.services.contracts.GetIdentifiersRequestMessage;

/*@WebService(
	name = "JanpixPixManager",
	targetNamespace = "jpx:ar.com.janpix",
	serviceName = "JanpixPixManager_Service",
	portName = "JanpixPixManager_PortType"
	)*/
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)
class PIXManagerJanpixService 
{
	static transactional = false
	
	def pixManagerService

	/**
	 * Add new patients to the PIX. 
	 */	
	@WebMethod
	public ACKMessage AddNewPatient(@WebParam(name = "addPatientRequestMessage")AddPatientRequestMessage requestMessage) 
	{
		return pixManagerService.patientRegistryRecordAdded(requestMessage.person,requestMessage.healthEntity, requestMessage.organizationId)
	}

	/**
	 * This method is for updating patient information
	 */
	public ACKMessage UpdatePatient(PatientDTO patient,AssigningAuthorityDTO healthEntity) {
		throw new NotImplementedException("Method not allowed at this RUP implementation.") 
	}


	/**
	 * Returns all the identifiers of a patient.
	 */
	@WebMethod
	public ACKMessage GetIdentifiersPatient(@WebParam(name = "getIdentifiersRequestMessage")GetIdentifiersRequestMessage requestMessage) 
	{
		return pixManagerService.patientRegistryGetIdentifiersQuery(
					requestMessage.patientIdentifier,
					requestMessage.assigningAuthority,
					requestMessage.othersDomain
					)
	}


	@WSDLDocumentation("Add new patients to the PIX without validate matching with other patients.")
	public ACKMessage AddNewPatientWithoutValidation(PersonDTO person, AssigningAuthorityDTO healthEntity, String organizationId) {
		throw new NotImplementedException("Method not allowed at this RUP implementation.") 
	}

	@WSDLDocumentation("Returns all patient maching with Patient")
	public ACKMessage GetAllPossibleMatchedPatients(PersonDTO person) {
		throw new NotImplementedException("Method not allowed at this RUP implementation.") 
	}
}
