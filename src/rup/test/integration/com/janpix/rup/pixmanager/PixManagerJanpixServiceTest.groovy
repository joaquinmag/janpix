package com.janpix.rup.pixmanager;

import static org.junit.Assert.*;

import org.junit.Test;

import com.janpix.rup.empi.ExtendedDate;
import com.janpix.rup.empi.Person;
import com.janpix.rup.empi.Identifier;
import com.janpix.rup.infrastructure.dto.AddressDTO
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.CityDTO
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
		def assigningAuthPatId = "1"
		def assigningAuthOID = "2.16.840.1.113883.2.10.1"
		def assigningAuthName = "Hospital Italiano de Buenos Aires"
		def assigningAuthDTO = new AssigningAuthorityDTO(assigningAuthOID, assigningAuthName)
		def ackNewPatient = addNewPatient("Isabel", "Gimenez", "1985-05-15", assigningAuthOID, assigningAuthName, assigningAuthPatId, "32.365.363")
		
		println "texto ack: ${ackNewPatient.text}"
		assert ackNewPatient.typeCode == ACKMessage.TypeCode.SuccededCreation
		
		def getIdentifiersRequestMessage = new GetIdentifiersRequestMessage(patientIdentifier: assigningAuthPatId, assigningAuthority: assigningAuthDTO, othersDomain: null)
		
		def ackIdentifiers = PIXManagerJanpixService.GetIdentifiersPatient(getIdentifiersRequestMessage)
		
		println "uniqueId: ${ackIdentifiers.patient.uniqueId}"
		assert ackIdentifiers.patient.uniqueId		
	}
	
	@Test
	public void testWhenAddTwoNewPatientsAndGetUniqueIdentifierFromFirstPatient() {
		def assigningAuthPatId = "1"
		def assigningAuthOID = "2.16.840.1.113883.2.10.1"
		def assigningAuthName = "Hospital Italiano de Buenos Aires"
		def assigningAuthDTO = new AssigningAuthorityDTO(assigningAuthOID, assigningAuthName)
		def ackNewPatient = addNewPatient("Isabel", "Gimenez", "1985-05-15", assigningAuthOID, assigningAuthName, assigningAuthPatId, "66.365.363")
		
		println "texto ack: ${ackNewPatient.text}"
		assert ackNewPatient.typeCode == ACKMessage.TypeCode.SuccededCreation
		
		def assigningAuthPat2Id = "1"
		def assigningAuthOID2 = "2.16.840.1.113883.2.10.11"
		def assigningAuthName2 = "Clinica Parque SRL"
		def assigningAuthDTO2 = new AssigningAuthorityDTO(assigningAuthOID2, assigningAuthName2)
		def ackNewPatient2 = addNewPatient("Gabriel", "Ramirez", "1985-05-11", assigningAuthOID2, assigningAuthName2, assigningAuthPat2Id, "66.653.362")
		
		println "texto ack: ${ackNewPatient2.text}"
		assert ackNewPatient2.typeCode == ACKMessage.TypeCode.SuccededCreation
		
		def getIdentifiersRequestMessage = new GetIdentifiersRequestMessage(patientIdentifier: assigningAuthPatId, assigningAuthority: assigningAuthDTO, othersDomain: null)
		def ackIdentifiers = PIXManagerJanpixService.GetIdentifiersPatient(getIdentifiersRequestMessage)
		
		assert ackIdentifiers.patient.uniqueId
		println "uniqueId: ${ackIdentifiers.patient.uniqueId}"

	}
	
	def addNewPatient(def firstName, def lastName, def birthDate, def assigningAuthOID, def assigningAuthName, def assigningAuthPatId, def personDNI) {
		def assigningAuthDTO = new AssigningAuthorityDTO(assigningAuthOID, assigningAuthName)
		def dniAssigningAuthDTO = new AssigningAuthorityDTO("2.16.32","Argentina")
		CityDTO city = new CityDTO(nameCity:"Venado Tuerto",nameProvince:"AR-S");
		def person = new PersonDTO(
			name: new PersonNameDTO(firstName: firstName,
									lastName: lastName),
			birthdate: new ExtendedDateDTO(date: birthDate, precission: ExtendedDate.TYPE_PRECISSION_DAY),
			administrativeSex: Person.TYPE_SEX_FEMALE,
			identifiers: [ new IdentifierDTO(type: Identifier.TYPE_IDENTIFIER_DNI, number: personDNI, assigningAuthority: dniAssigningAuthDTO) ],
			address:[new AddressDTO(type:"CIVIL",street:"Direccion",number:"numero",city:city)]
		)

		def ackNewPatient = PIXManagerJanpixService.AddNewPatient(new AddPatientRequestMessage(person: person, healthEntity: assigningAuthDTO, organizationId: assigningAuthPatId))
	}

}

