package com.janpix.rup.empi


class ExtendedDate {
	static final Integer TYPE_PRECISSION_UNKNOWN 	= 0
	static final Integer TYPE_PRECISSION_DAY 		= 1
	static final Integer TYPE_PRECISSION_MONTH 	= 2
	static final Integer TYPE_PRECISSION_YEAR 		= 3
	
	
	
	Date date
	Integer precission
	
	static belongsTo = [Person]
	 
	
	
	static constraints = {
		date(
			nullable:true,
			validator:{val,obj->
					if(obj.precission != ExtendedDate.TYPE_PRECISSION_UNKNOWN){
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
