package com.janpix.rup.infrastructure

class MockUUIDGenerator {
	static def getUUID() {
		return { UUID.randomUUID().toString() }
	}
}
