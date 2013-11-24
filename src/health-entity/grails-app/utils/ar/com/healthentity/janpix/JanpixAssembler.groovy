package ar.com.healthentity.janpix

import org.apache.commons.io.IOUtils

import ar.com.healthentity.City
import ar.com.healthentity.ClinicalDocument
import ar.com.healthentity.Patient
import ar.com.healthentity.SexType

import com.janpix.webclient.repodoc.ClinicalDocumentDTO
import com.janpix.webclient.rup.AddressDTO
import com.janpix.webclient.rup.AssigningAuthorityDTO
import com.janpix.webclient.rup.CityDTO
import com.janpix.webclient.rup.ExtendedDateDTO
import com.janpix.webclient.rup.IdentifierDTO
import com.janpix.webclient.rup.PersonDTO
import com.janpix.webclient.rup.PersonNameDTO
import com.janpix.webclient.rup.PhoneNumberDTO



/**
 * Clase encargada de transformar entidades de Janpix
 * a entidades de Health Entity
 * @author martin
 *
 */
class JanpixAssembler {
	
	/**
	 * Transforma Paciente de la Entidad Sanitaria en un PersonDTO de Janpix
	 * @return
	 */
	static PersonDTO toPerson(Patient patient){
		if(patient == null)
			return null
			
		PersonDTO person = new PersonDTO()
		person.name = new PersonNameDTO()
		person.name.firstName = patient.firstName
		person.name.lastName = patient.lastName
		person.birthdate = new ExtendedDateDTO()
		person.birthdate.precission = "Day"
		person.birthdate.date = patient.birthdate
		person.administrativeSex = (patient.sex == SexType.Masculino)?'M':'F'
		person.birthplace = JanpixAssembler.toCity(patient.city)
		
		person.addresses = new PersonDTO.Addresses()
		person.addresses.address.add(JanpixAssembler.toAddress(patient.addressName,patient.addressNumber,person.birthplace))
		
		PhoneNumberDTO phone = JanpixAssembler.toPhoneNumber(patient.phone)
		if(phone != null){
			person.phoneNumbers = new PersonDTO.PhoneNumbers()
			person.phoneNumbers.phoneNumber.add(phone)
		}
		
		person.identifiers = new PersonDTO.Identifiers()	
		person.identifiers.identifier.add(JanpixAssembler.toIdentifier(patient.dni))

		return person
	}
	
	/**
	 * Transforma City de la Entidad Sanitaria en una CityDTO de Janpix
	 * @return
	 */
	static CityDTO toCity(City city){
		if(city == null)
			return null
			
		CityDTO dto = new CityDTO()
		dto.nameCity = city.name
		dto.nameProvince = city.province?.name
		dto.nameCountry = city.province?.country
		
		return dto
	}
	
	/**
	 * Transforma Address de la Entidad Sanitaria en una AddressDTO de Janpix
	 * @return
	 */
	static AddressDTO toAddress(String addressName,String addressNumber,CityDTO city){
		if(!addressName || ! addressNumber)
			return null
		
		AddressDTO dto = new AddressDTO()
		dto.type = 'LEGAL'
		dto.street = addressName
		dto.number = addressNumber
		dto.city = city
		
		return dto
	}
	
	/**
	 * Transforma phone de la Entidad Sanitaria en una PhoneNumberDTO de Janpix
	 * @return
	 */
	static PhoneNumberDTO toPhoneNumber(String phone){
		if(!phone)
			return null
			
		PhoneNumberDTO dto = new PhoneNumberDTO()
		dto.type = 'HOME'
		dto.number = phone
		
		return dto
	}
	
	/**
	 * Transforma phone de la Entidad Sanitaria en una PhoneNumberDTO de Janpix
	 * @return
	 */
	static IdentifierDTO toIdentifier(String dni){
		if(!dni)
			return null
			
		IdentifierDTO dto = new IdentifierDTO()
		dto.type = 'DNI'
		dto.number = dni
		dto.assigningAuthority = new AssigningAuthorityDTO()
		dto.assigningAuthority.oid = "2.16.32" // Hermoso Hardcode
		dto.assigningAuthority.name = "Argentina" // Hermoso Hardcode
		
		return dto
	}
	
	static AssigningAuthorityDTO toAssigningAuthority(ConfigObject parameters){
		if(!parameters)
			return null
			
		AssigningAuthorityDTO dto = new AssigningAuthorityDTO()
		dto.name = parameters.name
		dto.oid = parameters.oid
		
		return dto
	}
	
	/**
	 * Transforma un Documento de janpix en un Documento de healthentity
	 * @return
	 */
	static ClinicalDocument fromDocument(ClinicalDocumentDTO janpixDocument){
		if(janpixDocument == null)
			return null
			
		ClinicalDocument document = new ClinicalDocument()
		document.name = janpixDocument.fileAttributes.filename
		document.mimeType = janpixDocument.fileAttributes.mimeType
		document.size = janpixDocument.fileAttributes.size
		document.binaryData = IOUtils.toByteArray(janpixDocument.binaryData.getInputStream());
		
		return document
	} 
}
