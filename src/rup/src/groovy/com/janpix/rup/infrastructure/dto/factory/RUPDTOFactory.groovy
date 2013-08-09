package com.janpix.rup.infrastructure.dto.factory

import com.janpix.rup.infrastructure.dto.AssigningAuthorityDTO;

class RUPDTOFactory {
	def grailsApplication
	
	AssigningAuthorityDTO buildRUPDTO() {
		return new AssigningAuthorityDTO(grailsApplication.config.rup.authority.rupoid, grailsApplication.config.rup.authority.name)
	}
}
