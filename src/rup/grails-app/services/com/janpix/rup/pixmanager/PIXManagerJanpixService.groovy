package com.janpix.rup.pixmanager

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.ParameterStyle

import org.apache.commons.lang.NotImplementedException
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.hl7dto.hl7.v3.contracts.*
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.PatientDTO
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.services.contracts.ACKMessage
import com.janpix.rup.services.contracts.ACKQueryPatientMessage
import com.janpix.rup.services.contracts.AddPatientRequestMessage
import com.janpix.rup.services.contracts.GetAllPossibleMatchedPatientsRequestMessage
import com.janpix.rup.services.contracts.GetIdentifiersRequestMessage
import com.janpix.rup.services.contracts.UpdatePatientRequestMessage
import com.janpix.rup.services.contracts.ACKMessage.TypeCode


@WebService(serviceName = "PIXManagerJanpixService")
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
	public ACKMessage AddNewPatient(AddPatientRequestMessage requestMessage) {
		return pixManagerService.patientRegistryRecordAdded(requestMessage.person,requestMessage.healthEntity, requestMessage.organizationId)
	}

	/**
	 * This method is for updating patient information
	 */
	public ACKMessage UpdatePatient(UpdatePatientRequestMessage updatePatientRequestMessage) {
		return pixManagerService.patientRegistryRecordRevised(updatePatientRequestMessage.patient, updatePatientRequestMessage.healthEntity)
	}


	/**
	 * Returns all the identifiers of a patient.
	 */
	@WebMethod
	public ACKMessage GetIdentifiersPatient(GetIdentifiersRequestMessage requestMessage) {
		return pixManagerService.patientRegistryGetIdentifiersQuery(
					requestMessage.patientIdentifier,
					requestMessage.assigningAuthority,
					requestMessage.othersDomain
					)
	}

	@WebMethod
	public ACKMessage AddNewPatientWithoutValidation(AddPatientRequestMessage requestMessage) {
		return pixManagerService.patientRegistryRecordAddedWithoutMatching(requestMessage.person,requestMessage.healthEntity, requestMessage.organizationId)
	}

	@WebMethod
	public ACKQueryPatientMessage GetAllPossibleMatchedPatients(GetAllPossibleMatchedPatientsRequestMessage requestMessage) {
		 List<PatientDTO> patients = pixManagerService.getAllPossibleMatchedPatients(requestMessage.person)
		 return new ACKQueryPatientMessage(patients:patients,typeCode: TypeCode.SuccededQuery)
	}
}
