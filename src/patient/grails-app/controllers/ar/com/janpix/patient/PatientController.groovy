package ar.com.janpix.patient

import ar.com.janpix.patient.PatientCommand
import ar.com.janpix.patient.StudyCommand
import com.janpix.exceptions.JanpixException

class PatientController {

	def securityService
	def janpixService
	
	/**
	 * Lista todos los documentos del paciente actualmente logueado
	 * @return
	 */
	def listPatientStudies() {
		log.info("Obteniendo usuario logueado..")
		PatientCommand patient = securityService.currentUser
		if(patient) {
			try {
				// Busco todos los estudios del paciente consultando el Registro Documentos
				log.info("Consultando documentos del paciente "+patient)
				List<StudyCommand> studies =  janpixService.queryAllStudies(patient.cuis)
				
				log.info("Documentos obtenidos correctamente. Se obtuvieron "+studies.size()+" documentos")
				respond patient,[view: "remote_studies", model:[studiesInstanceList: studies]]
				
			} catch(JanpixException e) {
				log.error("Error obteniendo documentos. Error:"+e.message)
				// TODO renderizar a una pagina error
				render status: 404
			}
		} else {
			log.error("patient == null")
			render status: 404
		}
		
	}
	
	/**
	 * Aprueba el documento enviado
	 * @param id
	 * @return
	 */
	def approbePatientStudy(Long id){
		
	}
}
