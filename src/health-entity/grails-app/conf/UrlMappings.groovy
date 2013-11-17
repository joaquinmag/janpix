class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }
		
		name dashboard:"/"(controller:'patient', action: 'index')
		name patients:"/patients"(controller:'patient', action: 'index')
        "500"(view:'/error')
	}
}
