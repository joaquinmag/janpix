package com.janpix.repodoc



import spock.lang.*

import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.FileUtils

/**
 * Testea el correcto funcionamiento del Servicio que aloja los documentos
 */
class RepositorioServiceTestSpec extends Specification {
	
	private static String PATH_RESOURCES = "test/resources/files/"
	def repositorioService
	
    def setup() {
    }

    def cleanup() {
    }

    void "test retrieve document already exists in repository"() {
		when:
			String uuid = "urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1"
			String mimeType = "text/plain"
			String nameFile = "archivo1.txt"
			this.createAndSaveNewDocument(uuid,mimeType,nameFile)
		
		then:
			ClinicalDocumentDTO document = repositorioService.retrieveDocumentByUUID(uuid)
			
			document.name == nameFile
			document.fileAttributes.uuid == uuid
			document.fileAttributes.mimeType == mimeType
			
			document.fileAttributes.size == new File(PATH_RESOURCES+nameFile).bytes.size()
			document.binaryData == new File(PATH_RESOURCES+nameFile).bytes
			document.fileAttributes.fileHash == FileUtils.calculateSHA1(document.binaryData);
    }
	
	/*void "test provide document and seek"(){
		// TODO Guardar un documento y luego obtenerlo para compararlos
	}
	
	void "test register document on Document Register"(){
		// TODO probar un mock del grabado de los datos necesarios en el registro de documentos
	}*/
	
	private void createAndSaveNewDocument(String uniqueId,String mimeType,String nameFile){
		
		ClinicalDocument clinicalDocument = new ClinicalDocument();
		byte [] byteArray = new File(PATH_RESOURCES+nameFile).bytes
		
		clinicalDocument.name = nameFile
		clinicalDocument.uuid = uniqueId
		clinicalDocument.binaryData = byteArray
		clinicalDocument.mimeType = mimeType
		clinicalDocument.hash = FileUtils.calculateSHA1(byteArray);
		clinicalDocument.size = byteArray.size()
		clinicalDocument.save(failOnError:true)
	}
}
