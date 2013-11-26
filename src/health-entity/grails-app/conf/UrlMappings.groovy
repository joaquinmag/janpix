class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }
		
		name dashboard:"/"(controller:'patient', action: 'index')
		name patients:"/patients"(controller:'patient', action: 'index')
		
		name showPatient: "/patient/$id" (controller: 'patient', action: 'show')
		//name createDocument: "/documentcreate" (controller: 'study', action: 'create')
		
        "500"(view:'/error')
	}
}
