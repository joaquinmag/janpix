import grails.util.Environment

import com.janpix.repodoc.RepositorioJanpixService
import com.janpix.repodoc.RepositorioService
import com.janpix.repodoc.stubs.StubRegistroService

// Place your Spring DSL code here
beans = {
	
	// Falla la inyeccion de janpixRegdocServiceClient por eso se comenta todo
	
	/*if(Environment.current == Environment.TEST)
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
		registroService = ref('registroService')
	}
	
	repositorioJanpixService(RepositorioJanpixService){
		repositorioService = ref(repositorioService)
	}*/
}
