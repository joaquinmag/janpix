package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.Author
import com.janpix.servidordocumentos.dto.AuthorDTO

class AuthorAssembler {

	static AuthorDTO toDTO(Author author) {
		AuthorDTO dto = new AuthorDTO()
		dto.oidHealthEntity = author.institution.oid
		dto.authorPerson = author.person.name
		dto.authorRole = author.person.role
		dto.authorSpecialty = author.person.specialty
		dto
	}

	static Author fromDTO(AuthorDTO authorDto) {
		
	}

}
