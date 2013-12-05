import java.util.Date;
import java.util.List;

import ar.com.healthentity.ClinicalDocument;
import ar.com.healthentity.Patient
import ar.com.healthentity.Role
import ar.com.healthentity.Person
import ar.com.healthentity.SexType;
import ar.com.healthentity.Study;
import ar.com.healthentity.StudyType
import ar.com.healthentity.User
import ar.com.healthentity.UserRole

import grails.util.Environment
import ar.com.healthentity.City
import ar.com.healthentity.Province


class BootStrap {
	
	def springSecurityService

    def init = { servletContext ->
		def hwRole = Role.findOrCreateByAuthority('HealthWorker').save(flush: true)


		def testUser = User.findByUsername("admin")
		if (testUser == null) {
			def person = new Person('Juan Carlos', 'Administrador')
			testUser = new User('admin', person)
			testUser.password = 'password'
			testUser.springSecurityService = springSecurityService
			testUser.save(flush: true)
			UserRole.create testUser, hwRole, true
		}
  
		assert User.count() == 1
		assert Role.count() == 1
		assert UserRole.count() == 1

		// inicializo tipos de documentos
		def pediatric = StudyType.findOrCreateByIdStudyType(1)
		pediatric.name = "Pediatría"
		pediatric.save(flush: true, failOnError: true)
		def radioped = StudyType.findOrCreateByIdStudyType(5)
		radioped.name = "Radiografía"
		radioped.father = pediatric
		radioped.save(flush: true, failOnError: true)
		def tomoped =  StudyType.findOrCreateByIdStudyType(6)
		tomoped.name = "Tomografía"
		tomoped.father = pediatric
		tomoped.save(flush: true, failOnError: true)
		def resoped =  StudyType.findOrCreateByIdStudyType(7)
		tomoped.name = "Resonancia magnética"
		tomoped.father = pediatric
		tomoped.save(flush: true, failOnError: true)
		def laboratoryPed =  StudyType.findOrCreateByIdStudyType(3)
		laboratoryPed.name = "Laboratorio"
		laboratoryPed.father = pediatric
		laboratoryPed.save(flush: true, failOnError: true)
		def labpedhdl = StudyType.findOrCreateByIdStudyType(8)
		labpedhdl.name = "HDL"
		labpedhdl.father = laboratoryPed
		labpedhdl.save(flush: true, failOnError: true)
		def labpedldl = StudyType.findOrCreateByIdStudyType(9)
		labpedldl.name = "LDL"
		labpedldl.father = laboratoryPed
		labpedldl.save(flush: true, failOnError: true)
		def labpedblancos = StudyType.findOrCreateByIdStudyType(10)
		labpedblancos.name = "Glóbulos blancos"
		labpedblancos.father = laboratoryPed
		labpedblancos.save(flush: true, failOnError: true)
		def labpedcdcuatro = StudyType.findOrCreateByIdStudyType(11)
		labpedcdcuatro.name = "CD4"
		labpedcdcuatro.father = laboratoryPed
		labpedcdcuatro.save(flush: true, failOnError: true)
		def adults = StudyType.findOrCreateByIdStudyType(2)
		adults.name = "Adultos"
		adults.save(flush: true, failOnError: true)
		def radioadult = StudyType.findOrCreateByIdStudyType(12)
		radioadult.name = "Radiografía"
		radioadult.father = adults
		radioadult.save(flush: true, failOnError: true)
		def tomoadult = StudyType.findOrCreateByIdStudyType(13)
		tomoadult.name = "Tomografía"
		tomoadult.father = adults
		tomoadult.save(flush: true, failOnError: true)
		if(Environment.current != Environment.PRODUCTION) {
			new StudyType("Resonancia magnética", adults, 14).save(flush: true, failOnError: true)
			def laboratoryAdults = new StudyType("Laboratorio", adults, 4).save(flush: true, failOnError: true)
			new StudyType("HDL", laboratoryAdults, 15).save(flush: true, failOnError: true)
			new StudyType("LDL", laboratoryAdults, 16).save(flush: true, failOnError: true)
			new StudyType("Glóbulos blancos", laboratoryAdults, 17).save(flush: true, failOnError: true)
			new StudyType("CD4", laboratoryAdults, 18).save(flush: true, failOnError: true)
		}

		this.buildMockCities()

		if(Environment.current == Environment.TEST || Environment.current == Environment.DEVELOPMENT) {
			
			def province = Province.findByName("Capital Federal")
			def city = City.findByProvinceAndName(province,"Capital Federal")

			ClinicalDocument cd = new ClinicalDocument(
										filename: "test",
										mimeType: "text/xml",
										size: 123,				
										fileLocation: "test"
									)
			cd.save(flush: true, failOnError: true)
			def study = new Study(
				date: new Date(),
				title: "test",
				observation: "Todo normal",
				document: cd,
				type: laboratoryAdults
			)
			def patient = new Patient(firstName: "Juan Carlos",
										  lastName: "Pérez",
										  dni: "20535122",
										  addressName: "Cabildo",
										  addressNumber: "123",
										  city: city,
										  sex: SexType.Masculino,
										  birthdate: new Date(2000, 1, 5) 
							)
			patient.save(flush:true, failOnError: true)
			patient.addToStudies(study)
			study.save(flush: true, failOnError: true)
		}

    }
    def destroy = {
    }
	
	private void buildMockCities(){
		String country = "Argentina"
		// Buenos Aires
		def province = Province.findOrCreateWhere(country: country, name: "Buenos Aires").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Luján").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Castelar").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Campana").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Gerli").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Glew").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Moreno").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Moron").save(failOnError: true, flush: true)

		// C.A.B.A
		province = Province.findOrCreateWhere(country: country, name: "Capital Federal").save(failOnError: true, flush: true)
		def city = City.findOrCreateWhere(province: province, name: "Capital Federal").save(failOnError: true, flush: true)
		
		// Mendoza
		province = Province.findOrCreateWhere(country: country, name: "Mendoza").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Capital").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Godoy Cruz").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "San Rafael").save(failOnError: true, flush: true)
	}
	
}
