package ar.com.healthentity

import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.validation.Validateable
import org.joda.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

@Validateable
class CreateStudyCommand {
	Long patientId
	String studyTitle
	LocalDate creationDate
	Long studyType
	String observations
	MultipartFile file
	
	static constraints = {
		observations  nullable: true, blank: false
		studyType nullable: false
		creationDate nullable: false
		studyTitle nullable: false, blank: false
		patientId nullable: false
	}
}

@Secured("hasRole('HealthWorker')")
class StudyController {
	
	def studyTypeService
	
	static allowedMethods = [
		create:"POST"
	]

	def create(CreateStudyCommand createStudyCommand) {
		withForm {
			createStudyCommand.validate()
			if (!createStudyCommand.hasErrors()) {
				flash.success = "Estudio creado correctamente"
				redirect mapping:'showPatient', id: createStudyCommand.patientId
			} else {
				render view:"/patient/show", model:[patientInstance: Patient.findById(createStudyCommand.patientId), studyTypeRoots: studyTypeService.listStudyTypeRoots(), createStudyModel: createStudyCommand]
			}
		}.invalidToken {
			flash.warning = "Ha intentado enviar información que ya ha enviado anteriormente. Si realmente desea ingresar datos nuevos, recargue la página."
			redirect mapping: 'showPatient', id: createStudyCommand.patientId
		}
	}
}
