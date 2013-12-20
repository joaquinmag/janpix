package ar.com.healthentity

class ModelAppenderFilters {
	
	def springSecurityService
	def grailsApplication
	
	def filters = {
		
		final String anonymousControllers = '^(login|logout)$'
		
		all(controller: '*', action: '*', controllerExclude: anonymousControllers) {
			after = { Map model ->
				// el modelo podria ser null en caso de que haya un redirect
				if (model) {
					if (!model.ctx) model.ctx = [:]
					final User user = springSecurityService.currentUser
					model.ctx.user = user
					model.ctx.healthentityname = grailsApplication.config.healthEntity.name
				}
			}
		}
	}
}
