import com.janpix.rup.empi.AuthorityRUP;

class BootStrap {

	def grailsApplication
	
    def init = { servletContext ->
		if (AuthorityRUP.count() == 0) {
			new AuthorityRUP(grailsApplication.config.rup.authority.rupoid, grailsApplication.config.rup.authority.name ).save(failOnError: true)
		}
    }
    def destroy = {
    }
}
