package com.janpix.rup.infrastructure

import static java.util.UUID.randomUUID

class MockUUIDGenerator {
	static def getUUID() {
		return { randomUUID() as String }
	}
}
