package ar.com.healthentity

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Years;

import com.janpix.PasswordCantBeTheSameException
import com.janpix.PasswordDoesntMatchException
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Person {
	String firstName
	String lastName

	static constraints = {
		firstName blank:false, nullable:false
		lastName blank:false, nullable:false
	}

	Person(String firstName, String lastName) {
		this.firstName = firstName
		this.lastName = lastName
	}

}

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	boolean validatedEmail = false
	String validationCodeEmail = null
	
	// fecha de creacion
	LocalDateTime dateCreated
	LocalDateTime lastUpdated
	
	LocalDate birthDate

	// datos de este usuario
	Person person

	static constraints = {
		username blank: false, unique: true
		password blank: false
		
		validatedEmail nullable: false
		validationCodeEmail blank:true, nullable: true

		person blank:false, nullable:false

		birthDate blank: true, nullable: true
	}

	static mapping = {
		table 'users'
		password column: '`password`'
	}
	
	static embedded = ['person']
	
	User(String username, Person person) {
		this.person = person
		this.username = username
	}
	
	Years getYears(LocalDate today) {
		if (birthDate) {
			Years.yearsBetween(birthDate, today)
		} else {
			null
		}
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	void changePassword(String actualPass, String newPass, def saltGenerator) {
		if (encodePassword(actualPass) != password) throw new PasswordDoesntMatchException("el password actual no matchea con el almacenado usuario id=${id}")

		// chequeamos que no sea el mismo password
		if (actualPass == newPass) throw new PasswordCantBeTheSameException("el password no puede ser el mismo para id=${id}")

		this.password = encodePassword(newPass)
	}
	
	// equals, hashcode & string
	boolean equals(Object o) {
		if (o == null) false
		else if (o.is(this)) true
		else if (!eqClass(o)) false
		else new EqualsBuilder()
			.append(username, o.username)
			.isEquals()
	}

	int hashCode() {
		new HashCodeBuilder()
			.append(username)
			.toHashCode()
	}

	String toString() {
		"Usuario [id=${id}]"
	}
}
