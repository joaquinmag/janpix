import com.janpix.rup.empi.AuthorityRUP;

class BootStrap {

	def grailsApplication
	
    def init = { servletContext ->
		if (AuthorityRUP.count() == 0) {
			new AuthorityRUP(oid: grailsApplication.config.rup.authority.rupoid , name: grailsApplication.config.rup.authority.name ).save(failOnError: true)
		}
    }
    def destroy = {
    }
}
