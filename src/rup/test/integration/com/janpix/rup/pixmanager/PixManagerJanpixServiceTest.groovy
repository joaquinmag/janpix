package com.janpix.rup.pixmanager;

import static org.junit.Assert.*;

import org.junit.Test;

import com.janpix.rup.empi.ExtendedDate;
import com.janpix.rup.empi.Person;
import com.janpix.rup.empi.Identifier;
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.ExtendedDateDTO
import com.janpix.rup.infrastructure.dto.IdentifierDTO
import com.janpix.rup.infrastructure.dto.PatientDTO;
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.infrastructure.dto.PersonNameDTO
import com.janpix.rup.services.contracts.ACKMessage;
import com.janpix.rup.services.contracts.AddPatientRequestMessage
import com.janpix.rup.services.contracts.GetIdentifiersRequestMessage

class PixManagerJanpixServiceTest {

	def PIXManagerJanpixService
	
	@Test
	public void testAddNewPatientAndGetUniqueIdIdentifiersPatient() {
		def assigningAuthDTO = new AssigningAuthorityDTO("2.16.840.1.113883.2.10.1", "Hospital Italiano de Buenos Aires")
		def orgId = "1"
		def person = new PersonDTO(
			name: new PersonNameDTO(firstName: "Isabel", 
									lastName: "Gimenez"),
			birthdate: new ExtendedDateDTO(date: "1985-05-15", precission: ExtendedDate.TYPE_PRECISSION_DAY),
			administrativeSex: Person.TYPE_SEX_FEMALE
		)
		def ackNewPatient = PIXManagerJanpixService.AddNewPatient(new AddPatientRequestMessage(person: person, healthEntity: assigningAuthDTO, organizationId: orgId))
		
		assert ackNewPatient.typeCode == ACKMessage.TypeCode.SuccededCreation
		
		def getIdentifiersRequestMessage = new GetIdentifiersRequestMessage(patientIdentifier: orgId, assigningAuthority: assigningAuthDTO, othersDomain: null)
		
		def ackIdentifiers = PIXManagerJanpixService.GetIdentifiersPatient(getIdentifiersRequestMessage)
		
		println "uniqueId: ${ackIdentifiers.patient.uniqueId}"
		assert ackIdentifiers.patient.uniqueId		
	}

}

