package com.janpix.regdoc.services

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.ParameterStyle
import javax.xml.ws.soap.MTOM

import org.apache.commons.lang.NotImplementedException
import org.apache.cxf.annotations.WSDLDocumentation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.regdoc.domain.ClinicalDocument;
import com.janpix.servidordocumentos.dto.message.*

@MTOM(enabled = true)
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)
class RegisterJanpixService {

	static transactional = false

	def registerService

	@WebMethod
	public ACKMessage registerDocument(@WebParam(name = "registerDocumentRequestMessage") RegisterDocumentRequest registerDocumentRequestMessage) {
		registerService.registerDocument(registerDocumentRequestMessage)
	}
	
	@WebMethod
	public ACKMessage queryDocument(@WebParam(name = "queryDocumentRequestMessage") QueryDocumentRequest queryDocumentRequestMessage) {
		queryService.queryDocument(queryDocumentRequestMessage)
	}

}
