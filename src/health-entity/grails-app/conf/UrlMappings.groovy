class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }
		
		name dashboard:"/"(controller:'patient', action: 'index')
		name patients:"/patients"(controller:'patient', action: 'index')
		
		name study:"/study/$id(.${format})?"(controller:'study') {
			action = [GET: "show", POST: "save"]
		}
		
        "500"(view:'/error')
	}
}
