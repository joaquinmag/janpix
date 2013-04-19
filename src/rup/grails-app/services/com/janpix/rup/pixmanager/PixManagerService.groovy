package com.janpix.rup.pixmanager

import com.janpix.rup.empi.HealthEntity;
import com.janpix.rup.empi.MatchRecord
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
		def matchedPatients = EMPIService.getAllMatchedPatients(person, true)
		if (matchedPatients.empty) {
			try {
				EMPIService.createPatient(person)
				//TODO falta agregar id del health entity
				return new ResponseMessage()
			}
			catch(ShortDemographicDataException e) {
				log.debug("Excepcion ShortDemografic")
				return new ResponseMessage()
			}
		}
		def record = matchedPatients.find { it.matchLevel == MatchRecord.TYPE_LEVEL_HIGH }
		if (record) {
			try {
				EMPIService.addEntityIdentifierToPatient(record.person, healthEntity, organizationId)
				return new ResponseMessage()
			}
			catch (IdentifierException e) {
				log.debug(e.message)
				return new ResponseMessage()
			}
		}
		// Si llega hasta acá quedan pacientes con matcheo medio. Se debe retornar un response message con error.
		//TODO armar response message con error
		return new ResponseMessage()
	}

	/**
	 * Updates patient's information such as ids and demographic information.
	 */
	def updatePatient(){
		
	}
	
	/**
	 * Joins two patients that are saved in different records.
	 */
	def mergePatients(){
		//TODO ver si es necesario
	}
	
	/**
	 * Returns all patient ids.
	 */
	def getIdentifiersPatient(){
		
	}
	
}
