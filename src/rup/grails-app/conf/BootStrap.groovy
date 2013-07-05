import com.janpix.rup.empi.AuthorityRUP
import com.janpix.rup.empi.City
import com.janpix.rup.empi.Country
import com.janpix.rup.empi.HealthEntity
import com.janpix.rup.empi.Province

class BootStrap {

	def grailsApplication
	
    def init = { servletContext ->
		if (AuthorityRUP.count() == 0) {
			new AuthorityRUP(grailsApplication.config.rup.authority.rupoid, grailsApplication.config.rup.authority.name ).save(flush: true, failOnError: true)
		}
		def testAuthorityOID = "1.2.840.114350.1.13.99998.8734"
		def testAuthorityName = "Good Health Clinic"
		new HealthEntity(testAuthorityOID, testAuthorityName).save(flush: true, failOnError: true)
		
		def country = Country.findOrCreateWhere(name: "AR").save(failOnError: true, flush: true)
		def province = Province.findOrCreateWhere(country: country, name: "AR-S").save(failOnError: true, flush: true)
		City.findOrCreateWhere(province: province, name: "Venado Tuerto").save(failOnError: true, flush: true)
    }
    def destroy = {
    }
}
