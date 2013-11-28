import grails.util.Environment

import com.janpix.repodoc.RegistroService
import com.janpix.repodoc.RepositorioService
import com.janpix.repodoc.stubs.StubRegistroService

// Place your Spring DSL code here
beans = {
	
	if(Environment.current == Environment.TEST)
	{
		registroService(StubRegistroService)
	}
	else
	{
		registroService(RegistroService) {
			janpixRegdocServiceClient = ref('janpixRegdocServiceClient')
		}
	}
	
	
	
	repositorioService(RepositorioService) {
		registroService = ref(registroService)
	}
}
