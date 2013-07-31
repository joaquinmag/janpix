package com.janpix.rup.infrastructure.dto

import com.janpix.rup.infrastructure.Mapper;

class AssigningAuthorityDTO {
	String name
	String oid
	
	public AssigningAuthorityDTO(){
		
	}
	public AssigningAuthorityDTO(oid,name){
		this.oid = oid
		this.name = name
	}
	
	def convert(Mapper mapper){
		mapper.convert(this);
	}
}
