package com.janpix.rup.services.mappings;

import grails.test.mixin.*

import javax.xml.bind.JAXBElement
import javax.xml.namespace.QName

import com.janpix.hl7dto.hl7.v3.contracts.PRPAIN201301UV02
import com.janpix.hl7dto.hl7.v3.datatypes.AD
import com.janpix.hl7dto.hl7.v3.datatypes.CE
import com.janpix.hl7dto.hl7.v3.datatypes.PN
import com.janpix.hl7dto.hl7.v3.datatypes.TS
import com.janpix.rup.empi.City
import com.janpix.rup.exceptions.MessageMappingException
import com.janpix.rup.pixmanager.PlaceService

@TestFor(PIXContractMapper)
@Mock([PlaceService])
class PIXContractMapperTest {
	
//	public void testWhenHl7AddPatientObjectHasNameThenCheckThatTheMapMethodReturnsAPatientDomainObjectWithTheSameName() {
//		def placeServiceMocker = mockFor(PlaceService)
//		placeServiceMocker.demand.findByPlace { String cityName, String provinceName, String countryName -> return new City(name:"Luján")  }
//		def pixContractMapper = new PIXContractMapper()
//		pixContractMapper.placeService = placeServiceMocker.createMock()
//		
//		//BUILD ADD PATIENT MESSAGE WITH JUAN FIRST NAME AND PEREZ GARCIA LAST NAME
//		def patientName = new PN()
//		patientName.content.add(new JAXBElement<EnGiven>(new QName("given"), JAXBElement.class, "Juan"))
//		patientName.content.add(new JAXBElement<EnGiven>(new QName("family"), JAXBElement.class, "Perez García"))
//		def patientPerson = new PRPAMT201301UV02Person()
//		patientPerson.name.add(patientName)
//		patientPerson.administrativeGenderCode = new CE(code: "M") 
//		def subjectControlActProcess = new PRPAIN201301UV02MFMIMT700701UV01Subject1()
//		subjectControlActProcess.registrationEvent = new PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent()
//		subjectControlActProcess.registrationEvent.subject1 = new PRPAIN201301UV02MFMIMT700701UV01Subject2()
//		subjectControlActProcess.registrationEvent.subject1.patient = new PRPAMT201301UV02Patient()
//		subjectControlActProcess.registrationEvent.subject1.patient.patientPerson = patientPerson
//		def inPatientMessage = new PRPAIN201301UV02()
//		inPatientMessage.controlActProcess = new PRPAIN201301UV02MFMIMT700701UV01ControlActProcess()
//		inPatientMessage.controlActProcess.subject.add(subjectControlActProcess)
//		
//		def patient = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(inPatientMessage)
//
//		assert patient.givenName.firstName == "Juan"
//		assert patient.givenName.lastName == "Perez García"
//	}
//	
//	public void testWhenHl7AddPatientObjectHasNoFirstNameThenCheckThatTheMapMethodReturnsAPatientDomainObjectWithFirstNameAsNull() {
//		def placeServiceMocker = mockFor(PlaceService)
//		placeServiceMocker.demand.findByPlace { String cityName, String provinceName, String countryName -> return new City(name:"Luján")  }
//		def pixContractMapper = new PIXContractMapper()
//		pixContractMapper.placeService = placeServiceMocker.createMock()
//		
//		//BUILD ADD PATIENT MESSAGE WITH JUAN FIRST NAME AND PEREZ GARCIA LAST NAME
//		def patientName = new PN()
//		patientName.content.add(new JAXBElement<EnGiven>(new QName("family"), JAXBElement.class, "Perez García"))
//		def patientPerson = new PRPAMT201301UV02Person()
//		patientPerson.name.add(patientName)
//		patientPerson.administrativeGenderCode = new CE(code: "M")
//		def subjectControlActProcess = new PRPAIN201301UV02MFMIMT700701UV01Subject1()
//		subjectControlActProcess.registrationEvent = new PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent()
//		subjectControlActProcess.registrationEvent.subject1 = new PRPAIN201301UV02MFMIMT700701UV01Subject2()
//		subjectControlActProcess.registrationEvent.subject1.patient = new PRPAMT201301UV02Patient()
//		subjectControlActProcess.registrationEvent.subject1.patient.patientPerson = patientPerson
//		def inPatientMessage = new PRPAIN201301UV02()
//		inPatientMessage.controlActProcess = new PRPAIN201301UV02MFMIMT700701UV01ControlActProcess()
//		inPatientMessage.controlActProcess.subject.add(subjectControlActProcess)
//		
//		def patient = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(inPatientMessage)
//
//		assert patient.givenName.firstName == null
//		assert patient.givenName.lastName == "Perez García"
//	}
//	
//	public void testWhenHl7AddPatientObjectHasBirthTimeCompleteCheckThePersonHasDayPrecissionAndCorrectDate() {
//		def placeServiceMocker = mockFor(PlaceService)
//		placeServiceMocker.demand.findByPlace { String cityName, String provinceName, String countryName -> return new City(name:"Luján")  }
//		def pixContractMapper = new PIXContractMapper()
//		pixContractMapper.placeService = placeServiceMocker.createMock()
//		
//		def patientName = new PN()
//		patientName.content.add(new JAXBElement<EnGiven>(new QName("family"), JAXBElement.class, "Perez García"))
//		def patientPerson = new PRPAMT201301UV02Person()
//		patientPerson.name.add(patientName)
//		patientPerson.administrativeGenderCode = new CE(code: "M")
//		patientPerson.birthTime = new TS(value: "19850523")
//		def subjectControlActProcess = new PRPAIN201301UV02MFMIMT700701UV01Subject1()
//		subjectControlActProcess.registrationEvent = new PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent()
//		subjectControlActProcess.registrationEvent.subject1 = new PRPAIN201301UV02MFMIMT700701UV01Subject2()
//		subjectControlActProcess.registrationEvent.subject1.patient = new PRPAMT201301UV02Patient()
//		subjectControlActProcess.registrationEvent.subject1.patient.patientPerson = patientPerson
//		def inPatientMessage = new PRPAIN201301UV02()
//		inPatientMessage.controlActProcess = new PRPAIN201301UV02MFMIMT700701UV01ControlActProcess()
//		inPatientMessage.controlActProcess.subject.add(subjectControlActProcess)
//		
//		def patient = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(inPatientMessage)
//
//		assert patient.birthdate.date == new GregorianCalendar(1985,5,23).getTime()
//	}
//	
//	
//	public void testWhenHl7AddPatientObjectHasABadCityNameThenShouldReturnAMessageMappingException() {
//		def placeServiceMocker = mockFor(PlaceService)
//		placeServiceMocker.demand.findByPlace { String cityName, String provinceName, String countryName -> return null  }
//		def pixContractMapper = new PIXContractMapper()
//		pixContractMapper.placeService = placeServiceMocker.createMock()
//		
//		//BUILD ADD PATIENT MESSAGE WITH JUAN FIRST NAME AND PEREZ GARCIA LAST NAME
//		def patientName = new PN()
//		patientName.content.add(new JAXBElement<EnGiven>(new QName("given"), JAXBElement.class, "Juan"))
//		patientName.content.add(new JAXBElement<EnGiven>(new QName("family"), JAXBElement.class, "Perez García"))
//		def patientPerson = new PRPAMT201301UV02Person()
//		patientPerson.name.add(patientName)
//		patientPerson.administrativeGenderCode = new CE(code: "M")
//		def addr = new AD()
//		addr.content.add(new JAXBElement<AdxpCity>(new QName("city"), JAXBElement.class, "Bad name"))
//		patientPerson.addr.add(addr)
//		def subjectControlActProcess = new PRPAIN201301UV02MFMIMT700701UV01Subject1()
//		subjectControlActProcess.registrationEvent = new PRPAIN201301UV02MFMIMT700701UV01RegistrationEvent()
//		subjectControlActProcess.registrationEvent.subject1 = new PRPAIN201301UV02MFMIMT700701UV01Subject2()
//		subjectControlActProcess.registrationEvent.subject1.patient = new PRPAMT201301UV02Patient()
//		subjectControlActProcess.registrationEvent.subject1.patient.patientPerson = patientPerson
//		def inPatientMessage = new PRPAIN201301UV02()
//		inPatientMessage.controlActProcess = new PRPAIN201301UV02MFMIMT700701UV01ControlActProcess()
//		inPatientMessage.controlActProcess.subject.add(subjectControlActProcess)
//		shouldFail(MessageMappingException) {
//			def patient = pixContractMapper.mapPersonFromhl7v3AddNewPatientMessage(inPatientMessage)
//		}
//	}

}
