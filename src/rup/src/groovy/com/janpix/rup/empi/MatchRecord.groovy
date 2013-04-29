package com.janpix.rup.empi
/**
 * Clase que representa un registro devuelto por el DemographicPersonService
 * el cual contiene una Persona y un nivel de matcheo
 *
 */
class MatchRecord extends Object {
	
	public enum LevelMatchRecord{
		High,
		Medium,
		Low,
		Unknown
	} 
	
	Person person
	Double matchPercentage
	Double upperBound
	Double lowerBound
	
	MatchRecord(Person p, Double percentage){
		this.person = p
		this.matchPercentage = percentage
	}
	
	/**
	 * Devuelve el nivel de matcheo del registro
	 * @return String level segun:
	 * LEVEL_HIGH: El nivel de matcheo era mayor estricto que el limite superior
	 * LEVEL_MEDIUM: El nivel de matcheo era mayor estricto que el limite inferior y menor que el limite superior 
	 * LEVEL_LOW: El nivel de matcheo era menor que el limite inferior
	 * LEVEL_UNKNOWN: No se contaba con información suficiente para calcular el nivel
	 * 
	 */
	LevelMatchRecord getMatchLevel(){		
		if(!this.matchPercentage || !upperBound || !lowerBound){
			return LevelMatchRecord.Unknown
		}
		
		if(this.matchPercentage > upperBound)
			return LevelMatchRecord.High
		else if(this.matchPercentage > lowerBound)
			return LevelMatchRecord.Medium
		else
			return LevelMatchRecord.Low	
	}
	
	
	/**
	 * Si las personas son la misma se considera el mismo registro
	 */
	boolean equals(other){
		return this.person == other.person
	}
	
	String toString(){
		return "${person}:${matchPercentage}%"
	}
}
