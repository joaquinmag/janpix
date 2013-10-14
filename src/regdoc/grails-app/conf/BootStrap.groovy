
import com.janpix.regdoc.domain.*
import com.janpix.servidordocumentos.dto.AuthorDTO

class BootStrap {

    def init = { servletContext ->
		// inicializo tipos de documentos
		def pediatric = new ClinicalDocumentType("Pediatría", null, 1).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Radiografía", pediatric, 5).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Tomografía", pediatric, 6).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Resonancia magnética", pediatric, 7).save(flush: true, failOnError: true)
		def laboratoryPed = new ClinicalDocumentType("Laboratorio", pediatric, 3).save(flush: true, failOnError: true)
		new ClinicalDocumentType("HDL", laboratoryPed, 8).save(flush: true, failOnError: true)
		new ClinicalDocumentType("LDL", laboratoryPed, 9).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Glóbulos blancos", laboratoryPed, 10).save(flush: true, failOnError: true)
		new ClinicalDocumentType("CD4", laboratoryPed, 11).save(flush: true, failOnError: true)
		def adults = new ClinicalDocumentType("Adultos", null, 2).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Radiografía", adults, 12).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Tomografía", adults, 13).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Resonancia magnética", adults, 14).save(flush: true, failOnError: true)
		def laboratoryAdults = new ClinicalDocumentType("Laboratorio", adults, 4).save(flush: true, failOnError: true)
		new ClinicalDocumentType("HDL", laboratoryAdults, 15).save(flush: true, failOnError: true)
		new ClinicalDocumentType("LDL", laboratoryAdults, 16).save(flush: true, failOnError: true)
		new ClinicalDocumentType("Glóbulos blancos", laboratoryAdults, 17).save(flush: true, failOnError: true)
		new ClinicalDocumentType("CD4", laboratoryAdults, 18).save(flush: true, failOnError: true)
		
		
    }
    def destroy = {
    }
}
