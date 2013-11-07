package ar.com.healthentity


import static org.springframework.http.HttpStatus.*
import grails.plugins.springsecurity.Secured
import grails.transaction.Transactional

import com.janpix.exceptions.JanpixException
import com.janpix.exceptions.JanpixPossibleMatchingPatientException

@Secured("isAuthenticated()")
//@Transactional(readOnly = true,noRollbackFor=[JanpixException]) // TODO ver porque no funciona
class PatientController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	def janpixService

	def registerOnJanpix(Patient patientInstance){
		try{
			if (patientInstance == null) {
				notFound()
				return
			}
			
		String cuis = janpixService.addNewPatient(patientInstance)
		
		render(view:"register_janpix_ok",model:[patientInstance:patientInstance,cuis:cuis])
			
		}
		catch(JanpixPossibleMatchingPatientException ex){
			//render(view:"matching_template",model:[it:patientInstance])
			//TODO el patient no tiene relaciones porque se desatacho al lanzar la exception
			// Usar solo valores constantes
			respond patientInstance, view:'matching_patient'
			return
		}
		catch(JanpixException ex){
			render ex.message
		}

	}
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Patient.list(params), model:[patientInstanceCount: Patient.count()]
    }

    def show(Patient patientInstance) {
        respond patientInstance
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
                flash.message = message(code: 'default.created.message', args: [message(code: 'patientInstance.label', default: 'Patient'), patientInstance.id])
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
