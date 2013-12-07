import com.janpix.regdoc.infrastructure.AuthorAssembler
import com.janpix.regdoc.infrastructure.ClinicalDocumentAssembler
import com.janpix.regdoc.infrastructure.FileAttributesAssembler
import com.janpix.regdoc.infrastructure.HealthEntityAssembler
import com.janpix.regdoc.services.QueryDocumentService

// Place your Spring DSL code here
beans = {

	authorAssembler(AuthorAssembler) {
	}

	fileAttributesAssembler(FileAttributesAssembler) {
	}
	
	healthEntityAssembler(HealthEntityAssembler) {	}
	
	clinicalDocumentAssembler(ClinicalDocumentAssembler) {
		authorAssembler = ref(authorAssembler)
		fileAttributesAssembler = ref(fileAttributesAssembler)
	}
	
	queryDocumentService(QueryDocumentService) {
		clinicalDocumentAssembler = ref(clinicalDocumentAssembler)
	}

}
