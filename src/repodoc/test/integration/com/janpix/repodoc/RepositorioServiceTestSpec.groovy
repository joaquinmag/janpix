package com.janpix.repodoc



import spock.lang.*

import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.FileUtils
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.FileAttributesDTO

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
			
			document != null
			document.name == nameFile
			document.fileAttributes.uuid == uuid
			document.fileAttributes.mimeType == mimeType
			
			document.fileAttributes.size == new File(PATH_RESOURCES+nameFile).bytes.size()
			document.binaryData == new File(PATH_RESOURCES+nameFile).bytes
			document.fileAttributes.fileHash == FileUtils.calculateSHA1(document.binaryData);
    }
	
	void "test provide document and seek"() {
		when:
			// Creo un documento y lo mando a guardar con el servicio
			ClinicalDocumentDTO document = this.buildDocumentDTO()
			repositorioService.provideAndRegisterDocument(document)
			
		then:
			//Obtengo el documento creado y lo comparo
			ClinicalDocumentDTO retriveDocument = repositorioService.retrieveDocumentByUUID(document.fileAttributes.uuid)
			
			document != null
			//retriveDocument.uniqueId != null
			retriveDocument.name == document.name
			retriveDocument.fileAttributes.uuid == document.fileAttributes.uuid
			retriveDocument.fileAttributes.size == document.fileAttributes.size
			retriveDocument.binaryData == document.binaryData
			retriveDocument.fileAttributes.fileHash == document.fileAttributes.fileHash
			retriveDocument.fileAttributes.mimeType == document.fileAttributes.mimeType
	}
	
	void "test register document on Document Register"() {
		// TODO probar un mock del grabado de los datos necesarios en el registro de documentos
	}
	
	// Crear el DTO de un documento
	private ClinicalDocumentDTO buildDocumentDTO(){
		ClinicalDocumentDTO dto = new ClinicalDocumentDTO()
		dto.fileAttributes = new FileAttributesDTO()
		dto.name = "archivo.odt"
		dto.fileAttributes.mimeType = "application/vnd.oasis.opendocument.text"
		dto.fileAttributes.uuid = "uuid:urn:1234567891011"
		dto.binaryData = new File(PATH_RESOURCES+dto.name).bytes
		dto.fileAttributes.size = dto.binaryData.size()
		dto.fileAttributes.fileHash = FileUtils.calculateSHA1(dto.binaryData)
		
		return dto
	}
	
	// Crea un document y lo guarda en la base
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
