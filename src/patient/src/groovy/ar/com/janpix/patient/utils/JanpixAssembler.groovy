package ar.com.janpix.patient.utils

import groovy.util.ConfigObject;
import ar.com.janpix.patient.AuthorCommand
import ar.com.janpix.patient.HealthEntityCommand
import ar.com.janpix.patient.StudyCommand
import ar.com.janpix.patient.StudyState
import com.janpix.webclient.regdoc.AuthorDTO
import com.janpix.webclient.regdoc.ClinicalDocumentDTO
import com.janpix.webclient.regdoc.HealthEntityDTO

class JanpixAssembler {
	
	private static String DOCUMENT_STATE_SUBMITTED = "Submitted"
	private static String DOCUMENT_STATE_APPROVED = "Approved"
	static HealthEntityDTO toHealthEntity(ConfigObject parameters){
		if(!parameters)
			return null
		
		HealthEntityDTO dto = new HealthEntityDTO()
		dto.name = parameters.name
		dto.oid = parameters.oid
	
		return dto
	}
	
	/** Froms **/
	static StudyCommand fromDocument(ClinicalDocumentDTO document){
		if(!document)
			return null
		
		StudyCommand study = new StudyCommand()
		study.author = JanpixAssembler.fromAuthor(document.author)
		study.uniqueId = document.uniqueId
		study.name = document.name
		study.state = JanpixAssembler.fromDocumentState(document.stateDescription)
		study.type = document.typeName
		study.comments = document.comments
		study.date = document.documentCreationStarted
		
		return study
	}
	
	static AuthorCommand fromAuthor(AuthorDTO authorDTO){
		if(!authorDTO)
			return null
		
		AuthorCommand author = new AuthorCommand()
		author.healthEntity = new HealthEntityCommand()
		author.healthEntity.name = authorDTO.healthEntity?.name
		author.healthEntity.oid = authorDTO.healthEntity?.oid
		
		return author
	}
	
	static StudyState fromDocumentState(String state){
		if(state == DOCUMENT_STATE_SUBMITTED){
			return StudyState.Pendiente
		} else if(state == DOCUMENT_STATE_APPROVED) {
			return StudyState.Aprobado
		}
	}
	
	
}
