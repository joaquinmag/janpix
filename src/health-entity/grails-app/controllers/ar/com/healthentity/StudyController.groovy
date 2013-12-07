package ar.com.healthentity

import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.validation.Validateable
import org.joda.time.LocalDate
import org.springframework.web.multipart.MultipartFile

import com.janpix.exceptions.ErrorUploadingDocumentException
import com.janpix.exceptions.PatientDoesNotExistsException
import com.janpix.exceptions.StudyDoesNotExistsException
import com.janpix.healthentity.StudyService

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
	Long id
}

@Secured("hasRole('HealthWorker')")
class StudyController {
	
	def studyTypeService
	def studyService
	def springSecurityService
	
	static allowedMethods = [
		create:"POST",
		uploadToJanpix: "POST",
		download: "GET"
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
	
	def download(String id) {
		if(id != null){
			try {
				ClinicalDocument document = studyService.getDocumentByStudyId(id)
				String nameDocument = document.filename
				String mimeType = document.mimeType
				response.setContentLength((int)document.size)
				response.addHeader("Content-disposition", "attachment; filename=${nameDocument}")
				response.addHeader("Content-type", "${mimeType}")
				
				OutputStream out = response.getOutputStream()
				File f = new File("${studyService.uploadsPath}/${document.fileLocation}")
				out.write(f.readBytes())
				out.close()
			}
			catch (StudyDoesNotExistsException ex) {
				log.error("No se puede descargar archivo para studyId=${id}", ex)
				render status: 404
			}
		} else {
			log.error("id == null")
			render status: 404
		}
	}

	def uploadToJanpix(UploadStudyCommand uploadStudyCommand) {
		try{
			uploadStudyCommand.validate()
			if (!uploadStudyCommand.hasErrors()) {
				studyService.uploadStudy(uploadStudyCommand)
				respond uploadStudyCommand,[model:[upload_correct: true], view: 'upload_study']
			} else {
				respond uploadStudyCommand,[model:[upload_correct: false], view: 'upload_study']
			}
		}
		catch(	ErrorUploadingDocumentException e) {
			log.error("error en StudyController: ",e)
			render "Error al subir el documento. Esto puede deberse a un problema de conexion. Error: "+e
			return
		}
		catch(Exception e){
			log.error("error en StudyController: ",e)
			render "Error inesperado al subir el documento. Error: "+e
			return
		}
	}

	def refreshRemoteDocuments(Long id) {
		if (id == null) {
			log.error("id == null")
			render status: 400
		} else {
			try {
				def studies = studyService.downloadRemoteStudies(id)
				respond id,[view: "remote_documents", model:[remoteStudies: studies]]
			}
			catch(PatientDoesNotExistsException ex) {
				log.error("Paciente no existe.", ex)
				render status: 404
			}
		}
	}

}
