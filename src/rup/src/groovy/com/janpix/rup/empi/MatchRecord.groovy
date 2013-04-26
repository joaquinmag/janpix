package com.janpix.rup.empi
/**
 * Clase que representa un registro devuelto por el DemographicPersonService
 * el cual contiene una Persona y un nivel de matcheo
 *
 */
class MatchRecord extends Object {
	def grailsApplication
	
	
	static final String TYPE_LEVEL_HIGH 		= 'H'
	static final String TYPE_LEVEL_MEDIUM 		= 'M'
	static final String TYPE_LEVEL_LOW 		= 'L'
	static final String TYPE_LEVEL_UNKNOWKN 	= 'U'
	
	Person person
	Double matchPercentage
	
	protected MatchRecord(){}
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
	String getMatchLevel(){
		def upperBound = grailsApplication.config.demographic.upperBound as Double
		def lowerBound = grailsApplication.config.demographic.lowerBound as Double
		
		if(!this.matchPercentage || !upperBound || !lowerBound){
			return MatchRecord.TYPE_LEVEL_UNKNOWKN
		}
		
		if(this.matchPercentage > upperBound)
			return MatchRecord.TYPE_LEVEL_HIGH
		else if(this.matchPercentage > lowerBound)
			return MatchRecord.TYPE_LEVEL_MEDIUM
		else
			return MatchRecord.TYPE_LEVEL_LOW	
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
