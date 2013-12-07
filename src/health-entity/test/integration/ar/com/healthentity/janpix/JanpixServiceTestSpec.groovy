package ar.com.healthentity.janpix

import spock.lang.*

import ar.com.healthentity.Patient
import ar.com.healthentity.Study
import com.janpix.servidordocumentos.dto.AuthorDTO
import com.janpix.servidordocumentos.dto.ClinicalDocumentDTO
import com.janpix.servidordocumentos.dto.FileAttributesDTO
import com.janpix.servidordocumentos.dto.HealthEntityDTO
import com.janpix.servidordocumentos.dto.message.ACKMessage
import com.janpix.webclient.repodoc.RepositorioJanpixServicePortType

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
	// @Ignore
	void "test query patient studies on RegDoc"() {
		given:
			Patient patient = this.buildPatient()
		when:
			List<Study> studies = janpixService.queryAllStudies(patient)
		then:
			studies.size() > 0
	}

    
	
	// Crea un document y lo guarda en la base
	private Patient buildPatient(){
		Patient patient = Patient.get(1)
		return patient
	}

}
