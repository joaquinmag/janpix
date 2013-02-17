package com.jampix.rup.dto.mappings

import com.jampix.rup.dto.PatientDTO;
import com.janpix.rup.empi.Patient;

class PersonMapper {

	static Patient mapFromDto(PatientDTO patientDto) {
		def patient = new Patient()
		patient.givenName = patientDto.patientName
		patient
	}
	
}
