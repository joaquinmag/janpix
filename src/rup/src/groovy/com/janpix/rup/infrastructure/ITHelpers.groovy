package com.janpix.rup.infrastructure

import static java.util.UUID.randomUUID

class UUIDGenerator {
	static def getUUID() {
		return { randomUUID().toString().substring(0,13)}
	}
}

class DateHelper { 
	static def getActualDate() {
		return { new Date() }
	}
}

