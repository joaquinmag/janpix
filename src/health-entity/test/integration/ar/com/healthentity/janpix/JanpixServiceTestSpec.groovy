package ar.com.healthentity.janpix

import spock.lang.*
import ar.com.healthentity.ClinicalDocument
import ar.com.healthentity.Patient
import ar.com.healthentity.Study

/**
 * Testea el correcto funcionamiento del Servicio que aloja los documentos
 */
class JanpixServiceTestSpec extends Specification {
	
	
	def janpixService
	
    def setup() {
    }

    def cleanup() {

    }
	
	
	// Tengo que levantar el WS del regDoc para probarlo
	 @Ignore
	void "test query patient studies on RegDoc"() {
		given:
			Patient patient = this.buildPatient()
		when:
			List<Study> studies = janpixService.queryAllStudies(patient)
		then:
			studies.size() > 0
	}
	
	// Tengo que levantar el WS del repoDoc para probarlo
	// @Ignore
	void "test get document from repository"(){
		given:
			// Este uuid lo tengo en mi base local (Martín)
			String uniqueId = "52a1e6d284ae3963c40e2287"
		when:
			ClinicalDocument document = janpixService.getDocumentByUniqueId(uniqueId)
		then:
			document != null
			document.filename == "Imagen254.jpg"
			document.mimeType == "image/jpeg"
			document.size == 34292
	}

    
	
	// Crea un document y lo guarda en la base
	private Patient buildPatient(){
		Patient patient = Patient.get(1)
		return patient
	}

}
