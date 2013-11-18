package ar.com.healthentity

import com.janpix.exceptions.JanpixException
import grails.plugins.springsecurity.Secured

@Secured("hasRole('HealthWorker')")
class ClinicalDocumentController {
	def janpixService
	
	
	def downloadDocument(String uuid){
		try{
			if(uuid != null){
				ClinicalDocument document = janpixService.getDocumentByUUID(uuid)
				if(document){
					String nameDocument = document.name
					String mimeType = document.mimeType
					response.setContentLength((int)document.size)
					response.addHeader("Content-disposition", "attachment; filename=${nameDocument}")
					response.addHeader("Content-type", "${mimeType}")
					
					OutputStream out = response.getOutputStream()
					out.write(document.binaryData)
					out.close()
				}
			}
			
			// TODO enviar a una pagina de error
		}
		catch(JanpixException e){
			render "Error de conexión"
		}
	}
}
