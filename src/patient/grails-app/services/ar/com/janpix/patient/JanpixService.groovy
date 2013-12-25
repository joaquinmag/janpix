package ar.com.janpix.patient

import ar.com.janpix.patient.utils.JanpixAssembler

import com.janpix.exceptions.ErrorOnApproveDocumentJanpixException
import com.janpix.exceptions.JanpixConnectionException
import com.janpix.exceptions.LoginException
import com.janpix.webclient.regdoc.AckMessage
import com.janpix.webclient.regdoc.AckStoredQueryMessage
import com.janpix.webclient.regdoc.QueryDocumentRequest
import com.janpix.webclient.regdoc.TypeCode
import com.janpix.webclient.regdoc.UpdateStateDocumentRequest
import com.janpix.webclient.rup.AckMessage as AckMessageRUP
import com.janpix.webclient.rup.TypeCode as TypeCodeRUP
import com.janpix.webclient.rup.ValidatePatientRequestMessage

class JanpixService {
	
	static transactional = false
	
	def grailsApplication
	def janpixRegdocServiceClient
	def janpixPixManagerServiceClient
	
    List<StudyCommand> queryAllStudies(PatientCommand patient) {
		List<StudyCommand> studies = []
		AckStoredQueryMessage ack
		String cuis = patient.cuis
		
		try {
			log.info("Consultando por todos los estudios del paciente "+patient)
			
			log.info("Armando Request")
			QueryDocumentRequest request = new QueryDocumentRequest()
			request.healthEntityFinder = JanpixAssembler.toHealthEntity(grailsApplication.config.patients)
			request.patientId = cuis
			
			log.info("Enviando request al WS")
			ack = janpixRegdocServiceClient.queryDocument(request)
		}
		catch(Exception ex){
			String message ="Error de conexión contra el Registro de Documentos: "+ex.message
			log.error(message, ex)
			throw new JanpixConnectionException(message);
		}
		
		// TODO validar el tipo del ACK
		
		log.info("Se recibieron "+ack.clinicalDocuments.clinicalDocument.size()+" estudios")
		
		// Transformo todos los estudios
		log.info("Transformando estudios...")
		ack.clinicalDocuments.clinicalDocument.each {com.janpix.webclient.regdoc.ClinicalDocumentDTO document->
			def study = JanpixAssembler.fromDocument(document)
			studies.add(study)
		}
		log.info("Estudios transformados correctamente")
		
		return studies
		
		/*AuthorCommand author = new AuthorCommand()
		HealthEntityCommand healthEntity = new HealthEntityCommand()
		healthEntity.oid = "HE_OID_0001"
		healthEntity.name = "HE_NAME_MOCK"
		author.healthEntity = healthEntity
		
		StudyCommand study1 = new StudyCommand()
		study1.date = new Date()
		study1.name = "Study Mock"
		study1.state = "Pendiente Mock"
		study1.comments = "Es un Mock"
		study1.uniqueId = "Mock Unique Id"
		study1.author = author
		
		studies.add(study1)
		studies.add(study1)
		
		return studies;*/
    }
	
	def approveStudy(StudyCommand study){
		if(!study || !study.uniqueId)
			throw new ErrorOnApproveDocumentJanpixException("No se envio ningun estudio para ser aprobado")
			
		AckMessage ack
		try{
			log.info("Enviado Request para aprobar el documento con id "+study.uniqueId)
			UpdateStateDocumentRequest requestMessage = new UpdateStateDocumentRequest()
			requestMessage.documentUniqueId = study.uniqueId
			requestMessage.stateDescription = JanpixAssembler.DOCUMENT_STATE_APPROVED
			requestMessage.authority = JanpixAssembler.toHealthEntity(grailsApplication.config.patients)
			
			ack = janpixRegdocServiceClient.updateStateDocument(requestMessage)
		}catch(Exception ex){
			String message ="Error de conexión contra el Registro de Documentos: "+ex.message
			log.error(message, ex)
			throw new JanpixConnectionException(message);
		}
		
		if(ack.typeCode != TypeCode.SUCCEDED_INSERTION){
			String msg = "No se pudo Aprobar el documento "+ack.text
			log.error(msg)
			throw new ErrorOnApproveDocumentJanpixException(msg)
		}
		
		log.info("Documento Aprobado correctamente")
	}
	
	def desapproveStudy(StudyCommand study){
		if(!study || !study.uniqueId)
			throw new ErrorOnApproveDocumentJanpixException("No se envio ningun estudio para ser desaprobado")
			
		AckMessage ack
		try{
			log.info("Enviado Request para desaprobar el documento con id "+study.uniqueId)
			UpdateStateDocumentRequest requestMessage = new UpdateStateDocumentRequest()
			requestMessage.documentUniqueId = study.uniqueId
			requestMessage.stateDescription = JanpixAssembler.DOCUMENT_STATE_SUBMITTED
			requestMessage.authority = JanpixAssembler.toHealthEntity(grailsApplication.config.patients)
			
			ack = janpixRegdocServiceClient.updateStateDocument(requestMessage)
		}catch(Exception ex){
			String message ="Error de conexión contra el Registro de Documentos: "+ex.message
			log.error(message, ex)
			throw new JanpixConnectionException(message);
		}
		
		if(ack.typeCode != TypeCode.SUCCEDED_INSERTION){
			String msg = "No se pudo Desaprobar el documento "+ack.text
			log.error(msg)
			throw new ErrorOnApproveDocumentJanpixException(msg)
		}
		
		log.info("Documento Desaprobado correctamente")
	}
	/**
	 * Valida la existencia del user
	 * Devuelve el patient con los datos cargados del user
	 * @param user
	 * @return
	 */
	PatientCommand validateUser(UserCommand user){
		AckMessageRUP ack 
		try{
			log.info("Enviado Request para validar paciente con cuis "+user.user)
			ValidatePatientRequestMessage requestMessage = new ValidatePatientRequestMessage()
			requestMessage.user = user.user
			requestMessage.pass = user.pass
			requestMessage.authority = JanpixAssembler.toAssigningAuthority(grailsApplication.config.patients)
			
			ack = janpixPixManagerServiceClient.validatePatient(requestMessage)
		}catch(Exception ex){
			String message ="Error de conexión contra el Registro Unico de pacientes: "+ex.message
			log.error(message, ex)
			throw new JanpixConnectionException(message);
		}
		
		log.info("Validando respuesta del servidor")
		if(ack.typeCode != TypeCodeRUP.SUCCEDED_QUERY){
			log.info("Error al obtener permisos del usuario Error:"+ack.text)
			return null
		}
		
		log.info("Respuesta correcta")
		
		return JanpixAssembler.fromPatient(ack.patient)
	}
}
