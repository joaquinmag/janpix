package com.janpix.rup.infrastructure.dto

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
class AssigningAuthorityDTO {
	@XmlElement(required=true)
	String name
	@XmlElement(required=true)
	String oid
	
	public AssigningAuthorityDTO(){
		
	}
	public AssigningAuthorityDTO(oid,name){
		this.oid = oid
		this.name = name
	}
	
	public boolean equals(other){
		return (this.name == other.name && this.oid == other.oid)
	}
}
