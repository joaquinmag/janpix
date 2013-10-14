class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
	
	// Esto es para que funcione MTOM con WS SOAP
	static excludes = ['/services*']
}
