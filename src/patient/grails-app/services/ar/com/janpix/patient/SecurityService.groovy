package ar.com.janpix.patient

import grails.transaction.Transactional

@Transactional
class SecurityService {

	// TODO por ahora HardCode
    def getCurrentUser() {
		PatientCommand user = new PatientCommand()
		user.cuis = "3410ea1d-9f5b-4485-8001-e3c4de3687ee"
		user.user = "mbarnech"
		user.firstname = "Martin"
		user.lastname = "Barnech"
		user.mail = "mgbarnech@gmail.com"
		
		return user
    }
}
