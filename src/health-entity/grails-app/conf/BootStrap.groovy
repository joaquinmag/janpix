import ar.com.healthentity.Role
import ar.com.healthentity.Person
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

		if(Environment.current == Environment.TEST || Environment.current == Environment.DEVELOPMENT){
			String country = "Argentina"
			// Buenos Aires
			def province = Province.findOrCreateWhere(country: country, name: "Buenos Aires").save(failOnError: true, flush: true)
			City.findOrCreateWhere(province: province, name: "Luján").save(failOnError: true, flush: true)
			
			// C.A.B.A
			province = Province.findOrCreateWhere(country: country, name: "C.A.B.A").save(failOnError: true, flush: true)
			City.findOrCreateWhere(province: province, name: "C.A.B.A").save(failOnError: true, flush: true)
		}
		
    }
    def destroy = {
    }
}
