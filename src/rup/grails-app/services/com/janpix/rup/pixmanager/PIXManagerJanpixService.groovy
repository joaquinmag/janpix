package com.janpix.rup.pixmanager

import java.util.List;

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
class PIXManagerJanpixService {
	static transactional = false
	def pixManagerService



	/**
	 * Add new patients to the PIX. 
	 */	
	@WebMethod
	public ACKMessage AddNewPatient(PersonDTO patient, AssigningAuthorityDTO healthEntity, String organizationId) {
		
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
	public ACKMessage GetIdentifiersPatient(String patientIdentifier,AssigningAuthorityDTO assigningAuthority,List<AssigningAuthorityDTO> othersDomain) {
		throw new NotImplementedException("Method not allowed at this RUP implementation.")
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
