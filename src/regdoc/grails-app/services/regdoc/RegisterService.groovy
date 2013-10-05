package regdoc

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.ParameterStyle

import org.apache.commons.lang.NotImplementedException
import org.apache.cxf.annotations.WSDLDocumentation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.regdoc.domain.ClinicalDocument
import grails.transaction.Transactional

@Transactional
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)
class RegisterService {

	@WebMethod
	public void registerDocument(@WebParam(name = "clinicalDocument")ClinicalDocument clinicalDocument) {
		
	}
	
}
