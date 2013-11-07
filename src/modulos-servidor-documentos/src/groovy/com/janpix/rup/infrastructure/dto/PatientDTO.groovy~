package com.janpix.rup.infrastructure.dto
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.janpix.rup.infrastructure.Mapper

@XmlRootElement
class PatientDTO extends PersonDTO {
	@XmlElement(required = true)
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
