package com.janpix.servidordocumentos.dto.message

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO;
import com.janpix.servidordocumentos.dto.HealthEntityDTO;
import com.janpix.servidordocumentos.dto.SearchCriteriaDTO;

@XmlRootElement
class QueryDocumentRequest {
	
	@XmlElement(required=true)
	HealthEntityDTO healthEntityFinder // entidad que busca el documento
	
	@XmlElementWrapper(name="criterias")
	@XmlElement(name="criteria", required=true)
	List<SearchCriteriaDTO> criterias
	
	QueryDocumentRequest() {
		criterias = new ArrayList<SearchCriteriaDTO>()
	}
	
}
