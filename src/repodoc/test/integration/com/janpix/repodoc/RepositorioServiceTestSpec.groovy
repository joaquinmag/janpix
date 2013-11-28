package com.janpix.repodoc



import spock.lang.*

import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.FileUtils
import com.janpix.servidordocumentos.dto.AuthorDTO;
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.FileAttributesDTO
import com.janpix.servidordocumentos.dto.HealthEntityDTO;
import com.janpix.servidordocumentos.dto.message.ACKMessage
import com.janpix.servidordocumentos.dto.message.RetrieveDocumentRequest
import com.janpix.webclient.repodoc.RepositorioJanpixServicePortType

/**
 * Testea el correcto funcionamiento del Servicio que aloja los documentos
 */
class RepositorioServiceTestSpec extends Specification {
	
	private static String PATH_RESOURCES = "test/resources/files/"
	private static String FILE_NAME = "archivo1.txt"
	
	def repositorioService
	RepositorioJanpixServicePortType janpixRepodocServiceClient
	
    def setup() {
    }

    def cleanup() {
		// Se elimina la coleccion
		ClinicalDocument.collection.drop()
    }

    void "test retrieve document already exists in repository"() {
		when:
			String uuid = UUID.randomUUID().toString()
			String mimeType = "text/plain"
			String nameFile = "archivo1.txt"
			String objectId = this.createAndSaveNewDocument(uuid,mimeType,nameFile)
		
		then:
			ACKMessage ack = repositorioService.retrieveDocumentByUUID(objectId)
			ClinicalDocumentDTO document = ack.clinicalDocument 
			
			document != null
			document.fileAttributes.filename == nameFile
			document.fileAttributes.uuid == uuid
			document.fileAttributes.mimeType == mimeType
			
			document.fileAttributes.size == new File(PATH_RESOURCES+nameFile).bytes.size()
			FileUtils.DataHandlerToByteArray(document.binaryData) == new File(PATH_RESOURCES+nameFile).bytes
			//document.fileAttributes.fileHash == FileUtils.calculateSHA1(document.binaryData);
    }
	
	void "test provide document without register"(){
		when:
			// Creo un documento y lo mando a guardar con el servicio
			ClinicalDocumentDTO document = this.buildDocumentDTO()
			ACKMessage ackProvide = repositorioService.provideAndRegisterDocument(document)
		then:
			// Se obtiene el documento de la base y se compara que este bien guardado
			ClinicalDocument findedDocument = ClinicalDocument.findByName(FILE_NAME)
			findedDocument != null
			findedDocument.mimeType == document.fileAttributes.mimeType
			findedDocument.size == document.fileAttributes.size
			findedDocument.uuid == document.fileAttributes.uuid
			findedDocument.name == document.fileAttributes.filename
			findedDocument.binaryData == FileUtils.DataHandlerToByteArray(document.binaryData)
			findedDocument.dateAssigned == document.documentCreationStarted
	}
	
	/**
	 * Testea que se provea y registre correctamente el documento
	 * Tiene que estar levantado el WS y sacado el mock en resources
	 */
	@Ignore
	void "test provide document and register"(){
		given:
			//Creo un documento y lo mando a guardar con el servicio
			ClinicalDocumentDTO document = this.buildDocumentDTO()
		when:
			ACKMessage ackProvide = repositorioService.provideAndRegisterDocument(document)
		then:
			ackProvide.typeCode == ACKMessage.TypeCode.SuccededInsertion
	}
	
	
	
	// Tengo que levantar el WS local para probarlo
	@Ignore
	void "test WS comunication with Repositorio Service"() {
		when:
			RetrieveDocumentRequest requestMessage = new RetrieveDocumentRequest(uuid:"525c633784ae5e0947b36c7e")
			ACKMessage ack =  janpixRepodocServiceClient.retrieveDocument(requestMessage)
		
		then:
			ack.typeCode == ACKMessage.TypeCode.SuccededRetrieve
			ack.clinicalDocument.name == "Examen de prostata"
			ack.clinicalDocument.fileAttributes.mimeType == "application/pdf"
		
	}
	
	// Crear el DTO de un documento
	private ClinicalDocumentDTO buildDocumentDTO(){
		ClinicalDocumentDTO dto = new ClinicalDocumentDTO()
		dto.fileAttributes = new FileAttributesDTO()
		dto.name = "Un nombre de titulo"
		dto.fileAttributes.filename = FILE_NAME 
		dto.fileAttributes.mimeType = "application/vnd.oasis.opendocument.text"
		dto.fileAttributes.uuid = "EntidadSanitaria_"+UUID.randomUUID().toString()
		dto.fileAttributes.creationTime = new Date()
		dto.binaryData = FileUtils.ByteArrayToDataHandler(new File(PATH_RESOURCES+FILE_NAME).bytes, "application/octet-stream")
		dto.fileAttributes.size = new File(PATH_RESOURCES+FILE_NAME).bytes.size()
		dto.fileAttributes.fileHash = FileUtils.calculateSHA1(new File(PATH_RESOURCES+FILE_NAME).bytes)
		dto.documentCreationStarted = new Date()
		
		dto.patientId = UUID.randomUUID().toString()
		dto.author = this.buildAuthorDTO()
		dto.comments = "Sin comentarios"
		dto.language = "es-AR"
		dto.typeId = 2
		dto.typeName = "Laboratorio"
		dto.formatName = "ODT"
		
		return dto
	}
	
	// Crea un document y lo guarda en la base
	private String createAndSaveNewDocument(String uniqueId,String mimeType,String nameFile){
		
		ClinicalDocument clinicalDocument = new ClinicalDocument();
		byte [] byteArray = new File(PATH_RESOURCES+nameFile).bytes
		
		clinicalDocument.name = nameFile
		clinicalDocument.uuid = uniqueId
		clinicalDocument.binaryData = byteArray
		clinicalDocument.dateAssigned = new Date()
		clinicalDocument.mimeType = mimeType
		clinicalDocument.hash = FileUtils.calculateSHA1(byteArray);
		clinicalDocument.size = byteArray.size()
		clinicalDocument.save(failOnError:true,flush:true)
		
		return clinicalDocument.id.toString()
	}
	
	private AuthorDTO buildAuthorDTO(){
		AuthorDTO author = new AuthorDTO()
		author.healthEntity = this.buildHealthEntityDTO()
		author.authorPerson = "Ricardo Ruben"
		author.authorRole = "Ciruja no"
		author.authorSpecialty = "Neurocirujano"
		
		return author
	}
	
	private HealthEntityDTO buildHealthEntityDTO() {
		HealthEntityDTO healthEntity = new HealthEntityDTO()
		healthEntity.healthcareFacilityTypeCode = "1"
		healthEntity.name = "Entidad Sanitaria"
		healthEntity.oid = "1"
		
		return healthEntity
	}
	
}
