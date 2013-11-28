package com.janpix.repodoc



import spock.lang.*

import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.repodoc.exceptions.BussinessRuleException
import com.janpix.servidordocumentos.FileUtils
import com.janpix.servidordocumentos.dto.AuthorDTO
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.FileAttributesDTO
import com.janpix.servidordocumentos.dto.HealthEntityDTO
import com.janpix.servidordocumentos.dto.message.ACKMessage
import com.janpix.webclient.repodoc.RepositorioJanpixServicePortType

/**
 * Testea el correcto funcionamiento del Servicio que aloja los documentos
 */
class RegistroServiceTestSpec extends Specification {
	
	private static String PATH_RESOURCES = "test/resources/files/"
	private static String FILE_NAME = "archivo1.txt"
	
	def registroService
	RepositorioJanpixServicePortType janpixRepodocServiceClient
	
    def setup() {
    }

    def cleanup() {

    }
	
	
	// Tengo que levantar el WS del regDoc para probarlo
	// y modificar el resources para que no use el mock
	 @Ignore
	void "test register document on Document Register"() {
		given:
			ClinicalDocumentDTO documentDTO = this.buildDocumentDTO()
		when:
			ACKMessage ack = registroService.registerDocument(documentDTO)
		then:
			notThrown BussinessRuleException
		
	}

    
	
	// Crea un document y lo guarda en la base
	private ClinicalDocument createAndSaveNewDocument(String uniqueId,String mimeType,String nameFile){
		
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
		
		return clinicalDocument
	}
	
	private ClinicalDocumentDTO buildDocumentDTO(){
		ClinicalDocumentDTO dto = new ClinicalDocumentDTO()
		dto.uniqueId = UUID.randomUUID().toString()
		
		dto.fileAttributes = new FileAttributesDTO()
		dto.name = "Un nombre de titulo"
		dto.fileAttributes.filename = FILE_NAME
		dto.fileAttributes.mimeType = "application/vnd.oasis.opendocument.text"
		dto.fileAttributes.uuid = UUID.randomUUID().toString()
		dto.fileAttributes.repositoryId = "IdRepoDoc"
		dto.fileAttributes.creationTime = new Date()
		dto.binaryData = FileUtils.ByteArrayToDataHandler(new File(PATH_RESOURCES+FILE_NAME).bytes, "application/octet-stream")
		dto.fileAttributes.size = new File(PATH_RESOURCES+FILE_NAME).bytes.size()
		dto.fileAttributes.fileHash = FileUtils.calculateSHA1(new File(PATH_RESOURCES+FILE_NAME).bytes)
		dto.documentCreationStarted = new Date()
		dto.documentCreationEnded = new Date()
		
		dto.patientId = UUID.randomUUID().toString()
		dto.author = this.buildAuthorDTO()
		dto.comments = "Sin comentarios"
		dto.language = "es-AR"
		dto.typeId = 2
		dto.typeName = "Laboratorio"
		dto.formatName = "ODT"
		
		return dto
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
