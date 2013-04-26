package com.janpix.rup.services.helpers

import org.hl7.v3.II;

class Hl7v3MessageHelper {
	def uuidGenerator
	
	II generateUniqueII() {
		return new II(root: uuidGenerator())
	}
}
