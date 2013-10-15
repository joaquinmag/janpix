import com.janpix.regdoc.infrastructure.ClinicalDocumentAssembler
import com.janpix.regdoc.infrastructure.FileAttributesAssembler
import com.janpix.regdoc.infrastructure.AuthorAssembler

// Place your Spring DSL code here
beans = {

	authorAssembler(AuthorAssembler) {
	}

	fileAttributesAssembler(FileAttributesAssembler) {
	}
	
	clinicalDocumentAssembler(ClinicalDocumentAssembler) {
		authorAssembler = ref(authorAssembler)
		fileAttributesAssembler = ref(fileAttributesAssembler)
	}

}
