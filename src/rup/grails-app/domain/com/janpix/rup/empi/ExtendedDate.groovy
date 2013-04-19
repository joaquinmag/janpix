package com.janpix.rup.empi


class ExtendedDate {
	static final Integer TYPE_PRECISSION_UNKNOWN 	= 0
	static final Integer TYPE_PRECISSION_YEAR 		= 1
	static final Integer TYPE_PRECISSION_MONTH 	= 2
	static final Integer TYPE_PRECISSION_DAY 		= 3
	
	
	
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
	 * 2 fechas son iguales sin tienen misma precision y en base a esta mismos atributos
	 */
	boolean equals(other){
		if(this.precission == other.precission){
			switch(this.precission){
				case ExtendedDate.TYPE_PRECISSION_UNKNOWN:
					return true
					break
				case ExtendedDate.TYPE_PRECISSION_YEAR:
					if(this.date.year == other.date.year)
						return true
					break
				case ExtendedDate.TYPE_PRECISSION_MONTH:
					if(this.date.year == other.date.year && (this.date.month == other.date.month))
						return true
					break
				case ExtendedDate.TYPE_PRECISSION_DAY:
					if(this.date.year == other.date.year && (this.date.month == other.date.month) && (this.date.day == other.date.day))
						return true
					break
			}
		}else{
			return false
		}
	}
	
	String toString(){
		return "${this.date}";
	}
}
