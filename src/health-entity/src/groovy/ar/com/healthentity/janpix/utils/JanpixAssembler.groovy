package ar.com.healthentity.janpix.utils

import java.util.Date;

import javax.activation.DataHandler
import javax.activation.DataSource
import javax.mail.util.ByteArrayDataSource

import org.apache.commons.io.IOUtils
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes as GA

import ar.com.healthentity.City
import ar.com.healthentity.ClinicalDocument
import ar.com.healthentity.Patient
import ar.com.healthentity.SexType
import ar.com.healthentity.Study
import ar.com.healthentity.StudyType;
import ar.com.healthentity.User

import com.janpix.webclient.repodoc.AuthorDTO
import com.janpix.webclient.repodoc.ClinicalDocumentDTO
import com.janpix.webclient.repodoc.FileAttributesDTO
import com.janpix.webclient.repodoc.HealthEntityDTO
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

	static final String DNI = 'DNI'
	static final String DAY = "Day"
	static final String MALE = 'M'
	static final String FEMALE = 'F'
	static final String LEGAL = 'LEGAL'
	static final String HOME = 'HOME'
	
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
		person.birthdate.precission = DAY
		person.birthdate.date = patient.birthdate
		person.administrativeSex = (patient.sex == SexType.Masculino)?MALE:FEMALE
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
		dto.type = LEGAL
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
		dto.type = HOME
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
		dto.type = DNI
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
	
	static HealthEntityDTO toHealthEntity(ConfigObject parameters){
		if(!parameters)
			return null
			
		HealthEntityDTO dto = new HealthEntityDTO()
		dto.name = parameters.name
		dto.oid = parameters.oid
		
		// TODO falta obtenerlo
		dto.healthcareFacilityTypeCode = "FRUTA"
		
		return dto
	}
	
	static com.janpix.webclient.regdoc.HealthEntityDTO toHealthEntityRegistro(ConfigObject parameters){
		if(!parameters)
			return null
			
		com.janpix.webclient.regdoc.HealthEntityDTO dto = new com.janpix.webclient.regdoc.HealthEntityDTO()
		dto.name = parameters.name
		dto.oid = parameters.oid
		
		// TODO falta obtenerlo
		dto.healthcareFacilityTypeCode = "FRUTA"
		
		return dto
	}
	
	static ClinicalDocumentDTO toClinicalDocument(Study study) {
		def ctx = SCH.servletContext.getAttribute(GA.APPLICATION_CONTEXT)
		String uploadsPath = ctx.servletContext.getRealPath("/uploads")
		
		def dto = new ClinicalDocumentDTO()
		def document = study.document
		def f = new File("${uploadsPath}/${document.fileLocation}")
		
		dto.binaryData = byteArrayToDataHandler(f.bytes, document.mimeType)
		dto.comments = study.observation
		dto.documentCreationStarted = study.date
		dto.documentCreationEnded = study.date
		dto.fileAttributes = toFileAttributes(document)
		dto.language = "es" //FIXME hardcode
		dto.name = study.title
		dto.patientId = study.patient.id
		dto.typeId = study.type.idStudyType
		dto.typeName = study.type.name

		dto.formatName = document.format


		return dto
	}
	
	static FileAttributesDTO toFileAttributes(ClinicalDocument document) {
		def fileAttr = new FileAttributesDTO()
		fileAttr.creationTime = document.dateCreated
		fileAttr.filename = document.filename
		fileAttr.mimeType = document.mimeType
		fileAttr.size = document.size
		fileAttr.uuid = "${document.size}${document.id}" //FIXME not a UUID
		return fileAttr
	}
	
	static AuthorDTO toAuthor(User user,ConfigObject paramHealthEntity){
		AuthorDTO author = new AuthorDTO();
		author.authorPerson = user.name
		// Supongo que el usuario tiene un solo rol
		author.authorRole = user.authorities[0].authority
		// TODO falta obtener
		author.authorSpecialty;
		
		// HealthEntity
		author.healthEntity = toHealthEntity(paramHealthEntity)
		
		return author;
		
	}
	
	/** FROMS **/
	
	/**
	 * Transforma un Documento de janpix en un Documento de healthentity
	 * @return
	 */
	static ClinicalDocument fromDocument(ClinicalDocumentDTO janpixDocument){
		if(janpixDocument == null)
			return null
			
		ClinicalDocument document = new ClinicalDocument()
		document.filename = janpixDocument.fileAttributes.filename
		document.mimeType = janpixDocument.fileAttributes.mimeType
		document.size = janpixDocument.fileAttributes.size
		//document.binaryData = IOUtils.toByteArray(janpixDocument.binaryData.getInputStream());
		document.format = janpixDocument.formatName
		
		
		return document
	} 
	
	/**
	 * Transforma un ClinicalDocument del Registro de documento en un Study de healhentity
	 * @param janpixDocument
	 * @return
	 */
	static Study fromRegisterDocument(com.janpix.webclient.regdoc.ClinicalDocumentDTO janpixDocument){
		if(janpixDocument == null)
			return null;
		
		Study study = new Study();
		study.title = janpixDocument.name
		study.date = janpixDocument.documentCreationStarted
		study.type = JanpixAssembler.fromTypeId(janpixDocument.typeId)
		study.observation = janpixDocument.comments
		study.isSynchro = true
		
		return study
	}
	
	/**
	 * Retorna un Tipo a partir de su ID
	 * Sino existen dicho ID retorna null
	 * @param id
	 * @return
	 */
	static fromTypeId(Long id){
		return StudyType.findByIdStudyType(id)
	}
	
	/**
	 * Transforma PersonDTO de Janpix en un Patient de la Entidad Sanitaria
	 * @return
	 */
	static Patient fromPatient(PersonDTO person){
		if(!person)
			return null
			
		Patient patient = new Patient()
		patient.firstName = person.name?.firstName
		patient.lastName = person.name?.lastName
		patient.dni = JanpixAssembler.fromIdentifiers(person.identifiers?.identifier)
		patient.addressName = JanpixAssembler.fromAddresses(person.addresses?.address)[0]
		patient.addressNumber = JanpixAssembler.fromAddresses(person.addresses?.address)[1]
		patient.city = JanpixAssembler.fromCity(person.addresses?.address)
		patient.sex = JanpixAssembler.fromSex(person.administrativeSex)
		patient.birthdate = JanpixAssembler.fromExtendedDate(person.birthdate)
		patient.phone = JanpixAssembler.fromPhoneNumbers(person.phoneNumbers.phoneNumber)
		
		return patient
	}
	
	static String fromIdentifiers(List<IdentifierDTO> identifiers){
		if(!identifiers)
			return null
			
		// Se busca el DNI entre todos los identificadores
		IdentifierDTO dni = identifiers.find{it->it.type == DNI}
		
		return ( (dni) ? dni.number : "" )
	}
	
	static String[] fromAddresses(List<AddressDTO> addresses){
		ArrayList<String> arrayAddress = new ArrayList<String>()
		arrayAddress[0] = ""
		arrayAddress[1] = ""
		
		if(addresses) {
			// Se busca la direccion Legal y sino se obtiene la que se encuentre
			AddressDTO address = JanpixAssembler.getPrincipalAddress(addresses)
			arrayAddress[0] = address.street
			arrayAddress[1] = address.number
		}
		
		return arrayAddress
	}
	
	static City fromCity(List<AddressDTO> addresses) {
		def ctx = SCH.servletContext.getAttribute(GA.APPLICATION_CONTEXT)
		def placeService = ctx.placeService
		
		// Se obtiene la city del address
		AddressDTO address = JanpixAssembler.getPrincipalAddress(addresses)
		
		CityDTO city = address?.city
		if(!city?.nameCity || !city?.nameProvince )
			return null
			
		// Se debe buscar la city
		return placeService.findByPlace(city.nameCity,city.nameProvince)
	}
	
	static SexType fromSex(String sex){
		if(!sex)
			return null
		
		switch(sex){
			case MALE :
				return SexType.Masculino
				break;
			case FEMALE :
				return SexType.Femenino
				break;
				
			default:
				return null;
				break;
		}
	}
	
	static Date fromExtendedDate(ExtendedDateDTO extendedDate){
		if(!extendedDate)
			return null
			
		return Date.parse("yyyy-M-d",extendedDate.date)
	}
	
	static String fromPhoneNumbers(List<PhoneNumberDTO> phoneNumbers){
		if(!phoneNumbers)
			return null
			
		// Se busca el telefono de la casa (sino lo tiene se devuelve el primero agregado)
		PhoneNumberDTO phone = phoneNumbers.find{it->it.type == HOME}
		if(!phone)
			phone = phoneNumbers[0]
			
		return phone.number
		
	}
	
	/**
	 * Devuelve la direccion principal de una lista de direcciones
	 * @param addresses
	 * @return
	 */
	private static AddressDTO getPrincipalAddress(List<AddressDTO> addresses){
		if(!addresses)
			return null;
			
		// Se busca la direccion Legal y sino se obtiene la que se encuentre
		AddressDTO address = addresses.find{it->it.type == LEGAL}
		if(!address){
			address = addresses[0]
		}
		
		return address
	}
	
	
	
	/** Privados **/
	private static DataHandler byteArrayToDataHandler(byte[] byteArray,String mimeType){
		DataSource dataSource = new ByteArrayDataSource(byteArray, mimeType);
		return new DataHandler(dataSource);
	}
}
