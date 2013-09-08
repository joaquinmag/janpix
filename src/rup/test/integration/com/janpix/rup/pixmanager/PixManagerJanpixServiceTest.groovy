package com.janpix.rup.pixmanager;

import static org.junit.Assert.*

import org.junit.Test

import com.janpix.rup.empi.Address
import com.janpix.rup.empi.ExtendedDate
import com.janpix.rup.empi.Identifier
import com.janpix.rup.empi.Person
import com.janpix.rup.infrastructure.dto.AddressDTO
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.CityDTO
import com.janpix.rup.infrastructure.dto.ExtendedDateDTO
import com.janpix.rup.infrastructure.dto.IdentifierDTO
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.infrastructure.dto.PersonNameDTO
import com.janpix.rup.services.contracts.ACKMessage
import com.janpix.rup.services.contracts.ACKQueryPatientMessage;
import com.janpix.rup.services.contracts.AddPatientRequestMessage
import com.janpix.rup.services.contracts.GetAllPossibleMatchedPatientsRequestMessage
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
	public void testAddNewPatientThenRetrieveIdentifiersAndCheckThatOnlyHavePIIdentifiers() {
		def assigningAuthPatId = "1"
		def assigningAuthOID = "2.16.840.1.113883.2.10.1"
		def assigningAuthName = "Hospital Italiano de Buenos Aires"
		def assigningAuthDTO = new AssigningAuthorityDTO(assigningAuthOID, assigningAuthName)
		def ackNewPatient = addNewPatient("Isabel", "Gimenez", "1985-05-15", assigningAuthOID, assigningAuthName, assigningAuthPatId, "24.365.363")
		
		def getIdentifiersRequestMessage = new GetIdentifiersRequestMessage(patientIdentifier: assigningAuthPatId, assigningAuthority: assigningAuthDTO, othersDomain: null)
		
		def ackIdentifiers = PIXManagerJanpixService.GetIdentifiersPatient(getIdentifiersRequestMessage)
		
		assert ackIdentifiers.patient.identifiers.findAll { it.type != Identifier.TYPE_IDENTIFIER_PI }.empty
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
	
	@Test
	public void testWhenAddNewPatientsAndAddNewIdentifierAndGetFirstPatientCheckAllIdentifiers() {
		def assigningAuthPatId = "1"
		def assigningAuthOID = "2.16.840.1.113883.2.10.1"
		def assigningAuthName = "Hospital Italiano de Buenos Aires"
		def assigningAuthDTO = new AssigningAuthorityDTO(assigningAuthOID, assigningAuthName)
		def ackNewPatient = addNewPatient("Isabel", "Gimenez", "1985-05-15", assigningAuthOID, assigningAuthName, assigningAuthPatId, "66.365.363")
		
		println "texto ack: ${ackNewPatient.text}"
		assert ackNewPatient.typeCode == ACKMessage.TypeCode.SuccededCreation
		
		def assigningAuthPat2Id = "2"
		def assigningAuthOID2 = "2.16.840.1.113883.2.10.11"
		def assigningAuthName2 = "Clinica Parque SRL"
		def assigningAuthDTO2 = new AssigningAuthorityDTO(assigningAuthOID2, assigningAuthName2)
		def ackNewPatient2 = addNewPatient("Isabel", "Gimenez", "1985-05-15", assigningAuthOID2, assigningAuthName2, assigningAuthPat2Id, "66.365.363")
		
		println "texto ack: ${ackNewPatient2.text}"
		assert ackNewPatient2.typeCode == ACKMessage.TypeCode.SuccededInsertion
		
		def getIdentifiersRequestMessage = new GetIdentifiersRequestMessage(patientIdentifier: assigningAuthPatId, assigningAuthority: assigningAuthDTO, othersDomain: null)
		def ackIdentifiers = PIXManagerJanpixService.GetIdentifiersPatient(getIdentifiersRequestMessage)
		
		assert ackIdentifiers.patient.uniqueId
		println "uniqueId: ${ackIdentifiers.patient.uniqueId}"
		
		assert ackIdentifiers.patient.identifiers.size() == 2
		ackIdentifiers.patient.identifiers.each {
			println "identifiers ${it.type} : ${it.number}"
		}
		assert ackIdentifiers.patient.identifiers.find { it.type == Identifier.TYPE_IDENTIFIER_PI && it.assigningAuthority.oid == assigningAuthOID2 }.number == assigningAuthPat2Id
	}
	
	@Test
	public void testGetAllIdentifiersPatientReturnAddedPatient(){

		def person = new PersonDTO(
			name: new PersonNameDTO(firstName: "Isabel",
									lastName: "Gimenez"),
			birthdate: new ExtendedDateDTO(date: "1985-05-15", precission: ExtendedDate.TYPE_PRECISSION_DAY),
			administrativeSex: Person.TYPE_SEX_FEMALE,
			address: [ new AddressDTO(type: Address.TYPE_CIVIL, street: "Siempreviva", number: "555", zipCode: "U3434ARR", city: new CityDTO(nameCity: "Luján", nameProvince: "AR-B", nameCountry: "AR")) ]
		)
		ACKQueryPatientMessage ackListPatient = PIXManagerJanpixService.GetAllPossibleMatchedPatients(
				new GetAllPossibleMatchedPatientsRequestMessage(person:person)
			)
		
		assert ackListPatient.patients.size() == 1
		assert ackListPatient.patients[0].name.firstName == "Isabel"
	}
	
	
	@Test
	public void testUpdatePatientInformation() {
		def assigningAuthPatId = "1"
		def assigningAuthOID = "2.16.840.1.113883.2.10.1"
		def assigningAuthName = "Hospital Italiano de Buenos Aires"
		def assigningAuthDTO = new AssigningAuthorityDTO(assigningAuthOID, assigningAuthName)
		def ackNewPatient = addNewPatient("Isabel", "Gimenez", "1985-05-15", assigningAuthOID, assigningAuthName, assigningAuthPatId, "66.365.363")

		def getIdentifiersRequestMessage = new GetIdentifiersRequestMessage(patientIdentifier: assigningAuthPatId, assigningAuthority: assigningAuthDTO, othersDomain: null)
		def ackIdentifiers = PIXManagerJanpixService.GetIdentifiersPatient(getIdentifiersRequestMessage)
		
		PatientDTO patient = new PatientDTO(uniqueId: ackIdentifiers.patient.uniqueId,
											name: new PersonNameDTO(firstName: "Isabel",
																lastName: "Gimenez"),
											birthdate: new ExtendedDateDTO(date: "1985-05-15", precission: ExtendedDate.TYPE_PRECISSION_DAY),
											administrativeSex: Person.TYPE_SEX_FEMALE,
											identifiers: [ new IdentifierDTO(type: Identifier.TYPE_IDENTIFIER_SS, number: "45676898987", assigningAuthority: new AssigningAuthorityDTO("2.16.32","Argentina")) ],
											address: [ new AddressDTO(type: Address.TYPE_CIVIL, street: "Juncal", number: "453", zipCode: "U3434ARR", city: new CityDTO(nameCity: "Luján", nameProvince: "AR-B", nameCountry: "AR")) ]
										)
		
		def ackUpdate = PIXManagerJanpixService.UpdatePatient(patient, assigningAuthDTO)
		println ackUpdate
	}
	
	def addNewPatient(def firstName, def lastName, def birthDate, def assigningAuthOID, def assigningAuthName, def assigningAuthPatId, def personDNI) {
		def assigningAuthDTO = new AssigningAuthorityDTO(assigningAuthOID, assigningAuthName)
		def dniAssigningAuthDTO = new AssigningAuthorityDTO("2.16.32","Argentina")
		def person = new PersonDTO(
			name: new PersonNameDTO(firstName: firstName,
									lastName: lastName),
			birthdate: new ExtendedDateDTO(date: birthDate, precission: ExtendedDate.TYPE_PRECISSION_DAY),
			administrativeSex: Person.TYPE_SEX_FEMALE,
			identifiers: [ new IdentifierDTO(type: Identifier.TYPE_IDENTIFIER_DNI, number: personDNI, assigningAuthority: dniAssigningAuthDTO) ],
			address: [ new AddressDTO(type: Address.TYPE_CIVIL, street: "Siempreviva", number: "555", zipCode: "U3434ARR", city: new CityDTO(nameCity: "Luján", nameProvince: "AR-B", nameCountry: "AR")) ]
		)

		def ackNewPatient = PIXManagerJanpixService.AddNewPatient(new AddPatientRequestMessage(person: person, healthEntity: assigningAuthDTO, organizationId: assigningAuthPatId))
	}

}

