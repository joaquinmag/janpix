package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.Author
import com.janpix.regdoc.domain.HealthEntity
import com.janpix.regdoc.domain.Person
import com.janpix.servidordocumentos.dto.AuthorDTO

class AuthorAssembler {

	static AuthorDTO toDTO(Author author) {
		AuthorDTO dto = new AuthorDTO()
		dto.authorPerson = author.person.name
		dto.authorRole = author.person.role
		dto.authorSpecialty = author.person.specialty
		dto.healthEntity = HealthEntityAssembler.toDTO(author.institution)
		dto
	}

	static Author fromDTO(AuthorDTO authorDto, HealthEntity he, Person person) {
		def author = new Author()
		author.person = person
		author.institution = he
		author
	}
	
	static Person personFromDTO(AuthorDTO authorDto) {
		def person = new Person()
		person.name = authorDto.authorPerson
		person.specialty = authorDto.authorSpecialty
		person.role = authorDto.authorRole
		person
	}

}
