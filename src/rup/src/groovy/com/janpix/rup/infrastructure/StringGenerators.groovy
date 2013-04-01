package com.janpix.rup.infrastructure
import java.util.UUID

import com.janpix.rup.empi.PatientIdentifier;

class MockUUIDGenerator {
	static def getUUID() {
		//def uuid = UUID.randomUUID()
		/*def uuid = "1"
		if(PatientIdentifier.count > 0)
			//uuid = PatientIdentifier.find {}
			uuid = "2"*/
			
		//return {uuid.toString()}
		//return uuid
		return {"5"}
	}
}
