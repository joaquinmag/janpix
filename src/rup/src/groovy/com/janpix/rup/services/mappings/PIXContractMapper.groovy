package com.janpix.rup.services.mappings

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlRootElement

import org.hl7.v3.*

import com.janpix.hl7dto.hl7.v3.contracts.MCCIIN000002UV01
import com.janpix.hl7dto.hl7.v3.contracts.PRPAIN201301UV02
import com.janpix.hl7dto.hl7.v3.datatypes.AD
import com.janpix.hl7dto.hl7.v3.datatypes.CE
import com.janpix.hl7dto.hl7.v3.datatypes.CS
import com.janpix.hl7dto.hl7.v3.datatypes.II
import com.janpix.hl7dto.hl7.v3.datatypes.PN
import com.janpix.hl7dto.hl7.v3.datatypes.TEL
import com.janpix.hl7dto.hl7.v3.datatypes.TS
import com.janpix.hl7dto.hl7.v3.datatypes.enums.CommunicationFunctionType
import com.janpix.hl7dto.hl7.v3.messages.Acknowledgement
import com.janpix.hl7dto.hl7.v3.messages.Device;
import com.janpix.hl7dto.hl7.v3.messages.HL7MessageReceiver;
import com.janpix.hl7dto.hl7.v3.messages.HL7MessageSender;
import com.janpix.hl7dto.hl7.v3.messages.RegistrationEvent;
import com.janpix.hl7dto.hl7.v3.messages.ack.AcknowledgementDetail
import com.janpix.hl7dto.hl7.v3.messages.ack.TargetMessage
import com.janpix.rup.empi.Address
import com.janpix.rup.empi.AssigningAuthority
import com.janpix.rup.empi.ExtendedDate
import com.janpix.rup.empi.HealthEntity
import com.janpix.rup.empi.Person
import com.janpix.rup.empi.PersonName
import com.janpix.rup.empi.PhoneNumber
import com.janpix.rup.exceptions.MessageMappingException
import com.janpix.rup.services.contracts.ACKMessage

/**	
 * Maps domain objects to PIX Web service
 */
class PIXContractMapper {
	
	def placeService
	def hl7Helper
	def actualDate
	
	
	II getMessageIdentifier(PRPAIN201301UV02 message) {
		return message.id
	}
	
	String getPatientId(PRPAIN201301UV02 message) {
		return message.controlActProcess.subject[0].registrationEvent.subject1.patient.id[0].extension
	}
	
	HealthEntity mapSenderToHealthEntity(PRPAIN201301UV02 message) {
		def oid = message.sender.device.id[0].root
		def name = message.sender.device.id[0].assigningAuthorityName
		return new HealthEntity(name: name, oid: oid)
	}
	
	HL7MessageReceiver mapHealthEntityToACKReceiver(AssigningAuthority authority) {
		def receiver = new HL7MessageReceiver()
		receiver.typeCode = CommunicationFunctionType.RCV
		receiver.device = buildACKDevice(authority)
		return receiver
	}
	
	HL7MessageSender mapAssigningAuthorityToACKSender(AssigningAuthority authority) {
		def sender = new HL7MessageSender()
		sender.typeCode = CommunicationFunctionType.SND
		sender.device = buildACKDevice(authority)
		return sender
	}
	
	Device buildACKDevice(AssigningAuthority authority) {
		def device = new Device()
		device.determinerCode = "INSTANCE"
		device.id.add(new II(root: authority.oid, assigningAuthorityName: authority.name))
		return device
	}
	
	MCCIIN000002UV01 mapACKMessageToHL7AcceptAcknowledgmentMessage(ACKMessage ackMessage, II messageIdentifier, AssigningAuthority receiver, AssigningAuthority sender) {
		def ackHl7 = new MCCIIN000002UV01()
		ackHl7.itsVersion = "XML_1.0" //TODO tomar valor como constante de la config
		ackHl7.id = messageIdentifier //FIXME esto tiene que ser un valor único
		ackHl7.creationTime = hl7Helper.buildHl7DateTime(actualDate())
		
		def messageName = (MCCIIN000002UV01.class.getAnnotation(XmlRootElement) as XmlRootElement).name()
		ackHl7.interactionId = hl7Helper.buildInteractionId(messageName)
		ackHl7.processingCode = hl7Helper.buildProcessingCode()
		ackHl7.processingModeCode = hl7Helper.buildProcessingModeCode()
		ackHl7.acceptAckCode = hl7Helper.buildAcceptAckCode()
		ackHl7.receiver.add(mapHealthEntityToACKReceiver(receiver))
		ackHl7.sender = mapAssigningAuthorityToACKSender(sender)
		def ackHl7spec = buildAcknowledgement(ackMessage)
		ackHl7spec.targetMessage = buildTargetMessage(messageIdentifier)
		ackHl7.acknowledgement.add(ackHl7spec)
		
		return ackHl7
	}
	
	private Acknowledgement buildAcknowledgement(ACKMessage ackMessage) {
		def ackHl7spec = new Acknowledgement()
		switch (ackMessage.typeCode) {
			case [ ACKMessage.TypeCode.SuccededCreation, ACKMessage.TypeCode.SuccededInsertion ]:
				ackHl7spec.typeCode = new CS(code:"CA")
				break
			case [ ACKMessage.TypeCode.PossibleMatchingPatientsError, ACKMessage.TypeCode.ShortDemographicError, ACKMessage.TypeCode.IdentifierError ]:
				ackHl7spec.typeCode = new CS(code:"CR")
				break
			default:
				ackHl7spec.typeCode = new CS(code:"CE")
		}
		ackHl7spec.acknowledgementDetail.add(buildAcknowledgementDetail(ackMessage))
		return ackHl7spec
	}
	
	private TargetMessage buildTargetMessage(II identifier) {
		def targetMessage = new TargetMessage()
		targetMessage.id = identifier
		return targetMessage
	}
	
	private AcknowledgementDetail buildAcknowledgementDetail(ACKMessage ackMessage) {
		def ackDetail = new AcknowledgementDetail()
		ackDetail.code = new CE(code: ackMessage.typeCode.exceptionCode())
		return ackDetail
	}

	/**
	 * maps PRPAIN201301UV02 message to Person
	 * @param inPatientMessage {@link org.hl7.v3.PRPAIN201301UV02 PRPAIN201301UV02} object
	 * @return {@link com.janpix.rup.empi.Person Person} mapped from PRPAIN201301UV02.
	 */
	Person mapPersonFromhl7v3AddNewPatientMessage(PRPAIN201301UV02 inPatientMessage) {
		RegistrationEvent regEvent = inPatientMessage.controlActProcess.subject[0].registrationEvent
		def patient = regEvent.subject1.patient
		com.janpix.hl7dto.hl7.v3.messages.Person patientPerson = patient.patientPerson
		
		//set name
		def person = new Person()
		person.givenName = new PersonName()
		person.givenName.firstName = patientPerson.name[0]?.given
		person.givenName.lastName = patientPerson.name[0]?.family
		person.administrativeSex =  patientPerson.administrativeGenderCode.code
		person.maritalStatus = patientPerson.maritalStatusCode?.code
		// TODO person.birthplace = placeService.findByPlace(cityName, provinceName, countryName)patientPerson.birthPlace?.addr.city
		person.birthdate = convertToExtendedDateFromTS(patientPerson.birthTime)
		person.multipleBirthIndicator = patientPerson.multipleBirthInd ? patientPerson.multipleBirthInd.value : false
		if (patientPerson.deceasedInd?.value?.value) {
			person.deathdate = convertToExtendedDateFromTS(patientPerson.deceasedTime)
		}
		patientPerson.addr.each { AD it ->
			person.addToAddresses(convertToAddress(it))
		}
		patientPerson.telecom.each { TEL it ->
			person.addToPhoneNumbers(convertToPhoneNumber(it))
		}
		return person
	}
	
	private PhoneNumber convertToPhoneNumber(TEL hl7Telephone) {
		return new PhoneNumber(number: hl7Telephone.value)
	}
	
	private Address convertToAddress(AD hl7Address) {
		def address = new Address()
		def street = hl7Address.streetAddressLine?.split(" ", 2)
		if (street) {
			if (!(street[0]?.isNumber()))
				throw new MessageMappingException('PRPAIN201301UV02 address list field "streetAddressLine" must contain format "<number> <street name>", notice a space character between fields.')
			address.number = street[0]
			address.street = street[1]
		}
		def additionalLocator = hl7Address.additionalLocator?.split(",", 2)
		if (additionalLocator) { 
			address.floor = additionalLocator[0]
			address.department = additionalLocator[1].trim()
		}
		def cityName = hl7Address.city
		def provinceName = hl7Address.province
		def countryName = hl7Address.country
		def city = placeService.findByPlace(cityName, provinceName, countryName)
		if (!city)
			throw new MessageMappingException('PRPAIN201301UV02 must contain a valid city, state and country names.')
		address.city = city
		address.zipCode = hl7Address.postalCode
		return address
	}
	
	private ExtendedDate convertToExtendedDateFromTS(TS hl7Date) {
		def precission = ExtendedDate.TYPE_PRECISSION_UNKNOWN
		def year = 1900
		def month = 1
		def day = 1
		if (hl7Date?.value?.length() >= 4) {
			year = hl7Date.value.substring(0, 4) as int
			precission = ExtendedDate.TYPE_PRECISSION_YEAR
		}
		if (hl7Date?.value?.length() >= 6) {
			month = hl7Date.value.substring(4, 6) as int
			precission = ExtendedDate.TYPE_PRECISSION_MONTH
		}
		if (hl7Date?.value?.length() >= 8) {
			day = hl7Date.value.substring(6, 8) as int
			precission = ExtendedDate.TYPE_PRECISSION_DAY
		}
		def date = buildDate(year, month, day)
		return new ExtendedDate(date: date, precission: precission)
	}
	
	private static Date buildDate(int year, int month, int day) {
		def calendar = new GregorianCalendar(year, month, day)
		return calendar.getTime()
	}
	
	public void validateHl7V3AddNewPatientMessage(PRPAIN201301UV02 inPatientMessage) {
		if (inPatientMessage.controlActProcess.subject.isEmpty())
			throw new MessageMappingException("PRPAIN201301UV02 message must contain one subject")
		if (inPatientMessage.controlActProcess.subject.size() > 1)
			throw new MessageMappingException("PRPAIN201301UV02 message can't contain more than one subject at this implementation")
		if (inPatientMessage.controlActProcess.subject[0].registrationEvent.subject1.patient.patientPerson.administrativeGenderCode == null)
			throw new MessageMappingException("PRPAIN201301UV02 message must contain an administrativeGenderCode")
		
		if (inPatientMessage.sender == null || inPatientMessage.sender.device.id.size() != 1) {
			throw new MessageMappingException("PRPAIN201301UV02 sender must exists and have only one unique identifier")
		} 
	}
	
}
