package com.janpix.repodoc

import grails.transaction.Transactional

import com.janpix.repodoc.assemblers.ClinicalDocumentAssembler
import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.servidordocumentos.FileUtils;
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.message.*;
import com.janpix.servidordocumentos.dto.message.ACKMessage.TypeCode;

@Transactional
class RepositorioService {
	//TODO ver transacionalidad de las cosas
	//static transactional = false;
	/**
	 * Ingresa un documento en el repositorio y lo registra en el registro
	 * @return
	 */
    def provideAndRegisterDocument(ClinicalDocumentDTO documentDTO) {
		try{
			log.debug("Iniciando ProvideAndRegisterDocument...")
			
			log.debug("Convirtiendo DTO...")
			// Ingreso el documento
			ClinicalDocument document = ClinicalDocumentAssembler.fromDTO(documentDTO)
			
			log.debug("Realizando validaciones...")
			String hash = FileUtils.calculateSHA1(document.binaryData)
			
			validateHash(hash,document) 
			validateSize(document) 
		
			
			// Busco el documento. Si existe y tiene el mismo HASH no hago nada
			// TODO hacer
			
			// Se vuelve a setear Hash y size por si el cliente no los envio
			document.hash = hash
			document.size = document.binaryData?.size()
			
			log.debug("Grabando documento...")
			document.save(failOnError:true,flush:true)
			
			// Registro el documento en el Registro
			log.debug("Registrando documento...")
			this.registerDocument(document)
			
			log.debug("ProvideAndRegisterDocument finalizada correctamente!")
			return new ACKMessage(typeCode:TypeCode.SuccededInsertion, clinicalDocument:ClinicalDocumentAssembler.toDTO(document))
		}
		catch (Exception e) { //TODO definir Exception propia
			//tx.setRollbackOnly()
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
		}
		
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
	
	/**
	 * Valida que el hash enviado sea igual al calculado
	 * @param calculatedHash
	 * @param documentDTO
	 * @return
	 */
	private def validateHash(String calculatedHash, ClinicalDocument documentDTO){
		// TODO hacer
	}
	
	/**
	 * Valida que el size enviado sea igual al calculado
	 * @param documentDTO
	 * @return
	 */
	private def validateSize(document){
	
	}
}

