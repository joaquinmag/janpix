package com.janpix.rup.empi

class EmitterCountry extends AssigningAuthority {
	EmitterCountry(String oid, String name) {
		super(oid, name)
	}
	
	static EmitterCountry buildArgentinaEmitterCountry() {
		//return new EmitterCountry(name:"Argentina", oid:"2.16.32")
		return EmitterCountry.findByOid("2.16.32")
	}
}
