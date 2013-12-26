import java.util.Date;
import java.util.List;

import ar.com.healthentity.ClinicalDocument;
import ar.com.healthentity.FormatType;
import ar.com.healthentity.Patient
import ar.com.healthentity.Role
import ar.com.healthentity.Person
import ar.com.healthentity.SexType;
import ar.com.healthentity.Study;
import ar.com.healthentity.StudyType
import ar.com.healthentity.User
import ar.com.healthentity.UserRole

import grails.util.GrailsUtil
import grails.util.Environment
import ar.com.healthentity.City
import ar.com.healthentity.Province


class BootStrap {
	
	def springSecurityService

    def init = { servletContext ->
		def hwRole = Role.findOrCreateByAuthority('HealthWorker').save(flush: true)

		def testUser
		if (GrailsUtil.environment == "democonsultorio") {
			testUser = User.findByUsername("drcasa")
			if (testUser == null) {
				def person = new Person('Gregorio', 'Casa')
				testUser = new User('drcasa', person)
				testUser.password = 'password'
				testUser.springSecurityService = springSecurityService
				testUser.save(flush: true)
				UserRole.create testUser, hwRole, true
			}
		} else if (GrailsUtil.environment == "demohospital") {
			testUser = User.findByUsername("susana")
			if (testUser == null) {
				def person = new Person('Susana', 'Horia')
				testUser = new User('susana', person)
				testUser.password = 'password'
				testUser.springSecurityService = springSecurityService
				testUser.save(flush: true)
				UserRole.create testUser, hwRole, true
			}
		} else {
			testUser = User.findByUsername("admin")
			if (testUser == null) {
				def person = new Person('Juan Carlos', 'Administrador')
				testUser = new User('admin', person)
				testUser.password = 'password'
				testUser.springSecurityService = springSecurityService
				testUser.save(flush: true)
				UserRole.create testUser, hwRole, true
			}
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
		StudyType.findOrCreateWhere(name: "Resonancia magnética", father: adults, idStudyType: 14l).save(failOnError: true, flush: true)
		def laboratoryAdults = StudyType.findOrCreateWhere(name: "Laboratorio", father: adults, idStudyType: 4l).save(failOnError: true, flush: true)
		StudyType.findOrCreateWhere(name: "HDL", father: laboratoryAdults, idStudyType: 15l).save(failOnError: true, flush: true)
		StudyType.findOrCreateWhere(name: "LDL", father: laboratoryAdults, idStudyType: 16l).save(failOnError: true, flush: true)
		StudyType.findOrCreateWhere(name: "Glóbulos blancos", father: laboratoryAdults, idStudyType: 17l).save(failOnError: true, flush: true)
		StudyType.findOrCreateWhere(name: "CD4", father: laboratoryAdults, idStudyType: 18l).save(failOnError: true, flush: true)

		this.buildMockCities()
		
		// Creo datos para la presentacion
		if (GrailsUtil.environment == "democonsultorio") {			
			Patient diego = createPatientDiegoArmando()
			createStudiesForPatient(diego)
		}

		if(Environment.current == Environment.TEST || Environment.current == Environment.DEVELOPMENT) {
			
			def province = Province.findByName("AR-C")
			def city = City.findByProvinceAndName(province,"Capital Federal")

			ClinicalDocument cd = new ClinicalDocument(
										filename: "test",
										mimeType: "text/xml",
										size: 123,				
										fileLocation: "test",
										format: FormatType.PDF
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
		String country = "AR"
		// Buenos Aires
		def province = Province.findOrCreateWhere(country: country, name: "AR-B", description: "Buenos Aires").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Luján").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Castelar").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Campana").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Gerli").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Glew").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Moreno").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Moron").save(failOnError: true, flush: true)

		// C.A.B.A
		province = Province.findOrCreateWhere(country: country, name: "AR-C", description: "Capital Federal").save(failOnError: true, flush: true)
		def city = City.findOrCreateWhere(province: province, name: "Capital Federal").save(failOnError: true, flush: true)
		
		// Mendoza
		province = Province.findOrCreateWhere(country: country, name: "AR-M", description: "Mendoza").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Capital").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Godoy Cruz").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "San Rafael").save(failOnError: true, flush: true)
	}
	
	private Patient createPatientDiegoArmando(){
		def province = Province.findByName("AR-C")
		def city = City.findByProvinceAndName(province,"Capital Federal")
		
		Patient diego = new Patient()
		diego.firstName = "Diego Armando"
		diego.lastName = "Maradona"
		diego.dni = "10101010"
		diego.addressName = "México"
		diego.addressNumber = "1986"
		diego.city =  city
		diego.sex = SexType.Masculino
		diego.studies = []
	
		diego.birthdate = Date.parse("yyyy-M-d","1960-10-30")
	
		diego.email = "d10s@argentina.com.ar"
		
		diego.save(flush:true, failOnError: true)
		
		return diego
	}
	
	private void createStudiesForPatient(Patient patient){
		/*def ctx = SCH.servletContext.getAttribute(GA.APPLICATION_CONTEXT)
		String uploadsPath = ctx.servletContext.getRealPath("/uploads")
		File file = new File("Recetaprivada.jpg")
		long fileSize = file.size()*/
		
		ClinicalDocument cd1 = createRecetaPDF("tobillo")
		def study1 = new Study(
			date: Date.parse("yyyy-M-d","1990-07-08"),
			title: "Hinchazon tobillo izquierdo",
			observation: "Realizarse una tomografía",
			document: cd1,
			type: StudyType.findByIdStudyType(13)
		)
		patient.addToStudies(study1)
		study1.save(flush:true,failOnError:true)
		
		ClinicalDocument cd2 = createRecetaPDF("cansancio")
		def study2 = new Study(
			date: Date.parse("yyyy-M-d","1994-07-13"),
			title: "Cansancio generalizado",
			observation: "Cansancio y exitación. Efedrina",
			document: cd2,
			type: StudyType.findByIdStudyType(13)
		)
		patient.addToStudies(study2)
		study2.save(flush:true,failOnError:true)
	}
	
	private ClinicalDocument createRecetaPDF(String name){
		ClinicalDocument cd = new ClinicalDocument(
			filename: "receta_"+name,
			mimeType: "image/jpeg",
			size: 6714,
			fileLocation: "Recetaprivada.jpg",
			format: FormatType.PDF,
		)
		cd.save(flush: true, failOnError: true)
		
		return cd
	}
	
}
