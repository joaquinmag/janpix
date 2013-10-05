package com.janpix.repodoc.domain
import grails.converters.*

class ClinicalDocumentController {

	static scaffold = true
	
    def index() {redirect(action:"list") }
	
	def list(){
		//ClinicalDocument.useDatabase("janpix_repodoc")
		List<ClinicalDocument> documents = ClinicalDocument.list()
		
		//render "hola:"+documents.size().toString()
		render documents as JSON
	}
}
