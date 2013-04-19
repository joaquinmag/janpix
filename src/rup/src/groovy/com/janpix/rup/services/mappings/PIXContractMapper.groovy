package com.janpix.rup.services.mappings

import javax.xml.bind.JAXBElement;

import org.hl7.v3.*

import com.janpix.rup.empi.Address
import com.janpix.rup.empi.City
import com.janpix.rup.empi.ExtendedDate
import com.janpix.rup.empi.Person
import com.janpix.rup.empi.PersonName
import com.janpix.rup.exceptions.MessageMappingException

/**
 * Maps domain objects to PIX Web service
 */
class PIXContractMapper {
	
	def placeService

	/**
	 * maps PRPAIN201301UV02 message to Person
	 * @param inPatientMessage {@link org.hl7.v3.PRPAIN201301UV02 PRPAIN201301UV02} object
	 * @return {@link com.janpix.rup.empi.Person Person} mapped from PRPAIN201301UV02.
	 */
	Person mapPersonFromhl7v3AddNewPatientMessage(PRPAIN201301UV02 inPatientMessage) {
		validateHl7V3AddNewPatientMessage(inPatientMessage)
		
		PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent regEvent = inPatientMessage.controlActProcess.subject[0].registrationEvent
		PRPAMT201301UV02Person patientPerson = regEvent.subject1.patient.patientPerson.getValue()
		
		//set name
		def person = new Person()
		person.givenName = new PersonName()
		person.givenName.firstName = getNameFromPatient(patientPerson, "given")?.toString()
		person.givenName.lastName = getNameFromPatient(patientPerson, "family")?.toString()
		person.administrativeSex =  patientPerson.administrativeGenderCode.code
		person.maritalStatus = patientPerson.maritalStatusCode?.code
		person.birthplace = getValueFromSerializableList((patientPerson.birthPlace?.value as PRPAMT201301UV02BirthPlace)?.addr?.content, "city")?.value
		person.birthdate = convertToExtendedDateFromTS(patientPerson.birthTime)
		person.multipleBirthIndicator = patientPerson.multipleBirthInd ? patientPerson.multipleBirthInd.value : false
		if (patientPerson.deceasedInd?.value?.value) {
			person.deathDate = convertToExtendedDateFromTS(patientPerson.deceasedTime)
		}
		patientPerson.addr.each { AD it ->
			person.addresses.add(convertToAddress(it))
		}
		return person
	}
	
	private Address convertToAddress(AD hl7Address) {
		def address = new Address()
		def street = getValueFromSerializableList(hl7Address.content, "streetAddressLine")?.value.split(" ", 2)
		if (!street || !(street[0]?.isNumber()))
			throw new MessageMappingException('PRPAIN201301UV02 address list field "streetAddressLine" must contain format "<number> <street name>", notice a space character between fields.')
		address.number = street[0]
		address.street = street[1]
		def additionalLocator = getValueFromSerializableList(hl7Address.content, "additionalLocator")?.value.split(",", 2)
		if (additionalLocator) { 
			address.floor = additionalLocator[0]
			address.department = additionalLocator[1].trim()
		}
		def cityName = hl7Address.getValueFromSerializableList(hl7Address.content, "city")?.value
		def provinceName = hl7Address.getValueFromSerializableList(hl7Address.content, "state")?.value
		def countryName = hl7Address.getValueFromSerializableList(hl7Address.content, "country")?.value
		def city = placeService.findByPlace(cityName, provinceName, countryName)
		if (!city)
			throw new MessageMappingException('PRPAIN201301UV02 must contain a valid city, state and country names.')
		address.zipCode = getValueFromSerializableList(hl7Address.content, "postalCode")?.value
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
	
	/**
	 * Gets first value from PN parameter
	 * @param patientPerson PRPAMT201301UV02Person
	 * @param xmlName
	 * @return
	 */
	private String getNameFromPatient(PRPAMT201301UV02Person patientPerson, String xmlName) {
		def givenNameJaxb = null
		patientPerson.name.find { PN it ->
			givenNameJaxb = getValueFromSerializableList(it.content, xmlName)
			if (givenNameJaxb != null)
				true
			else
				false
		}
		return givenNameJaxb?.value
	}
	
	private JAXBElement<Serializable> getValueFromSerializableList(List<Serializable> content, String xmlName) {
		return content.find() { JAXBElement<Serializable> it ->
			return it.name.localPart == xmlName
		} as JAXBElement<Serializable>
	}
	
	private void validateHl7V3AddNewPatientMessage(PRPAIN201301UV02 inPatientMessage) {
		if (inPatientMessage.getControlActProcess().getSubject().isEmpty())
			throw new MessageMappingException("PRPAIN201301UV02 message must contain one subject")
		if (inPatientMessage.getControlActProcess().getSubject().size() > 1)
			throw new MessageMappingException("PRPAIN201301UV02 message can't contain more than one subject at this implementation")
		if (inPatientMessage.controlActProcess.subject[0].registrationEvent.subject1.patient.patientPerson.value.administrativeGenderCode == null)
			throw new MessageMappingException("PRPAIN201301UV02 message must contain an administrativeGenderCode")
	}
	
}
