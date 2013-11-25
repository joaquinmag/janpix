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
		def hwRole = new Role(authority: 'HealthWorker').save(flush: true)
  
		def person = new Person('Juan Carlos', 'Administrador')
		def testUser = new User('admin', person)
		testUser.password = 'password'
		testUser.springSecurityService = springSecurityService
		testUser.save(flush: true)
  
		UserRole.create testUser, hwRole, true
  
		assert User.count() == 1
		assert Role.count() == 1
		assert UserRole.count() == 1

		if(Environment.current == Environment.TEST || Environment.current == Environment.DEVELOPMENT) {
			String country = "Argentina"
			// Buenos Aires
			def province = Province.findOrCreateWhere(country: country, name: "Buenos Aires").save(failOnError: true, flush: true)
			City.findOrCreateWhere(province: province, name: "Luján").save(failOnError: true, flush: true)
			
			// C.A.B.A
			province = Province.findOrCreateWhere(country: country, name: "C.A.B.A").save(failOnError: true, flush: true)
			def city = City.findOrCreateWhere(province: province, name: "C.A.B.A").save(failOnError: true, flush: true)
			
			// inicializo tipos de documentos
			def pediatric = new StudyType("Pediatría", null, 1).save(flush: true, failOnError: true)
			new StudyType("Radiografía", pediatric, 5).save(flush: true, failOnError: true)
			new StudyType("Tomografía", pediatric, 6).save(flush: true, failOnError: true)
			new StudyType("Resonancia magnética", pediatric, 7).save(flush: true, failOnError: true)
			def laboratoryPed = new StudyType("Laboratorio", pediatric, 3).save(flush: true, failOnError: true)
			new StudyType("HDL", laboratoryPed, 8).save(flush: true, failOnError: true)
			new StudyType("LDL", laboratoryPed, 9).save(flush: true, failOnError: true)
			new StudyType("Glóbulos blancos", laboratoryPed, 10).save(flush: true, failOnError: true)
			new StudyType("CD4", laboratoryPed, 11).save(flush: true, failOnError: true)
			def adults = new StudyType("Adultos", null, 2).save(flush: true, failOnError: true)
			new StudyType("Radiografía", adults, 12).save(flush: true, failOnError: true)
			new StudyType("Tomografía", adults, 13).save(flush: true, failOnError: true)
			new StudyType("Resonancia magnética", adults, 14).save(flush: true, failOnError: true)
			def laboratoryAdults = new StudyType("Laboratorio", adults, 4).save(flush: true, failOnError: true)
			new StudyType("HDL", laboratoryAdults, 15).save(flush: true, failOnError: true)
			new StudyType("LDL", laboratoryAdults, 16).save(flush: true, failOnError: true)
			new StudyType("Glóbulos blancos", laboratoryAdults, 17).save(flush: true, failOnError: true)
			new StudyType("CD4", laboratoryAdults, 18).save(flush: true, failOnError: true)
			
			ClinicalDocument cd = new ClinicalDocument(
										filename: "test",
										mimeType: "text/xml",
										size: 123,				
										binaryData: [ 12, 12 ]
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
}
