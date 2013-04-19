package com.janpix.rup.empi

import javax.naming.ldap.HasControls;
import org.apache.commons.lang.builder.HashCodeBuilder

class City {
	String name
	Province province
	
	static belongsTo = [province:Province]
	
    static constraints = {
    }
	
	boolean equals(other){
		return (this.province == other.province && this.name == other.name)
	}
	
	
}
