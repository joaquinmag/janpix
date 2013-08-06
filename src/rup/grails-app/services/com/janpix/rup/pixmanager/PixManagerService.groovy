package com.janpix.rup.pixmanager

import com.janpix.rup.empi.AssigningAuthority
import com.janpix.rup.empi.Identifier
import com.janpix.rup.empi.MatchRecord
import com.janpix.rup.empi.Patient
import com.janpix.rup.empi.Person
import com.janpix.rup.empi.MatchRecord.LevelMatchRecord
import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.DuplicateAuthorityIdentifierException
import com.janpix.rup.exceptions.identifier.IdentifierException
import com.janpix.rup.infrastructure.FactoryDTO
import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO
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
				
				def matchedPatients = EMPIService.getAllMatchedPatients(patientRequestMessage, true)
				//Es un paciente nuevo
				if (matchedPatients.empty) {
					def patient = EMPIService.createPatient(patientRequestMessage)
					EMPIService.addEntityIdentifierToPatient(patient, healthEntity, organizationId)
					return new ACKMessage(typeCode: TypeCode.SuccededCreation, text:i18nMessage("pixmanager.ackmessage.creation.succeded"))	
				}
				
				//El paciente tiene un alto matcheo
				MatchRecord record = matchedPatients.find { it.matchLevel == MatchRecord.LevelMatchRecord.High }
				if (record) {
					EMPIService.addEntityIdentifierToPatient(record.person, healthEntity, organizationId)
					return new ACKMessage(typeCode: TypeCode.SuccededInsertion, text: i18nMessage("pixmanager.ackmessage.insertion.succeded"))
				}
				
				// Si llega hasta acá quedan pacientes con matcheo medio. Se debe retornar un response message con error.
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

				return new ACKMessage(typeCode:TypeCode.SuccededUpdated,text:i18nMessage("pixmanager.ackmessage.updated.succeded"))
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
	ACKMessage patientRegistryGetIdentifiersQuery(String patientIdentifier,AssigningAuthority patientIdentifierDomain,List<AssigningAuthority> othersDomain=null){

		try{

			Set<Identifier> identifiers = []
			
			def assigningAuth = AssigningAuthority.findByOid(patientIdentifierDomain.oid)
			//AssigningAuthorityDTO patientIdentifierDomain = patientIdentifierDomainDTO.convert(mapperDtoDomain)
			
			Patient rupPatient = EMPIService.findPatientByHealthEntityId(patientIdentifier,assigningAuth)
			//FIXME!! Verificar que rupPatient sea diferente de null

			//Agrego los identificadores de los dominios pasados. Sino pasaron dominio agrego todos 
			if(othersDomain){
				rupPatient.identifiers.findAll{it.type == Identifier.TYPE_IDENTIFIER_PI}.each{Identifier it->
					//IdentifierDTO identifierDTO = it.convert(mapperDomainDto)
					if(othersDomain.contains(it.assigningAuthority)){
						identifiers.add(it)
					}
				}
			}else{
				identifiers.addAll(rupPatient.identifiers.findAll{it.type == Identifier.TYPE_IDENTIFIER_PI && it.assigningAuthority != patientIdentifierDomain})
			}
			//Si me pidieron el dominio RUP agrego el CUIS como un identificador mas
			if( !othersDomain || othersDomain.contains(assigningAuthorityService.rupAuthority())){
				Identifier identifier = new Identifier()
				if(rupPatient){
					identifier.type 				= Identifier.TYPE_IDENTIFIER_PI
					identifier.number				= "${rupPatient.uniqueId}"
					identifier.assigningAuthority	= assigningAuthorityService.rupAuthority()
				}
										
				identifiers.add(identifier)
			}
			//FIXME!!! Utilizar un mapperDomainDto
			Patient patientDTO = FactoryDTO.buildPatientDTO(rupPatient) 
			patientDTO.identifiers = identifiers;
			
			return new ACKMessage(typeCode:TypeCode.SuccededQuery,patient:patientDTO)
			
		}catch (Exception e) {
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
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
		try {
			//Transformo DTO a dominio
			Person patientRequestMessage = personDTO.convert(mapperDtoDomain);
			AssigningAuthority healthEntity = healthEntityDTO.convert(mapperDtoDomain)
			
			def patient = EMPIService.createPatient(patientRequestMessage)
			EMPIService.addEntityIdentifierToPatient(patient, healthEntity, organizationId)
			return new ACKMessage(typeCode: TypeCode.SuccededCreation, text:i18nMessage("pixmanager.ackmessage.creation.succeded"))
		}
		catch(ShortDemographicDataException e) {
			log.debug("Exception ShortDemografic : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.ShortDemographicError,text:e.message)
		}
		catch (DontExistingPatientException e) {
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.DontExistingPatientError, text: e.message)
		}
		catch (IdentifierException e) {
			log.debug("Exception IdentifierException : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.IdentifierError,text:e.message)
		}
		catch (Exception e) {
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
		}
	}
	
	/**
	 * Devuelve los posibles matcheos de la persona enviada
	 * @param patientRequestMessage
	 * @return
	 * FIXME!! Usar DTOs
	 */
	List<Patient> getAllPossibleMatchedPatients(Person patientRequestMessage){
		//Person patientRequestMessage = personDTO.convert(mapperDtoDomain)
		List<Patient> matchedPatients = []
		List<MatchRecord> records = EMPIService.getAllMatchedPatients(patientRequestMessage, true)
		
		records.each { MatchRecord it->
			matchedPatients.add( (Patient)it.person ) //Puedo castear porque ya se que tiene una persona
			//matchedPatients.add( ((Patient)it.person).convert(mapperDomainDto) )
		}
		
		return matchedPatients
	}

}
