package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.HealthEntity
import com.janpix.servidordocumentos.dto.HealthEntityDTO

class HealthEntityAssembler {

	static HealthEntityDTO toDTO(HealthEntity healthEntity) {
		def dto = new HealthEntityDTO()
		dto.oid = healthEntity.oid
		dto.name = healthEntity.name
		dto.healthcareFacilityTypeCode = healthEntity.healthcareFacilityTypeCode
		dto
	}

	static HealthEntity fromDTO(HealthEntityDTO dto) {
		def he = new HealthEntity()
		he.oid = dto.oid
		he.name = dto.name
		he.healthcareFacilityTypeCode = dto.healthcareFacilityTypeCode
		he
	}

}
