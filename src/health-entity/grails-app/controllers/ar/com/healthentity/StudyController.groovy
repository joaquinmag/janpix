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
	MultipartFile studyFile
	
	static constraints = {
		studyFile nullable: false
		observations  nullable: true, blank: false
		studyType nullable: false
		creationDate nullable: false
		studyTitle nullable: false, blank: false
		patientId nullable: false
	}
}

@Validateable
class UploadStudyCommand {
	Long studyId
}

@Secured("hasRole('HealthWorker')")
class StudyController {
	
	def studyTypeService
	def studyService
	def springSecurityService
	
	static allowedMethods = [
		create:"POST"
	]

	def create(CreateStudyCommand createStudyCommand) {
		withForm {
			createStudyCommand.validate()
			if (!createStudyCommand.hasErrors()) {
				studyService.createStudy(createStudyCommand, springSecurityService.currentUser, studyTypeService.findById(createStudyCommand.studyType))
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

	def uploadToJanpix(UploadStudyCommand uploadStudyCommand) {
		uploadStudyCommand.validate()
		if (!uploadStudyCommand.hasErrors()) {
			studyService.uploadStudy(uploadStudyCommand)
			respond uploadStudyCommand,[model:[upload_correct: true], view: 'upload_study']
		} else {
			respond uploadStudyCommand,[model:[upload_correct: false], view: 'upload_study']
		}
	}

}
