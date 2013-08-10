package com.janpix.rup.empi

import com.janpix.rup.infrastructure.Mapper
import com.janpix.rup.infrastructure.dto.ExtendedDateDTO


class ExtendedDate {
	static final String TYPE_PRECISSION_UNKNOWN 	= "Uknown"
	static final String TYPE_PRECISSION_YEAR 		= "Year"
	static final String TYPE_PRECISSION_MONTH 		= "Month"
	static final String TYPE_PRECISSION_DAY 		= "Day"
	
	
	
	Date date
	String precission
	
	static belongsTo = [Person]
	 
	
	
	static constraints = {
		date(
			nullable:true, 
			blank:true, 
			validator:{val,obj->
					if(obj.precission != ExtendedDate.TYPE_PRECISSION_UNKNOWN){
						return ((val != null) || (val != ""))
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
	
	/**
	 * Convierte la clase de dominio en su DTO
	 * @param mapper
	 * @return
	 */
	ExtendedDateDTO convert(Mapper mapper){
		return mapper.convert(this)
	}
}
