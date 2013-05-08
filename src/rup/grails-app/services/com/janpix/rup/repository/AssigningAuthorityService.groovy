package com.janpix.rup.repository

import com.janpix.rup.empi.AssigningAuthority
import com.janpix.rup.empi.AuthorityRUP

class AssigningAuthorityService {
	def grailsApplication
	
	AssigningAuthority rupAuthority(){
		return AuthorityRUP.findByOid(grailsApplication.config.rup.authority.rupoid)
	}
}
