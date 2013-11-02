
import ar.com.healthentity.Role
import ar.com.healthentity.User
import ar.com.healthentity.UserRole

class BootStrap {

    def init = { servletContext ->
		def hwRole = new Role('HealthWorker').save(flush: true)
  
		def testUser = new User(username: 'admin', enabled: true, password: 'password')
		testUser.save(flush: true)
  
		UserRole.create testUser, hwRole, true
  
		assert User.count() == 1
		assert Role.count() == 1
		assert UserRole.count() == 1
    }
    def destroy = {
    }
}
