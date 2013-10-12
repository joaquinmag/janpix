package com.janpix.repodoc

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.soap.SOAPBinding
import javax.jws.soap.SOAPBinding.ParameterStyle

import org.apache.cxf.annotations.WSDLDocumentation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint

import com.janpix.servidordocumentos.dto.message.*;

@SOAPBinding(parameterStyle=ParameterStyle.BARE)
@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)

class RepositorioJanpixService {
	
	static transactional = false
	
	def repositorioService
	
    /**
	 * Ingresa un documento en el repositorio y lo registra en el registro
	 * @return
	 */
	@WebMethod
    public ACKMessage provideAndRegisterDocument(
		@WebParam(name = "provideAndRegisterDocumentMessage")
		ProvideAndRegisterDocumentRequest retriveRequest) 
	{
		return repositorioService.provideAndRegisterDocument(retriveRequest.clinicalDocument);
	}
	
	/**
	 * Retorna el documento que se pide por parametro
	 * @param retriveRequest
	 * @return
	 */
	@WebMethod
	public ACKMessage retriveDocument(
		@WebParam(name = "retriveDocumentMessage")
		RetriveDocumentRequest retriveRequest
		)
	{
		return null
	}
}
