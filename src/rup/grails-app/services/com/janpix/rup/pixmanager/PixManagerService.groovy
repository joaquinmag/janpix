package com.janpix.rup.pixmanager

import com.janpix.rup.empi.AssigningAuthority;
import com.janpix.rup.empi.HealthEntity;
import com.janpix.rup.empi.Identifier
import com.janpix.rup.empi.MatchRecord
import com.janpix.rup.empi.Patient
import com.janpix.rup.empi.Person
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.IdentifierException
import com.janpix.rup.services.contracts.ResponseMessage


/**
 * Service in charge of processing different Health Entities requests using the EMPIService to solve them.
 */
class PixManagerService {
	
	def EMPIService
	
	/**
	 * Adds the patient from the healthentity to the eMPI
	 * If the patient already exists adds the identifier of the healthentity to the patient's ids collection.
	 * If the patient doesn't exists creates a new one and assigns the identifier from the healthentity to the patient's ids collection.
	 */
	ResponseMessage patientRegistryRecordAdded(Person patientRequestMessage, HealthEntity healthEntity, String organizationId){
		try {
			def matchedPatients = EMPIService.getAllMatchedPatients(patientRequestMessage, true)
			//Es un paciente nuevo
			if (matchedPatients.empty) {	
				def patient = EMPIService.createPatient(patientRequestMessage)
				EMPIService.addEntityIdentifierToPatient(patient, healthEntity, organizationId)
				//TODO armar response message con ok
				return new ResponseMessage()
			}
			
			//El paciente tiene un alto matcheo
			MatchRecord record = matchedPatients.find { it.matchLevel == MatchRecord.TYPE_LEVEL_HIGH }
			if (record) {
				EMPIService.addEntityIdentifierToPatient(record.person, healthEntity, organizationId)
				//TODO armar response message con message ok
				return new ResponseMessage()
			}
			
			// Si llega hasta acá quedan pacientes con matcheo medio. Se debe retornar un response message con error.
			//TODO armar response message con error
			return new ResponseMessage()
		
		}
		catch(ShortDemographicDataException e) {
			log.debug("Excepcion ShortDemografic")
			return new ResponseMessage()
		}
		catch (IdentifierException e) {
			log.debug(e.message)
			return new ResponseMessage()
		}

	}

	/**
	 * Updates patient's information such as ids and demographic information.
	 */
	ResponseMessage patientRegistryRecordRevised(Patient patientRequestMessage,Person personRequestMessage, HealthEntity healthEntity){
		
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
		if( !othersDomain || othersDomain.contains(AssigningAuthority.rupAuthority())){
			Identifier identifier = new Identifier()
			if(rupPatient){
				identifier.type 				= Identifier.TYPE_IDENTIFIER_PI
				identifier.number				= "${rupPatient.uniqueId}"
				identifier.assigningAuthority	= AssigningAuthority.rupAuthority()
			}
									
			identifiers.add(identifier)
		}
		
		return identifiers
	}

	
	

}
