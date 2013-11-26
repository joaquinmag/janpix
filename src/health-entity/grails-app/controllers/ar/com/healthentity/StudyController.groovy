package ar.com.healthentity

import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.validation.Validateable
import org.joda.time.LocalDate;

@Validateable
class CreateStudyCommand {
	Long patientId
	String studyTitle
	LocalDate creationDate
	Long studyType
	String observations
	//byte[] file
	
	static constraints = {
		//file maxSize: 1024 * 1024 * 20, nullable: false, blank: false
		observations  nullable: true, blank: false
		studyType nullable: false
		creationDate nullable: false
		studyTitle nullable: false, blank: false
		patientId nullable: false
	}
}

@Secured("hasRole('HealthWorker')")
class StudyController {
	
	static allowedMethods = [
		create:"POST"
	]

	def create(CreateStudyCommand createStudyCommand) {
		withForm {
			createStudyCommand.validate()
			if (!createStudyCommand.hasErrors()) {
		//		redirect controller: 'patient', action:'show', id: createStudyCommand.patientId
			} else {
		//		flash.error = "ocurrió un error"
		//		redirect mapping:"patients"//controller: 'patient', action: 'list'
				render view:"/patient/show", model:[patientInstance: Patient.findById(createStudyCommand.patientId), createStudyModel: createStudyCommand]
			}
		}.invalidToken {
			flash.warning = "Ha intentado enviar información que ya ha enviado anteriormente. Si realmente desea ingresar datos nuevos, recargue la página."
			redirect mapping: 'showPatient', id: createStudyCommand.patientId
		}
	}
}
