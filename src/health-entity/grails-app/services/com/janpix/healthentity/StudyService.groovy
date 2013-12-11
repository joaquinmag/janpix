package com.janpix.healthentity

import org.springframework.web.multipart.MultipartFile;

import ar.com.healthentity.ClinicalDocument
import ar.com.healthentity.CreateStudyCommand;
import ar.com.healthentity.FormatType;
import ar.com.healthentity.Study
import ar.com.healthentity.StudyType;
import ar.com.healthentity.User
import ar.com.healthentity.Patient
import ar.com.healthentity.janpix.StudyTypeRepository;
import ar.com.healthentity.janpix.utils.JanpixAssembler;

import com.janpix.exceptions.PatientDoesNotExistsException
import com.janpix.exceptions.StudyDoesNotExistsException

import grails.transaction.Transactional

@Transactional
class StudyService {
	
	def grailsApplication
	def janpixService
	def springSecurityService
	def studyTypeService
	
	String getUploadsPath() {
		return grailsApplication.mainContext.servletContext.getRealPath("/uploads")
	}

    def createStudy(CreateStudyCommand cmd, User author, StudyType type) {
		def random = new Random().nextInt().abs().toString()
		def randomName = "${random}${cmd.studyFile.originalFilename}"
		copy(cmd.studyFile, randomName)
		def cd = new ClinicalDocument(
			filename: cmd.studyFile.originalFilename,
			mimeType: cmd.studyFile.contentType,
			size: cmd.studyFile.size,
			fileLocation: randomName,
			format: cmd.formatType
		)
		cd.save(failOnError: true)
		def study = new Study(
			date: cmd.creationDate.toDateTimeAtStartOfDay().toDate(),
			title: cmd.studyTitle,
			observation: cmd.observations,
			document: cd,
			type: type
		)
		def patient = Patient.findById(cmd.patientId)
		patient.addToStudies(study)
		study.save(failOnError: true)
    }
	
	def getDocumentByStudyId(String studyId) {
		def study = Study.get(studyId)
		if (!study)
			throw new StudyDoesNotExistsException()

		study.document
	}

	def uploadStudy(def cmd) {
		def study = Study.get(cmd.id)
		if (!study)
			throw new StudyDoesNotExistsException()
		
		def currentUser = springSecurityService.currentUser
			
		janpixService.uploadDocument(study,currentUser)
		study.isSynchro = true
	}
	
	def downloadRemoteStudies(Long patientId) {
		def patient = Patient.get(patientId)
		if (!patient)
			throw new PatientDoesNotExistsException("No existe el paciente con id=${patientId}")
		janpixService.queryAllStudies(patient)
	}

	def obtainRemoteStudyForPatient(Long idPatient, Long uniqueId, Long filename) {
		final def patient = Patient.get(idPatient)
		if (!patient)
			throw new PatientDoesNotExistsException("No existe el paciente con id=${idPatient}")

		final def random = new Random().nextInt().abs().toString()
		final def randomName = "${random}${filename}"
		final def path = getUploadsPath()
		final def destination = new File(path, fileRandomName)
		def study = janpixService.getDocumentByUniqueId(uniqueId, destination)
		study.fileLocation = randomName
	}

	private def copy(def file, def fileRandomName) {
		final def path = getUploadsPath()
		final def destination = new File(path, fileRandomName)
		file.transferTo(destination)
	}
}
