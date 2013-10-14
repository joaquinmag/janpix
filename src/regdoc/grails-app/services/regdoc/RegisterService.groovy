package regdoc

import com.janpix.regdoc.domain.ClinicalDocument;
import com.janpix.servidordocumentos.dto.message.*
import com.janpix.servidordocumentos.dto.*
import com.janpix.regdoc.infrastructure.*
import grails.transaction.Transactional

@Transactional
class RegisterService {

	def clinicalDocumentAssembler

	public ACKMessage registerDocument(RegisterDocumentRequest registerDocumentRequestMessage) {
		try {
			def clinicalDocDTO = registerDocumentRequestMessage.clinicalDocument
			def clinicalDoc = clinicalDocumentAssembler.fromDTO(clinicalDocDTO)
			validateClinicalDocument(clinicalDoc, clinicalDocDTO)
			clinicalDoc.save()
			return new ACKMessage(typeCode: TypeCode.SuccededRegistration, clinicalDocument: clinicalDocDTO)
		}
		catch (Exception e) {
			return new ACKMessage(typeCode: TypeCode.RegistrationError, text:e.message, clinicalDocument: clinicalDocDTO)
		}
	}

	private void validateClinicalDocument(ClinicalDocument document, ClinicalDocumentDTO dto) {
		// TODO
	}

}
