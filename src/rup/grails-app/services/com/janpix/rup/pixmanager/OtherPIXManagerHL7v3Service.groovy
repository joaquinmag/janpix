
package com.janpix.rup.pixmanager;



import org.apache.cxf.annotations.WSDLDocumentation;
import org.grails.cxf.utils.EndpointType;
import org.grails.cxf.utils.GrailsCxfEndpoint;




import com.janpix.hl7dto.hl7.v3.datatypes.*;
import com.janpix.hl7dto.hl7.v3.messages.*;
import com.janpix.hl7dto.hl7.v3.interfaces.*;



@GrailsCxfEndpoint(expose = EndpointType.JAX_WS,soap12=true)
class OtherPIXManagerHL7v3Service implements PixManagerInterface {
			

	public HL7Message AddNewPatient(HL7Message body) {
		
		return body;
	}

		

}
