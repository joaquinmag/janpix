package com.janpix.rup.pixmanager

import com.janpix.rup.empi.Person
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
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
	ResponseMessage patientRegistryRecordAdded(Person patientRequestMessage){
		try{
			if(!EMPIService.matchPerson(person)){
				EMPIService.createPatient(person)
			}else{
				//TODO agregar identificador
			}
		}
		catch(ExistingPatientException e){
			//TODO en vez de preguntar puedo capturar la exception
			//TODO agregar el identificador al paciente ya existente
		}
		catch(ShortDemographicDataException e){
			//TODO avisar
		}
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
