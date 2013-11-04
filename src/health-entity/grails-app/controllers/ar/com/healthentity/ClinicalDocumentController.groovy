package ar.com.healthentity

import com.janpix.exceptions.JanpixException



class ClinicalDocumentController {
	static scaffold = true
	
	def janpixService
	
    def index() {
		render(text: "<xml>some xml</xml>", contentType: "text/xml", encoding: "UTF-8") 
		return 
		}
	
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
