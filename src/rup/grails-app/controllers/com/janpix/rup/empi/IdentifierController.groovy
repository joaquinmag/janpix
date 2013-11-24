package com.janpix.rup.empi

class IdentifierController {

    def index() { }
	
	def listPatientIdentifiers(){
		def identifiers = new HashSet<Identifier>()
		String patientName
		
		Patient patient = Patient.get(params.id);
		if(patient) {
			identifiers = patient.identifiers.findAll{i -> i.type == Identifier.TYPE_IDENTIFIER_PI};
			patientName = patient.givenName?.toString()
		}
		
		render(view:"list",model:[patientName:patientName,identifierInstanceList:identifiers,identifierInstanceTotal:identifiers.count])
	}
}
