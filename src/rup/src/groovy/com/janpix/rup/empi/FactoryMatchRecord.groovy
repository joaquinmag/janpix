package com.janpix.rup.empi

class FactoryMatchRecord {
	def grailsApplication
	/**
	 * Constructor estatico que inserta grails application
	 * @return
	 */
	public def buildWithPersonAndPercentage(Person p, Double percentage){
		def mr = new MatchRecord(p,percentage)
		mr.lowerBound = grailsApplication.config.demographic.lowerBound as Double
		mr.upperBound = grailsApplication.config.demographic.upperBound as Double
		
		return mr	
	}
	
}
