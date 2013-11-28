package com.janpix.repodoc

import grails.transaction.Transactional
import grails.validation.ValidationException

import org.bson.types.ObjectId

import com.janpix.repodoc.assemblers.ClinicalDocumentAssembler
import com.janpix.repodoc.domain.ClinicalDocument
import com.janpix.repodoc.exceptions.BussinessRuleException
import com.janpix.repodoc.exceptions.MetadataException
import com.janpix.repodoc.exceptions.RegistrationServiceException
import com.janpix.servidordocumentos.FileUtils
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.message.*
import com.janpix.servidordocumentos.dto.message.ACKMessage.TypeCode

class RepositorioService {
	
	def registroService
	
	/**
	 * Ingresa un documento en el repositorio y lo registra en el registro
	 * @return
	 */
    def provideAndRegisterDocument(ClinicalDocumentDTO documentDTO) {
		ClinicalDocument.withTransaction { tx->
		try{
			log.debug("Iniciando ProvideAndRegisterDocument...")
			
			log.debug("Convirtiendo DTO...")
			// Ingreso el documento
			ClinicalDocument document = ClinicalDocumentAssembler.fromDTO(documentDTO)
			
			log.debug("Realizando validaciones...")
			String hash = FileUtils.calculateSHA1(document.binaryData)
			
			validateHash(hash,document) 
			validateSize(documentDTO?.fileAttributes?.size,document?.size) 
		
			
			// Busco el documento. Si existe y tiene el mismo HASH no hago nada
			// TODO hacer
			
			// Se vuelve a setear Hash y size por si el cliente no los envio
			document.hash = hash
			document.size = document.binaryData?.size()
			
			log.debug("Grabando documento...")
			document.save(failOnError:true)
			
			// Registro el documento en el Registro
			log.debug("Registrando documento...")
			this.updateDocumentDTO(documentDTO,document)
			registroService.registerDocument(documentDTO)
			
			log.debug("ProvideAndRegisterDocument finalizada correctamente!")
			return new ACKMessage(typeCode:TypeCode.SuccededInsertion)
		}
		catch(ValidationException ve) {
			tx.setRollbackOnly()
			log.error("Validation Exception : ${ve.message}", ve)
			return new ACKMessage(typeCode:TypeCode.ValidationError, text: ve.message)
		}
		catch(RegistrationServiceException rse) {
			tx.setRollbackOnly()
			log.error("RegistrationService Exception : ${rse.message}", rse)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: rse.message)
		}
		catch (MetadataException me) {
			tx.setRollbackOnly()
			log.error("Metadata Exception : ${me.message}", me)
			return new ACKMessage(typeCode:TypeCode.MetadataError, text: me.message)
		}
		catch (BussinessRuleException bre) {
			tx.setRollbackOnly()
			log.error("BussinessRule Exception : ${bre.message}", bre)
			//return new ACKMessage(typeCode:TypeCode.RegistrationError, text: bre.message)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: bre.message)
		}
		catch (Exception e) {
			tx.setRollbackOnly()
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
		}
		}
    }
	
	/**
	 * Retorna el documento que contenga el uuid pasado
	 * @param uniqueId
	 * @return
	 */
	ACKMessage retrieveDocumentByUUID(String uuid){
		try{
			log.debug("Iniciando RetrieveDocument...")
			
			log.debug("Obteniendo documento...")
			ClinicalDocument document = ClinicalDocument.findById(new ObjectId(uuid))
			
			if(document == null){
				log.debug("El documento solicitado no existe")
				return new ACKMessage(typeCode:TypeCode.RetrieveError, text:"El documento solicitado no existe en el repositorio")
			}
				
			log.debug("Armando respuesta...")
			ClinicalDocumentDTO dto = ClinicalDocumentAssembler.toDTO(document)
			
			log.debug("RetrieveDocument finalizado correctamente")
			return new ACKMessage(typeCode:TypeCode.SuccededRetrieve, clinicalDocument:dto)
		}
		catch (BussinessRuleException bre) {
			//tx.setRollbackOnly()
			log.error("BussinessRule Exception : ${bre.message}", bre)
			return new ACKMessage(typeCode:TypeCode.RetrieveError, text: bre.message)
		}
		catch(IllegalArgumentException iae) {
			//tx.setRollbackOnly()
			log.error("IllegalArgument Exception : ${iae.message}", iae)
			return new ACKMessage(typeCode:TypeCode.RetrieveError, text: "El UUID enviado no es valido")
		}
		catch (Exception e) {
			//tx.setRollbackOnly()
			log.error("Exception : ${e.message}", e)
			return new ACKMessage(typeCode:TypeCode.InternalError, text: e.message)
		}
	}
		
	/**
	 * Valida que el hash enviado sea igual al calculado
	 * @param calculatedHash
	 * @param documentDTO
	 * @return
	 */
	private def validateHash(String calculatedHash, ClinicalDocument documentDTO){
		if(documentDTO?.hash != null)
			if(documentDTO.hash != calculatedHash)
				throw new MetadataException("El Hash enviado no coincide con el Hash del documento.")
	}
	
	/**
	 * Valida que el size enviado sea igual al calculado
	 * @param documentDTO
	 * @return
	 */
	private def validateSize(sizeDTO,sizeDomain){
		if(sizeDTO != null)
			if(sizeDTO != sizeDomain)
				throw new MetadataException("El tamaño enviado del archivo no coincide con el tamaño original")
	}
	
	/**
	 * Actualiza el dto en base a los parametros calculados para el documento
	 * @param dto
	 * @param document
	 */
	private void updateDocumentDTO(ClinicalDocumentDTO dto,ClinicalDocument document) {
		dto.uniqueId = document.id.toString()
		dto.documentCreationStarted = document.dateAssigned
		dto.documentCreationEnded = document.dateAssigned
		
		dto.fileAttributes.repositoryId = "PonerUUIDDelRepositorio"
		dto.fileAttributes.filename = document.name
		dto.fileAttributes.uuid = document.uuid
		dto.fileAttributes.mimeType = document.mimeType
		dto.fileAttributes.fileHash = document.hash 
		dto.fileAttributes.size  = document.size
	}
}

