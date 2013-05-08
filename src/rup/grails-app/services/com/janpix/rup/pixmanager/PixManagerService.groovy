package com.janpix.rup.pixmanager

import com.janpix.rup.empi.AssigningAuthority;
import com.janpix.rup.empi.HealthEntity;
import com.janpix.rup.empi.Identifier
import com.janpix.rup.empi.MatchRecord
import com.janpix.rup.empi.Patient
import com.janpix.rup.empi.Person
import com.janpix.rup.empi.MatchRecord.LevelMatchRecord;
import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.DuplicateAuthorityIdentifierException
import com.janpix.rup.exceptions.identifier.IdentifierException
import com.janpix.rup.services.contracts.ACKMessage
import com.janpix.rup.services.contracts.ACKMessage.TypeCode;




/**
 * Service in charge of processing different Health Entities requests using the EMPIService to solve them.
 */
class PixManagerService {
	
	def EMPIService
	def assigningAuthorityService
	def i18nMessage
	
	/**
	 * Adds the patient from the healthentity to the eMPI
	 * If the patient already exists adds the identifier of the healthentity to the patient's ids collection.
	 * If the patient doesn't exists creates a new one and assigns the identifier from the healthentity to the patient's ids collection.
	 */
	ACKMessage patientRegistryRecordAdded(Person patientRequestMessage, HealthEntity healthEntity, String organizationId){
		try {
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
			log.debug("Exception ShortDemografic : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.ShortDemographicError,text:e.message)
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
	 * Updates patient's information such as ids and demographic information.
	 */
	ACKMessage patientRegistryRecordRevised(Patient patientRequestMessage,Person personRequestMessage, HealthEntity healthEntity){
		try{
			//Actualizo informacion demografica
			Patient updatedPatient = EMPIService.updateDemographicDataPatient(patientRequestMessage,personRequestMessage)
		
			//Actualizo ids de autoridad
			Identifier heIdentifier = personRequestMessage.identifiers.find {it.type == Identifier.TYPE_IDENTIFIER_PI && it.assigningAuthority == healthEntity}
			if(heIdentifier)
				try{
					EMPIService.addEntityIdentifierToPatient(patientRequestMessage,healthEntity,heIdentifier.number)
				}
				catch(DuplicateAuthorityIdentifierException e){
					EMPIService.updateEntityIdentifierToPatient(patientRequestMessage,healthEntity,heIdentifier.number)
				}
				
			return new ACKMessage(typeCode:TypeCode.SuccededUpdated,text:i18nMessage("pixmanager.ackmessage.updated.succeded"))
		}
		catch (DontExistingPatientException e) {
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.DontExistingPatientError, text: e.message)
		}
		catch (ExistingPatientException e) {
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.DuplicatePatientError, text: e.message)
		}
		catch(IdentifierException e){
			log.debug("Exception IdentifierException : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.IdentifierError,text:e.message)
		}
		catch (Exception e) {
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
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
	List<Identifier> patientRegistryGetIdentifiersQuery(String patientIdentifier,AssigningAuthority patientIdentifierDomain,List<AssigningAuthority> othersDomain=null){
		List<Identifier> identifiers = []
		 
		Person rupPatient = EMPIService.findPatientByHealthEntityId(patientIdentifier,patientIdentifierDomain)
		//Agrego los identificadores de los dominios pasados. Sino pasaron dominio agrego todos 
		if(othersDomain){
			rupPatient.identifiers.findAll{it.type == Identifier.TYPE_IDENTIFIER_PI}.each{Identifier it->
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
		
		return identifiers
	}

	/* # Metodos Extendidos */
	
	/**
	 * Crea un nuevo paciente sin validar matcheos
	 * @param patientRequestMessage
	 * @param healthEntity
	 * @param organizationId
	 * @return
	 */
	ACKMessage patientRegistryRecordAddedWithoutMatching(Person patientRequestMessage, HealthEntity healthEntity, String organizationId){
		try {
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
	
	
	List<Patient> getAllPossibleMatchedPatients(Person patientRequestMessage){
		List<Patient> matchedPatients = []
		List<MatchRecord> records = EMPIService.getAllMatchedPatients(patientRequestMessage, true)
		
		records.each { MatchRecord it->
			matchedPatients.add( (Patient)it.person ) //Puedo castear porque ya se que tiene una persona
		}
		
		return matchedPatients
	}

}
