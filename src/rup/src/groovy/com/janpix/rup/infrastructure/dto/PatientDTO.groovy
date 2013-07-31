package com.janpix.rup.infrastructure.dto


import com.janpix.rup.infrastructure.Mapper

class PatientDTO extends PersonDTO {
	String uniqueId
	def convert(Mapper mapper){
		mapper.convert(this);
	}

}
