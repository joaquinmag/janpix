package com.janpix.healthentity

import grails.transaction.Transactional
import ar.com.healthentity.ClinicalDocument
import ar.com.healthentity.Patient
import ar.com.healthentity.janpix.JanpixAssembler

import com.janpix.exceptions.JanpixConnectionException
import com.janpix.exceptions.JanpixException
import com.janpix.exceptions.JanpixPossibleMatchingPatientException
import com.janpix.exceptions.JanpixDuplicatePatientException

import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.message.ACKMessage
import com.janpix.servidordocumentos.dto.message.RetrieveDocumentRequest

import com.janpix.rup.services.contracts.AddPatientRequestMessage
import com.janpix.rup.services.contracts.ACKMessage
import com.janpix.rup.services.contracts.GetAllPossibleMatchedPatientsRequestMessage;

@Transactional
class JanpixService {
	
	def janpixRepodocServiceClient
	def janpixPixManagerServiceClient
	def grailsApplication
	
	/**
	 * Agrega un nuevo paciente en el RUP
	 * @param patient
	 */
	void addNewPatient(Patient patient){
		ACKMessage ack = null
		try{
			AddPatientRequestMessage requestMessage = new AddPatientRequestMessage()
			requestMessage.person = JanpixAssembler.toPerson(patient)
			requestMessage.healthEntity = JanpixAssembler.toAssigningAuthority(grailsApplication.config.healthEntity)
			requestMessage.organizationId = patient.id
			
			log.info("Agregando paciente ["+patient+"] al Registro Unico de Pacientes")
			ack = janpixPixManagerServiceClient.addNewPatient(requestMessage)
		}
		catch(Exception ex){
			String message ="Error de conexión contra el RUP: "+ex.message
			log.error(message)
			throw new JanpixConnectionException(message);
		}
		
		this.validateACKMessageRUP(ack)
	}
	
	/**
	 * Agrega un nuevo paciente en el RUP sin validar posibles matcheos
	 * @param patient
	 */
	void addNewPatientWithoutValidation(Patient patient){
		
	}
	
	/**
	 * Retorna todos los pacientes que matchean con el paciente pasado por parametro
	 * @param patient
	 * @return
	 */
	List<Patient> getAllPossibleMatchedPatients(Patient patient){
		GetAllPossibleMatchedPatientsRequestMessage requestMessage = new GetAllPossibleMatchedPatientsRequestMessage()
		//TODO hacer
	}
	
	/**
	 * Retorna el documento del Repositorio de Documentos
	 * que contiene el uuid pasado
	 */
	ClinicalDocument getDocumentByUUID(String uuid){
		try{
			RetrieveDocumentRequest requestMessage = new RetrieveDocumentRequest(uuid:uuid)
			
			log.info("Consultando Repositorio de documentos por el uuid:"+uuid)
			ACKMessage ack =  janpixRepodocServiceClient.retrieveDocument(requestMessage)
			if(ack.typeCode != ACKMessage.TypeCode.SuccededRetrieve){
				log.error("Error al obtener el documento. Error:"+ack.typeCode.toString()+". Mensaje:"+ack.text)
				return null;
			}
			
			log.info("Documento obtenido correctamente")
			ClinicalDocument clinicalDocument = JanpixAssembler.fromDocument(ack.clinicalDocument)
			return clinicalDocument;
		}
		catch(Exception ex){
			String message ="Error de conexión contra el repositorio: "+ex.message 
			log.error(message)
			throw new JanpixException(message);
		}
	}
	
	/**
	 * Sube un documento clinico asociado a un paciente
	 * @param clinicalDocument
	 * @return
	 */
    Boolean UploadDocument(ClinicalDocumentDTO clinicalDocument){
    
	}
	
	/**
	 * Valida los posibles valores que vienen en un ACK del RUP
	 * @param ack
	 */
	private void validateACKMessageRUP(ACKMessage ack){
		if(ack.typeCode == ACKMessage.TypeCode.SuccededCreation){
			log.info("Paciente agregado correctamente")
			return
		}
		
		log.error("Error al agregar al paciente. Error:"+ack.typeCode.toString()+". Mensaje:"+ack.text)
		switch(ack.typeCode){
			case ACKMessage.TypeCode.PossibleMatchingPatientsError: 
				throw new JanpixPossibleMatchingPatientException("Existen pacientes que se asemejan al paciente que intenta agregar pero no se puede determinar con precisión");
				break;
				
			case ACKMessage.TypeCode.DuplicatePatientError:
				throw new JanpixPossibleMatchingPatientException("Existen pacientes que se asemejan al paciente que intenta agregar pero no se puede determinar con precisión");
				break;
			
			case ACKMessage.TypeCode.IdentifierError:
				throw new JanpixDuplicatePatientException("El paciente ya se encuentra ingresado");
				break;
				
			default :
				throw new JanpixException(ack.text);
				break;
		}
	}
}
