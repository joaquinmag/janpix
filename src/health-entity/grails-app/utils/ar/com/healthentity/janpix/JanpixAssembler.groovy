package ar.com.healthentity.janpix

import org.apache.commons.io.IOUtils

import ar.com.healthentity.ClinicalDocument

import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

/**
 * Clase encargada de transformar entidades de Janpix
 * a entidades de Health Entity
 * @author martin
 *
 */
class JanpixAssembler {

	/**
	 * Transforma un Documento de janpix en un Documento de healthentity
	 * @return
	 */
	static ClinicalDocument fromDocument(ClinicalDocumentDTO janpixDocument){
		if(janpixDocument == null)
			return null
			
		ClinicalDocument document = new ClinicalDocument()
		document.name = janpixDocument.fileAttributes.filename
		document.mimeType = janpixDocument.fileAttributes.mimeType
		document.size = janpixDocument.fileAttributes.size
		document.binaryData = IOUtils.toByteArray(janpixDocument.binaryData.getInputStream());
		
		return document
	} 
}
