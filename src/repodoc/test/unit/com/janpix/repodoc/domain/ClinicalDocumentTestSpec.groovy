package com.janpix.repodoc.domain

import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock(ClinicalDocument)
class ClinicalDocumentTestSpec extends Specification {
	
	ClinicalDocument clinicalDocument
	private static String PATH_RESOURCES = "test/resources/files/"
	
    def setup() {
		this.clinicalDocument = new ClinicalDocument()
    }

    def cleanup() {
    }

    void "test serialize and deserialize clinical document"() {
		when:
			//String nameFile = "IHE_ITI_TF_Vol1.pdf"
			String nameFile = "archivo1.txt"
			byte [] byteArray = new File(PATH_RESOURCES+nameFile).bytes
			clinicalDocument.name = nameFile
			clinicalDocument.uuid="unUniqueId"
			clinicalDocument.binaryData = byteArray
			clinicalDocument.mimeType = "application/pdf"
			clinicalDocument.save()
			
			String text = new ByteArrayInputStream( clinicalDocument.binaryData ).getText()
			
		then:
			clinicalDocument.name == nameFile
			clinicalDocument.uuid == "unUniqueId"
			clinicalDocument.binaryData.size() == byteArray.size()
			clinicalDocument.mimeType == "application/pdf"
			
			text == new File(PATH_RESOURCES+nameFile).getText()
    }

}
