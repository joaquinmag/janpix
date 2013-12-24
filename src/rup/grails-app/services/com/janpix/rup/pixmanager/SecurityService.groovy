package com.janpix.rup.pixmanager

import com.janpix.rup.empi.AssigningAuthority
import com.janpix.rup.empi.Identifier
import com.janpix.rup.empi.MatchRecord
import com.janpix.rup.empi.Patient
import com.janpix.rup.empi.PatientIdentifier
import com.janpix.rup.empi.Person
import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.DuplicateAuthorityIdentifierException
import com.janpix.rup.exceptions.identifier.IdentifierException
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.IdentifierDTO
import com.janpix.rup.infrastructure.dto.PatientDTO
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.services.contracts.ACKMessage
import com.janpix.rup.services.contracts.ValidatePatientRequestMessage;
import com.janpix.rup.services.contracts.ACKMessage.TypeCode



/**
 * Servicio que se utiliza para la seguridad de los pacientes
 */
class SecurityService {
	def EMPIService
	def mapperDomainDto
	static transactional = false
	
	
	ACKMessage validatePatient(PatientDTO patientDTO, AssigningAuthorityDTO authorityDTO){
		try{
			// Se valida la autoridad
			// TODO hacer
			
			// Se obtiene el paciente y se valida que exista
			Patient patient = EMPIService.findPatientByUUID(new PatientIdentifier(mainId:patientDTO.uniqueId))
			if(!patient)
				return new ACKMessage(typeCode:TypeCode.DontExistingPatientError,text:"No existe el paciente con ID "+patientDTO.uniqueId)
			
			// Validar que la contraseña sea correcta
			String pass = patientDTO.identifiers.find{it->it.type == Identifier.TYPE_IDENTIFIER_PW}
			this.validatePass(pass,patient)
			
			
			return new ACKMessage(typeCode:TypeCode.SuccededQuery,patient:patient.convert(mapperDomainDto))
		
		}
		catch(Exception e){
			
		}
	}
	
	private void validatePass(String pass,Patient patient){
		// TODO hacer
	}

}
