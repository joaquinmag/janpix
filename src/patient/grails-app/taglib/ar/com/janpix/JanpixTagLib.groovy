package ar.com.janpix

import ar.com.janpix.patient.PatientCommand

class JanpixTagLib {
    static defaultEncodeAs = 'html'
    //static encodeAsForTags = [tagName: 'raw']
	
	static namespace = "jx"
	
	def securityService
	
	def currentUser = {attrs,body->
		PatientCommand user = securityService.currentUser
		
		if(user){
			out << user.firstname
		}
	}
}
