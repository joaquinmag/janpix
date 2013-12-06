package ar.com.healthentity


import static org.springframework.http.HttpStatus.*
import grails.plugins.springsecurity.Secured
import grails.transaction.Transactional

import com.janpix.exceptions.JanpixException
import com.janpix.exceptions.JanpixPossibleMatchingPatientException

@Secured("hasRole('HealthWorker')")
//@Transactional(readOnly = true,noRollbackFor=[JanpixException]) // TODO ver porque no funciona
class PatientController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def grailsApplication
	def janpixService
	def studyTypeService
	def studyService
	
	/**
	 * Registra un paciente en Janpix
	 * @param patientInstance
	 * @return
	 */
	def registerOnJanpix(Patient patientInstance){
		try{
			if (patientInstance == null) {
				notFound()
				return
			}
			janpixService.addNewPatient(patientInstance)
			
			String healthEntity = grailsApplication.config.healthEntity.name
			respond patientInstance,[view:"register_janpix_ok",model:[healthEntity:healthEntity]]
			return
			
		}
		catch(JanpixPossibleMatchingPatientException ex){
			//TODO el patient no tiene relaciones porque se desatacho al lanzar la exception
			// Usar solo valores constantes
			respond patientInstance, view:'matching_patient'
			return
		}
		catch(JanpixException ex){
			render ex.message
			return
		}
		catch(Exception ex){
			render "Ocurrio un error inesperado"
			return
		}
	}
	
	/**
	 * Fuerza el registro de un paciente que matchea con otros
	 * en el Pix Manager
	 * @param patientInstance
	 * @return
	 */
	def forceRegister(Patient patientInstance){
		try{
			if (patientInstance == null) {
				notFound()
				return
			}
			
			janpixService.addNewPatientWithoutValidation(patientInstance)
			
			flash.message = "El paciente "+patientInstance+" se registro correctamente"
			redirect action:"index",id:patientInstance.id
			return
		}
		catch(JanpixException ex){
			flash.message = "Error: "+ex.message
			redirect action:"index",id:patientInstance.id
			return
		}
	}
	
	/**
	 * Lista todos los pacientes que matchean con el paciente pasado por parametro
	 * @param patientInstance
	 * @return
	 */
	def listMatching(Patient patientInstance){
		try{
			if (patientInstance == null) {
				notFound()
				return
			}
			
			List<Patient> patients = janpixService.getAllPossibleMatchedPatients(patientInstance)
			
			respond patientInstance,[view:"list_matched_patients",
									model:[patientInstanceList:patients,patientInstanceCount: patients.size()]]
			
		}
		catch(JanpixException ex){
			flash.message = ex.message
			redirect action:"index",id:patientInstance.id
			return
		}
	}
	
	
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Patient.list(params), model:[patientInstanceCount: Patient.count()]
    }

    def show(Patient patientInstance) {
        respond patientInstance, model:[studyTypeRoots: studyTypeService.listStudyTypeRoots(), createStudyModel: new CreateStudyCommand()]
    }
	
	def showDocuments(Patient patientInstance) {
		respond patientInstance, [view: "show_documents", model:[urldownload: studyService.uploadsPath]]
	}

    def create() {
        respond new Patient(params)
    }

    @Transactional
    def save(Patient patientInstance) {
        if (patientInstance == null) {
            notFound()
            return
        }

        if (patientInstance.hasErrors()) {
            respond patientInstance.errors, view:'create'
            return
        }

        patientInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'patient.created.message', args: [patientInstance.firstName, patientInstance.lastName])
                redirect patientInstance
            }
            '*' { respond patientInstance, [status: CREATED] }
        }
    }

    def edit(Patient patientInstance) {
        respond patientInstance
    }

    @Transactional
    def update(Patient patientInstance) {
        if (patientInstance == null) {
            notFound()
            return
        }

        if (patientInstance.hasErrors()) {
            respond patientInstance.errors, view:'edit'
            return
        }

        patientInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Patient.label', default: 'Patient'), patientInstance.id])
                redirect patientInstance
            }
            '*'{ respond patientInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Patient patientInstance) {

        if (patientInstance == null) {
            notFound()
            return
        }

        patientInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Patient.label', default: 'Patient'), patientInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'patientInstance.label', default: 'Patient'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
