import ar.com.healthentity.Role
import ar.com.healthentity.Person
import ar.com.healthentity.User
import ar.com.healthentity.UserRole

class BootStrap {

    def init = { servletContext ->
		def hwRole = new Role(authority: 'HealthWorker').save(flush: true)
  
		def person = new Person('Juan Carlos', 'Administrador')
		def testUser = new User('admin', person)
		testUser.password = 'password'
		testUser.save(flush: true)
  
		UserRole.create testUser, hwRole, true
  
		assert User.count() == 1
		assert Role.count() == 1
		assert UserRole.count() == 1
    }
    def destroy = {
    }
}
