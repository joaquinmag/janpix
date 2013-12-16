package ar.com.janpix.patient

import grails.transaction.Transactional

@Transactional
class SecurityService {

	// TODO por ahora HardCode
    def getCurrentUser() {
		PatientCommand user = new PatientCommand()
		user.cuis = "8e98ec7a-b06a-4cd7-a19f-76e174acebbe"
		user.user = "mbarnech"
		user.firstname = "Martin"
		user.lastname = "Barnech"
		user.mail = "mgbarnech@gmail.com"
		
		return user
    }
}
