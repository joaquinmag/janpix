
import grails.util.Environment

import com.janpix.regdoc.domain.*

class BootStrap {

    def init = { servletContext ->
		// inicializo tipos de documentos
		def pediatric = ClinicalDocumentType.findOrCreateByIdDocumentType(1)
		pediatric.name = "Pediatría"
		pediatric.save(flush: true, failOnError: true)
		def radioped = ClinicalDocumentType.findOrCreateByIdDocumentType(5)
		radioped.name = "Radiografía"
		radioped.father = pediatric
		radioped.save(flush: true, failOnError: true)
		def tomoped =  ClinicalDocumentType.findOrCreateByIdDocumentType(6)
		tomoped.name = "Tomografía"
		tomoped.father = pediatric
		tomoped.save(flush: true, failOnError: true)
		def resoped =  ClinicalDocumentType.findOrCreateByIdDocumentType(7)
		tomoped.name = "Resonancia magnética"
		tomoped.father = pediatric
		tomoped.save(flush: true, failOnError: true)
		def laboratoryPed =  ClinicalDocumentType.findOrCreateByIdDocumentType(3)
		laboratoryPed.name = "Laboratorio"
		laboratoryPed.father = pediatric
		laboratoryPed.save(flush: true, failOnError: true)
		def labpedhdl = ClinicalDocumentType.findOrCreateByIdDocumentType(8)
		labpedhdl.name = "HDL"
		labpedhdl.father = laboratoryPed
		labpedhdl.save(flush: true, failOnError: true)
		def labpedldl = ClinicalDocumentType.findOrCreateByIdDocumentType(9)
		labpedldl.name = "LDL"
		labpedldl.father = laboratoryPed
		labpedldl.save(flush: true, failOnError: true)
		def labpedblancos = ClinicalDocumentType.findOrCreateByIdDocumentType(10)
		labpedblancos.name = "Glóbulos blancos"
		labpedblancos.father = laboratoryPed
		labpedblancos.save(flush: true, failOnError: true)
		def labpedcdcuatro = ClinicalDocumentType.findOrCreateByIdDocumentType(11)
		labpedcdcuatro.name = "CD4"
		labpedcdcuatro.father = laboratoryPed
		labpedcdcuatro.save(flush: true, failOnError: true)
		def adults = ClinicalDocumentType.findOrCreateByIdDocumentType(2)
		adults.name = "Adultos"
		adults.save(flush: true, failOnError: true)
		def radioadult = ClinicalDocumentType.findOrCreateByIdDocumentType(12)
		radioadult.name = "Radiografía"
		radioadult.father = adults
		radioadult.save(flush: true, failOnError: true)
		def tomoadult = ClinicalDocumentType.findOrCreateByIdDocumentType(13)
		tomoadult.name = "Tomografía"
		tomoadult.father = adults
		tomoadult.save(flush: true, failOnError: true)
		if(Environment.current != Environment.PRODUCTION) {
			new ClinicalDocumentType("Resonancia magnética", adults, 14).save(flush: true, failOnError: true)
			def laboratoryAdults = new ClinicalDocumentType("Laboratorio", adults, 4).save(flush: true, failOnError: true)
			new ClinicalDocumentType("HDL", laboratoryAdults, 15).save(flush: true, failOnError: true)
			new ClinicalDocumentType("LDL", laboratoryAdults, 16).save(flush: true, failOnError: true)
			new ClinicalDocumentType("Glóbulos blancos", laboratoryAdults, 17).save(flush: true, failOnError: true)
			new ClinicalDocumentType("CD4", laboratoryAdults, 18).save(flush: true, failOnError: true)
		}
		
		
    }
    def destroy = {
    }
}
