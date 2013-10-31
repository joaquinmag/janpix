package ar.com.healthentity

import org.apache.commons.io.IOUtils

import com.janpix.JanpixException
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

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
				ClinicalDocumentDTO document = janpixService.getDocumentByUUID(uuid)
				if(document){
					OutputStream out = response.getOutputStream()
					byte[] bytes = IOUtils.toByteArray(document.binaryData.getInputStream());
					
					String nameDocument = document.fileAttributes.filename
					String mimeType = document.fileAttributes.mimeType
					response.setContentLength((int)document.fileAttributes.size)
					response.addHeader("Content-disposition", "attachment; filename=${nameDocument}")
					response.addHeader("Content-type", "${mimeType}")
					
					out.write(bytes)
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
