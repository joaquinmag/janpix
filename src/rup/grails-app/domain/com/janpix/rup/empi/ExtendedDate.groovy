package com.janpix.rup.empi


class ExtendedDate {
	enum PrecissionLevel {
		DAY,
		MONTH,
		YEAR,
		UNKNOWN,
	}
	
	Date date
	PrecissionLevel precission
	 
	
	
	static constraints = {
		date(
			nullable:true,
			validator:{val,obj->
					if(obj.precission != PrecissionLevel.UNKNOWN){
						return val != null
					}
					return true
				}
			)
		precission(nullable:false)
		
	}
	
	/**
	 * Compara 2 ExtendedDate
	 */
	boolean equals(other){
		if(this.date == other.date && this.precission == other.precission){
			return true
		}
		return false
	}
}
