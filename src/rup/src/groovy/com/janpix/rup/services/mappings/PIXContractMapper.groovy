package com.janpix.rup.services.mappings

import com.janpix.rup.empi.Patient;
import com.janpix.rup.services.contracts.AddNewPatientRequestMessage;

/**
 * Convierto los objetos de un contrato a objetos de dominio y vicecersa. 
 * Los objetos del contrato que convierte son los correspondientes a los utilizados en el PIXManagerService. 
 */
class PIXContractMapper {

	static Patient mapFromRequestMessage(AddNewPatientRequestMessage requestMessage) {
		def patient = new Patient()
		patient.givenName = requestMessage.patientRegisterMessage.controlActProcess[0].subject[0].registrationEvent.subject1.patient.patientPerson.name.given
		patient
	}
	
}
