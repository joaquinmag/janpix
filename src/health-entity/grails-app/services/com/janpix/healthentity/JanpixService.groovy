package com.janpix.healthentity

import java.io.File
import java.io.FileOutputStream
import grails.transaction.Transactional
import ar.com.healthentity.ClinicalDocument
import ar.com.healthentity.Patient
import ar.com.healthentity.Study
import ar.com.healthentity.User
import ar.com.healthentity.janpix.utils.JanpixAssembler

import com.janpix.exceptions.ErrorUploadingDocumentException
import com.janpix.exceptions.JanpixConnectionException
import com.janpix.exceptions.JanpixDuplicatePatientException
import com.janpix.exceptions.JanpixException
import com.janpix.exceptions.JanpixPossibleMatchingPatientException
import com.janpix.exceptions.PatientDoesNotExistsException
import com.janpix.webclient.repodoc.ProvideAndRegisterDocumentRequest
import com.janpix.webclient.repodoc.RetrieveDocumentRequest
import com.janpix.webclient.rup.AckMessage
import com.janpix.webclient.rup.AckQueryPatientMessage
import com.janpix.webclient.rup.AddPatientRequestMessage
import com.janpix.webclient.rup.AssigningAuthorityDTO
import com.janpix.webclient.rup.GetAllPossibleMatchedPatientsRequestMessage
import com.janpix.webclient.rup.GetIdentifiersRequestMessage
import com.janpix.webclient.rup.PatientDTO
import com.janpix.webclient.rup.TypeCode
import com.janpix.webclient.regdoc.AckStoredQueryMessage;
import com.janpix.webclient.regdoc.QueryDocumentRequest;
import com.janpix.webclient.repodoc.AckMessage as AckRepoDoc;
import com.janpix.webclient.repodoc.TypeCode as TypeCodeRepoDoc;



@Transactional
class JanpixService {
	
	def janpixRepodocServiceClient
	def janpixPixManagerServiceClient
	def janpixRegdocServiceClient
	def grailsApplication
	
	/**
	 * Agrega un nuevo paciente en el RUP
	 * @param patient
	 */
	void addNewPatient(Patient patient){
		AckMessage ack = null
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
		AckMessage ack = null
		try{
			AddPatientRequestMessage requestMessage = new AddPatientRequestMessage()
			requestMessage.person = JanpixAssembler.toPerson(patient)
			requestMessage.healthEntity = JanpixAssembler.toAssigningAuthority(grailsApplication.config.healthEntity)
			requestMessage.organizationId = patient.id
			
			log.info("Forzando el registro del paciente ["+patient+"] al Registro Unico de Pacientes")
			ack = janpixPixManagerServiceClient.addNewPatientWithoutValidation(requestMessage)
		}
		catch(Exception ex){
			String message ="Error de conexión contra el RUP: "+ex.message
			log.error(message)
			throw new JanpixConnectionException(message);
		}
		
		this.validateACKMessageRUP(ack)
	}
	
	/**
	 * Retorna todos los pacientes que matchean con el paciente pasado por parametro
	 * @param patient
	 * @return
	 */
	List<Patient> getAllPossibleMatchedPatients(Patient patient){
		AckQueryPatientMessage ack = null
		try{
			// Se genera el mensaje
			GetAllPossibleMatchedPatientsRequestMessage requestMessage = new GetAllPossibleMatchedPatientsRequestMessage()
			requestMessage.person = JanpixAssembler.toPerson(patient)
			
			// Se realiza la consulta
			log.info("Consultando por posibles matcheos del paciente "+patient)
			ack = janpixPixManagerServiceClient.getAllPossibleMatchedPatients(requestMessage)
						
		}
		catch(Exception ex){
			String message ="Error de conexión contra el RUP: "+ex.message
			log.error(message)
			throw new JanpixConnectionException(message);
		}
		
		// Se valida el ACK
		this.validateACKQueryMessageRUP(ack)
		
		// Se genera la respuesta
		List<PatientDTO> matchedPatients = ack.patients.patient
		log.info("Se obtuvieron "+matchedPatients.size()+" matcheos")
		
		List<Patient> patients = new ArrayList<Patient>();
		matchedPatients.each { PatientDTO it->
			patients.add(JanpixAssembler.fromPatient(it))
		}

		return patients
	}
	
	/**
	 * Retorna el documento del Repositorio de Documentos
	 * que contiene el uuid pasado
	 */
	ClinicalDocument getDocumentByUniqueId(String uniqueId, File destinationFile) {
		AckRepoDoc ack
		try{
			RetrieveDocumentRequest requestMessage = new RetrieveDocumentRequest(uuid:uniqueId)
			
			log.info("Consultando Repositorio de documentos por el uuid:"+uniqueId)
			ack =  janpixRepodocServiceClient.retrieveDocument(requestMessage)
		}
		catch(Exception ex){
			String message ="Error de conexión contra el repositorio: "+ex.message 
			log.error(message)
			throw new JanpixException(message);
		}
		
		if(ack.typeCode != TypeCodeRepoDoc.SUCCEDED_RETRIEVE){
			log.error("Error al obtener el documento. Error:"+ack.typeCode.toString()+". Mensaje:"+ack.text)
			return null;
		}
		log.info("Documento obtenido correctamente")
		
		log.info("Conviertiendo documento")
		ClinicalDocument clinicalDocument = JanpixAssembler.fromDocument(ack.clinicalDocument)
		log.info("Escribiendo en destinationFile=${destinationFile}")
		ack.clinicalDocument.binaryData.writeTo(new FileOutputStream(destinationFile))
		log.info("Archivo creado correctamente")
		log.info("Retornando ClinicalDocument")
		return clinicalDocument;
	}
	
	/**
	 * Sube un documento clinico asociado a un paciente
	 * @param clinicalDocument
	 * @return
	 */
    void uploadDocument(Study study,User currentUser){
		com.janpix.webclient.repodoc.AckMessage ack
    	try {
			// Se obtiene el CUIS del paciente
			String cuis = this.getPatientCUIS(study.patient)
			
			log.info("Subiendo estudio "+study+" en el Repositorio de Documentos")
			
			log.info("Armando Request")
			def msg = new ProvideAndRegisterDocumentRequest()
			msg.clinicalDocument = JanpixAssembler.toClinicalDocument(study)
			msg.clinicalDocument.author = JanpixAssembler.toAuthor(currentUser,grailsApplication.config.healthEntity)
			msg.clinicalDocument.patientId = cuis
			
			log.info("Enviando request al WS")
			ack = janpixRepodocServiceClient.provideAndRegisterDocument(msg)
			
    	}
		catch(Exception ex) {
			log.error("Excepcion subiendo documento al repo de docs", ex)
			throw new ErrorUploadingDocumentException(ex)
		}
			
		if (ack.typeCode != com.janpix.webclient.repodoc.TypeCode.SUCCEDED_INSERTION) {
			log.error("Error al insertar el documento. Error: ${ack.typeCode}. Mensaje: ${ack.text}")
			throw new ErrorUploadingDocumentException(ack.typeCode, ack.text)
		}
		log.info("Documento subido correctamente")
	}
	
	/**
	 * Retorna todos los documentos asociados a un paciente que puede contener
	 * otra entidad sanitaria
	 * @param patient
	 * @return
	 */
	List<Study> queryAllStudies(Patient patient){
		List<Study> studies = []
		AckStoredQueryMessage ack
		
		// Se obtiene el CUIS del paciente
		String cuis = this.getPatientCUIS(patient)
		
		try {
			log.info("Consultando por todos los estudios del paciente "+patient)
			
			log.info("Armando Request")
			QueryDocumentRequest request = new QueryDocumentRequest()
			request.healthEntityFinder = JanpixAssembler.toHealthEntityRegistro(grailsApplication.config.healthEntity)
			request.patientId = cuis
			
			log.info("Enviando request al WS")
			ack = janpixRegdocServiceClient.queryDocument(request)
			
			log.info("Se recibieron "+ack.clinicalDocuments.clinicalDocument.size()+" estudios")
			// Transformo todos los estudios
			ack.clinicalDocuments.clinicalDocument.each {com.janpix.webclient.regdoc.ClinicalDocumentDTO document->
				log.info("Filename: ${document.fileAttributes.filename}")
				def study = JanpixAssembler.fromRegisterDocument(document)
				log.info("Parsed filename: ${study.document.filename}")
				studies.add(study)
			}
		}
		catch(Exception ex){
			String message ="Error de conexión contra el Registro de Documentos: "+ex.message
			log.error(message, ex)
			throw new JanpixConnectionException(message);
		}
		
		return studies
	}
	
	/**
	 * Retorna todos los documentos que cumplen con el filtro inclusivo
	 * de busqueda
	 * @param filter
	 * @return
	 */
	/*List<Study> queryStudyByFilter(FilterStudy filter){
		// TODO implementar!!!
	}*/
	
	
	/**
	 * Retorna el CUIS del paciente si este se encuentra registrado en el RUP
	 * Sino esta registrado devuelve null
	 * @param patient
	 * @return
	 */
	String getPatientCUIS(Patient patient){
		try {
			log.info("Armando Request para consulta CUIS del paciente")
			GetIdentifiersRequestMessage requestMessage = new GetIdentifiersRequestMessage()
			requestMessage.patientIdentifier = patient.id
			requestMessage.assigningAuthority = JanpixAssembler.toAssigningAuthority(grailsApplication.config.healthEntity)
			requestMessage.othersDomain = new GetIdentifiersRequestMessage.OthersDomain()
			requestMessage.othersDomain.domain.add(this.getRUP())
			
			log.info("Consultando por el CUIS del paciente "+patient)
			AckMessage ack = janpixPixManagerServiceClient.getIdentifiersPatient(requestMessage)

			// Como solo pedi el identificador del RUP devuelvo ese
			if (ack.typeCode != TypeCode.SUCCEDED_QUERY) {
				log.info("No existe CUIS para el paciente "+patient)
				throw new PatientDoesNotExistsException("El paciente al cual intenta agregarle un estudio no se encuentra registrado en el RUP")
			}
			
			log.info("Respuesta satisfactoria. Se retornaron "+ack.patient?.identifiers?.identifier.size()+" identificadores")
			if(ack.patient?.identifiers?.identifier.size() == 1){
				return ack.patient.identifiers.identifier[0].number
			}
		}
		catch(Exception ex) {
			log.error("Excepcion obteniendo CUIS del paciente", ex)
			throw new JanpixConnectionException("Error al conectarse contra el RUP")
		}
	}
	
	/*** Metodos Privados ***/
	
	private AssigningAuthorityDTO getRUP(){
		return JanpixAssembler.toAssigningAuthority(grailsApplication.config.rup)
	}
	/**
	 * Valida los posibles valores que vienen en un ACK del RUP
	 * @param ack
	 */
	private void validateACKMessageRUP(AckMessage ack){
		if(ack.typeCode == TypeCode.SUCCEDED_CREATION){
			log.info("Paciente agregado correctamente")
			return
		}
		
		log.error("Error al agregar al paciente. Error:"+ack.typeCode.toString()+". Mensaje:"+ack.text)
		switch(ack.typeCode){
			case TypeCode.POSSIBLE_MATCHING_PATIENTS_ERROR: 
				throw new JanpixPossibleMatchingPatientException("Existen pacientes que se asemejan al paciente que intenta agregar pero no se puede determinar con precisión");
				break;
				
			case TypeCode.DUPLICATE_PATIENT_ERROR:
				throw new JanpixPossibleMatchingPatientException("Existen pacientes que se asemejan al paciente que intenta agregar pero no se puede determinar con precisión");
				break;
			
			case TypeCode.IDENTIFIER_ERROR:
				throw new JanpixDuplicatePatientException("Existe otro paciente con el mismo ID registrado por usted");
				break;
				
			default :
				throw new JanpixException(ack.text);
				break;
		}
	}
	
	/**
	 * Valida los posibles valores que vienen en un ACK del RUP
	 * @param ack
	 */
	private void validateACKQueryMessageRUP(AckQueryPatientMessage ack){
		if(ack.typeCode == TypeCode.SUCCEDED_QUERY){
			log.info("Query realizada correctamente")
			return
		}
		
		log.error("Error al realizar la query. Error:"+ack.typeCode.toString()+". Mensaje:"+ack.text)
		throw new JanpixException(ack.text);
	}
}
