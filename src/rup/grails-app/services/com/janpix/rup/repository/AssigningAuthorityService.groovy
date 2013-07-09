package com.janpix.rup.repository

import com.janpix.rup.empi.AssigningAuthority
import com.janpix.rup.empi.AuthorityRUP
import com.janpix.rup.empi.HealthEntity

class AssigningAuthorityService {
	static transactional = false
	def grailsApplication
	
	AssigningAuthority rupAuthority(){
		return AuthorityRUP.findByOid(grailsApplication.config.rup.authority.rupoid)
	}
	
	HealthEntity findHealtEntityByOid(String oid){
		return HealthEntity.findByOid(oid);
	}
}
