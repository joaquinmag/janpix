package com.janpix.rup.infrastructure.dto


import com.janpix.rup.infrastructure.Mapper

class PatientDTO extends PersonDTO {
	String uniqueId
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
	
	
	PersonDTO toPersonDTO() {
		PersonDTO personDTO = new PersonDTO()
		copyProperties(this, personDTO)
		return personDTO
	}

	def copyProperties(source, target) {
		source.properties.each { key, value ->
			if (target.hasProperty(key) && !(key in ['class', 'metaClass'])) {
				target[key] = value
			}
		}
	}
}
