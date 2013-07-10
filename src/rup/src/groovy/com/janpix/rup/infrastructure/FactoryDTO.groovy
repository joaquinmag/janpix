package com.janpix.rup.infrastructure

import com.janpix.rup.empi.Patient

//FIXME hacer que devuelva dtos
class FactoryDTO {
	public static Patient buildPatientDTO(Patient patient){
		Patient dto = new Patient();
		dto.uniqueId = patient.uniqueId
		dto.givenName = patient.givenName
		dto.birthdate = patient.birthdate
		dto.administrativeSex = patient.administrativeSex
		dto.maritalStatus = patient.maritalStatus
		dto.birthplace = patient.birthplace
		dto.multipleBirthIndicator = patient.multipleBirthIndicator
		dto.organDonorIndicator = patient.organDonorIndicator
		dto.deathdate = patient.deathdate
		dto.identifiers.addAll(patient.identifiers)
		dto.phoneNumbers.addAll(patient.phoneNumbers)
		dto.addresses.addAll(patient.addresses)
		
		return dto;	
	}
}
