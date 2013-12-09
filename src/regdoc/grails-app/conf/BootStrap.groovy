
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
		ClinicalDocumentType.findOrCreateWhere(name: "Resonancia magnética", father: adults, idDocumentType: 14l).save(failOnError: true, flush: true)
		def laboratoryAdults = ClinicalDocumentType.findOrCreateWhere(name: "Laboratorio", father: adults, idDocumentType: 4l).save(failOnError: true, flush: true)
		ClinicalDocumentType.findOrCreateWhere(name: "HDL", father: laboratoryAdults, idDocumentType: 15l).save(failOnError: true, flush: true)
		ClinicalDocumentType.findOrCreateWhere(name: "LDL", father: laboratoryAdults, idDocumentType: 16l).save(failOnError: true, flush: true)
		ClinicalDocumentType.findOrCreateWhere(name: "Glóbulos blancos", father: laboratoryAdults, idDocumentType: 17l).save(failOnError: true, flush: true)
		ClinicalDocumentType.findOrCreateWhere(name: "CD4", father: laboratoryAdults, idDocumentType: 18l).save(failOnError: true, flush: true)
    }
    def destroy = {
    }
}
