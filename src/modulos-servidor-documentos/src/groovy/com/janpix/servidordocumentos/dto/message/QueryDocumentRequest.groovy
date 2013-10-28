package com.janpix.servidordocumentos.dto.message

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO;
import com.janpix.servidordocumentos.dto.DateCreationCriteriaDTO;
import com.janpix.servidordocumentos.dto.HealthEntityDTO;
import com.janpix.servidordocumentos.dto.TitleCriteriaDTO;

@XmlRootElement
class QueryDocumentRequest {
	
	@XmlElement(required=true)
	HealthEntityDTO healthEntityFinder // entidad que busca el documento
	
	@XmlElement(required=false)
	TitleCriteriaDTO titleCriteria
	
	@XmlElement(required=false)
	DateCreationCriteriaDTO dateCreationCriteria
	
	@XmlElement(required=true)
	Long patientId
	
}
