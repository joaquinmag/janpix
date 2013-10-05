package com.janpix.repodoc

import grails.transaction.Transactional

import com.janpix.repodoc.assemblers.ClinicalDocumentAssembler
import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.FileUtils;
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO

@Transactional
class RepositorioService {
	//TODO ver transacionalidad de las cosas
	//static transactional = false;
	/**
	 * Ingresa un documento en el repositorio y lo registra en el registro
	 * @return
	 */
    def provideAndRegisterDocument(ClinicalDocumentDTO documentDTO) {
		//try{
			String hash = FileUtils.calculateSHA1(documentDTO.binaryData)
			// Busco el documento. Si existe y tiene el mismo HASH no hago nada
			// TODO hacer
			
			// Ingreso el documento
			ClinicalDocument document = ClinicalDocumentAssembler.fromDTO(documentDTO)
			document.hash = hash
			document.size = documentDTO.binaryData.size()
			
			document.save(failOnError:true,flush:true)
			
			// Registro el documento en el Registro
			this.registerDocument(document)
	/*	}
		catch (Exception e) {
			//tx.setRollbackOnly()
			log.error("Exception : ${e.message}", e)
			//return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
			throw e
		}*/
		
    }
	
	/**
	 * Retorna el documento que contenga el uuid pasado
	 * @param uniqueId
	 * @return
	 */
	ClinicalDocumentDTO retrieveDocumentByUUID(String uuid){
		// Se obtiene el documento
		ClinicalDocument document = ClinicalDocument.findByUuid(uuid)
		
		// Se transforma el documento
		return ClinicalDocumentAssembler.toDTO(document)
	}
	
	/**
	 * Registra un documento en el registro de documentos
	 * @param document
	 */
	private def registerDocument(ClinicalDocument document){
		
	}
}

