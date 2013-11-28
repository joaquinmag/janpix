class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }
		
		name dashboard:"/"(controller:'patient', action: 'index')
		name patients:"/patients"(controller:'patient', action: 'index')
		name showPatient: "/patient/show/$id" (controller: 'patient', action: 'show') {
			constraints { id(matches:/\d+/) }
		}
		name createDocument: "/study/create" (controller: 'study', action: 'create')
		name showDocuments: "/patient/showDocuments/$id"(controller:'patient', action:'showDocuments') {
			constraints { id(matches:/\d+/) }
		}

        "500"(view:'/error')
	}
}
