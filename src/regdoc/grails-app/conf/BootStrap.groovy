
import com.janpix.regdoc.domain.ClinicalDocumentType

class BootStrap {

    def init = { servletContext ->
		// inicializo tipos de documentos
		def pediatric = new ClinicalDocumentType("Pediatría", null).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Radiografía", pediatric).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Tomografía", pediatric).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Resonancia magnética", pediatric).save(flush: true, failOnError: true)
		def laboratoryPed = new ClinicalDocumentType("Laboratorio", pediatric).save(flush: true, failOnError: true)
		new ClinicalDocumentType("HDL", laboratoryPed).save(flush: true, failOnError: true)
		new ClinicalDocumentType("LDL", laboratoryPed).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Glóbulos blancos", laboratoryPed).save(flush: true, failOnError: true)
		new ClinicalDocumentType("CD4", laboratoryPed).save(flush: true, failOnError: true)
		def adults = new ClinicalDocumentType("Adultos", null).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Radiografía", adults).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Tomografía", adults).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Resonancia magnética", adults).save(flush: true, failOnError: true)
		def laboratoryAdults = new ClinicalDocumentType("Laboratorio", adults).save(flush: true, failOnError: true)
		new ClinicalDocumentType("HDL", laboratoryAdults).save(flush: true, failOnError: true)
		new ClinicalDocumentType("LDL", laboratoryAdults).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Glóbulos blancos", laboratoryAdults).save(flush: true, failOnError: true)
		new ClinicalDocumentType("CD4", laboratoryAdults).save(flush: true, failOnError: true)
    }
    def destroy = {
    }
}
