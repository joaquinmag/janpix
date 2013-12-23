package com.janpix.rup.pixmanager

import com.janpix.rup.empi.AssigningAuthority
import com.janpix.rup.empi.Identifier
import com.janpix.rup.empi.MatchRecord
import com.janpix.rup.empi.Patient
import com.janpix.rup.empi.PatientIdentifier
import com.janpix.rup.empi.Person
import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.DuplicateAuthorityIdentifierException
import com.janpix.rup.exceptions.identifier.IdentifierException
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
import com.janpix.rup.infrastructure.dto.IdentifierDTO
import com.janpix.rup.infrastructure.dto.PatientDTO
import com.janpix.rup.infrastructure.dto.PersonDTO
import com.janpix.rup.services.contracts.ACKMessage
import com.janpix.rup.services.contracts.ACKMessage.TypeCode





/**
 * Service in charge of processing different Health Entities requests using the EMPIService to solve them.
 */
class PixManagerService {
	def EMPIService
	def assigningAuthorityService
	def i18nMessage
	def mapperDtoDomain
	def mapperDomainDto
	static transactional = false
	
	/**
	 * Adds the patient from the healthentity to the eMPI
	 * If the patient already exists adds the identifier of the healthentity to the patient's ids collection.
	 * If the patient doesn't exists creates a new one and assigns the identifier from the healthentity to the patient's ids collection.
	 */
	ACKMessage patientRegistryRecordAdded(PersonDTO patientDTO, AssigningAuthorityDTO healthEntityDTO, String organizationId){
		Patient.withTransaction { tx ->
			try {				
				//Transformo DTO a dominio
				Person patientRequestMessage = patientDTO.convert(mapperDtoDomain);
				AssigningAuthority healthEntity = healthEntityDTO.convert(mapperDtoDomain)
				
				log.info("Obteniendo matcheos del paciente "+patientRequestMessage+"...")
				def matchedPatients = EMPIService.getAllMatchedPatients(patientRequestMessage, true)
				//Es un paciente nuevo
				if (matchedPatients.empty) {
					log.info("Nuevo paciente")
					log.info("Agregando paciente...")
					def patient = EMPIService.createPatient(patientRequestMessage)
					EMPIService.addEntityIdentifierToPatient(patient, healthEntity, organizationId)
					log.info("Paciente agregado correctamente")
					
					def createdPatientDTO = patient.convert(mapperDomainDto)
					return new ACKMessage(patient:createdPatientDTO,typeCode: TypeCode.SuccededCreation, text:i18nMessage("pixmanager.ackmessage.creation.succeded"))	
				}
				
				//El paciente tiene un alto matcheo
				MatchRecord record = matchedPatients.find { it.matchLevel == MatchRecord.LevelMatchRecord.High }
				if (record) {
					log.info("El paciente tiene un alto matcheo con otro paciente ya agregado")
					log.info("Agregando identificador al paciente ya registrado...")
					EMPIService.addEntityIdentifierToPatient(record.person, healthEntity, organizationId)
					log.info("Identificador agregado correctamente")
					return new ACKMessage(typeCode: TypeCode.SuccededInsertion, text: i18nMessage("pixmanager.ackmessage.insertion.succeded"))
				}
				
				// Si llega hasta acá quedan pacientes con matcheo medio. Se debe retornar un response message con error.
				log.info("El paciente que se intenta ingresar tiene un nivel medio de matcheo con uno o mas pacientes ya existentes")
				return new ACKMessage(typeCode:TypeCode.PossibleMatchingPatientsError,text:i18nMessage("pixmanager.ackmessage.possiblematching.error"))
			
			}
			catch(ShortDemographicDataException e) {
				tx.setRollbackOnly()
				log.debug("Exception ShortDemografic : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.ShortDemographicError,text:e.message)
			}
			catch (IdentifierException e) {
				tx.setRollbackOnly()
				log.debug("Exception IdentifierException : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.IdentifierError,text:e.message)
			}
			catch (Exception e) {
				tx.setRollbackOnly()
				log.error("Exception : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
			}
		}
	}

	/**
	 * Updates patient's information such as ids and demographic information.
	 */
	ACKMessage patientRegistryRecordRevised(PatientDTO patientDTO,AssigningAuthorityDTO healthEntityDTO){
		Patient.withTransaction { tx ->
			try{
				PersonDTO personDTO = patientDTO.toPersonDTO()
				//Transformo DTO a dominio
				Patient patientRequestMessage = patientDTO.convert(mapperDtoDomain);
				Person personRequestMessage = personDTO.convert(mapperDtoDomain)
				AssigningAuthority healthEntity = healthEntityDTO.convert(mapperDtoDomain)
				
				//Actualizo informacion demografica
				Patient updatedPatient = EMPIService.updateDemographicDataPatient(patientRequestMessage,personRequestMessage)
			
				//Actualizo ids de autoridad
				Identifier heIdentifier = personRequestMessage.identifiers.find {it.type == Identifier.TYPE_IDENTIFIER_PI && it.assigningAuthority == healthEntity}
				if(heIdentifier){
					//Si no posee un identificador para esa ENtidad agrega, sino actualiza
					if(patientRequestMessage.identifiers.find{it.assigningAuthority == healthEntity}==null)
						EMPIService.addEntityIdentifierToPatient(patientRequestMessage,healthEntity,heIdentifier.number)
					else
						EMPIService.updateEntityIdentifierToPatient(patientRequestMessage,healthEntity,heIdentifier.number)
				}
				
				Patient rupPatient = EMPIService.findPatientByUUID(new PatientIdentifier(mainId: patientDTO.uniqueId))
				def patientDto = rupPatient.convert(mapperDomainDto)
				return new ACKMessage(typeCode:TypeCode.SuccededUpdated,text:i18nMessage("pixmanager.ackmessage.updated.succeded"), patient: patientDto)
			}
			catch (DontExistingPatientException e) {
				tx.setRollbackOnly()
				log.error("Exception : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.DontExistingPatientError, text: e.message)
			}
			catch (ExistingPatientException e) {
				tx.setRollbackOnly()
				log.error("Exception : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.DuplicatePatientError, text: e.message)
			}
			catch(IdentifierException e){
				tx.setRollbackOnly()
				log.debug("Exception IdentifierException : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.IdentifierError,text:e.message)
			}
			catch(DuplicateAuthorityIdentifierException e){
				tx.setRollbackOnly()
				log.debug("DuplicateAuthorityIdentifierException : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.IdentifierError,text:e.message)
			}
			catch (Exception e) {
				tx.setRollbackOnly()
				log.error("Exception : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
			}
		}
	}
	
	/**
	 * Busca una lista de identificadores en 'othersDomains' que correspondan con 'patientIdentifier' para 'patientIdentifierDomain'
	 * @protocol ITI_TF2b - 3.45 PIXV3 Query 
	 * @param HealthEntity patientIdentifierDomain: dominio del identificador del paciente
	 * @param String patientIdentifier: El identificador del paciente en el dominio de la entidad sanitaria pasada
	 * @param List<AssigningAuthority> othersDomains: dominios de los cuales se requiere identificador. Sino se pasa busca en todos los dominios
	 * @return List<Identifier> identificadores del paciente en los dominios solicitados
	 * @return List<Identifier> empty si no hay ningun identificador
	 */
	ACKMessage patientRegistryGetIdentifiersQuery(String patientIdentifier,AssigningAuthorityDTO assigningAuthorityDTO,List<AssigningAuthorityDTO> othersDomain=null)
	{
		// Se coloca mediante withTransaction porque sino lanza error de lazy-initialization
		Patient.withTransaction { tx -> 
		try{
			Set<IdentifierDTO> identifiers = []
			
			AssigningAuthority patientIdentifierDomain = assigningAuthorityDTO.convert(mapperDtoDomain)
			
			Patient rupPatient = EMPIService.findPatientByHealthEntityId(patientIdentifier,patientIdentifierDomain)
			if(rupPatient == null)
				return new ACKMessage(typeCode:TypeCode.DontExistingPatientError)

			//Agrego los identificadores de los dominios pasados. Sino pasaron dominio agrego todos 
			if(othersDomain){
				rupPatient.identifiers.findAll{ it.type == Identifier.TYPE_IDENTIFIER_PI }.each{ Identifier it ->
					IdentifierDTO identifierDTO = it.convert(mapperDomainDto)
					//AssigningAuthorityDTO aaDTO = it.assigningAuthority?.convert(mapperDomainDto) 
					if(othersDomain.contains(identifierDTO.assigningAuthority)){
						identifiers.add(identifierDTO)
					}
				}
			}else{
				Set<Identifier> identifiersPatient = rupPatient.identifiers.findAll{it.type == Identifier.TYPE_IDENTIFIER_PI && it.assigningAuthority != patientIdentifierDomain}
				identifiersPatient.each {Identifier it->
					identifiers.add( it.convert(mapperDomainDto))
				} 
			}
			
			//Si me pidieron el dominio RUP agrego el CUIS como un identificador mas
			AssigningAuthorityDTO rupDTO = assigningAuthorityService.rupAuthority().convert(mapperDomainDto)
			if( !othersDomain || othersDomain.contains(rupDTO)){
				IdentifierDTO rupIdentifierDTO 		= new IdentifierDTO()
				rupIdentifierDTO.type 				= Identifier.TYPE_IDENTIFIER_PI
				rupIdentifierDTO.number				= "${rupPatient.uniqueId}"
				rupIdentifierDTO.assigningAuthority	= rupDTO
										
				identifiers.add(rupIdentifierDTO)
			}
			PatientDTO patientDTO = rupPatient.convert(mapperDomainDto)
			patientDTO.identifiers = identifiers //Modifico los identifiers por los obtenidos 

			return new ACKMessage(typeCode:TypeCode.SuccededQuery,patient:patientDTO)
			
		}catch (Exception e) {
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
		}
		}
	}

	/* # Metodos Extendidos */
	
	/**
	 * Crea un nuevo paciente sin validar matcheos
	 * @param patientRequestMessage
	 * @param healthEntity
	 * @param organizationId
	 * @return
	 */
	ACKMessage patientRegistryRecordAddedWithoutMatching(PersonDTO personDTO, AssigningAuthorityDTO healthEntityDTO, String organizationId){
		Patient.withTransaction { tx ->
			try {
				//Transformo DTO a dominio
				Person patientRequestMessage = personDTO.convert(mapperDtoDomain);
				AssigningAuthority healthEntity = healthEntityDTO.convert(mapperDtoDomain)
				
				def patient = EMPIService.createPatient(patientRequestMessage)
				EMPIService.addEntityIdentifierToPatient(patient, healthEntity, organizationId)
				return new ACKMessage(typeCode: TypeCode.SuccededCreation, text:i18nMessage("pixmanager.ackmessage.creation.succeded"))
			}
			catch(ShortDemographicDataException e) {
				tx.setRollbackOnly()
				log.debug("Exception ShortDemografic : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.ShortDemographicError,text:e.message)
			}
			catch (DontExistingPatientException e) {
				tx.setRollbackOnly()
				log.error("Exception : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.DontExistingPatientError, text: e.message)
			}
			catch (IdentifierException e) {
				tx.setRollbackOnly()
				log.debug("Exception IdentifierException : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.IdentifierError,text:e.message)
			}
			catch (Exception e) {
				tx.setRollbackOnly()
				log.error("Exception : ${e.message}", e)
				return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
			}
		}
	}
	
	/**
	 * Devuelve los posibles matcheos de la persona enviada
	 * @param patientRequestMessage
	 * @return
	 */
	List<PatientDTO> getAllPossibleMatchedPatients(PersonDTO personDTO)
	{
		Patient.withTransaction { tx ->
			List<PatientDTO> matchedPatients = []
			try{
				// Se convierto a clase de dominio
				Person patientRequestMessage = personDTO.convert(mapperDtoDomain)
		
				List<MatchRecord> records = EMPIService.getAllMatchedPatients(patientRequestMessage, true)
				
				records.each { MatchRecord it->
					// Se caste porque se sabe que tiene una persona
					PatientDTO patientDTO = it.person.convert(mapperDomainDto) as PatientDTO 
					matchedPatients.add( patientDTO )
				}
	
			}
			catch (Exception e) {
				tx.setRollbackOnly()
				log.error("Exception : ${e.message}", e)
			}
			finally{
				return matchedPatients
			}
		}
	}

}
