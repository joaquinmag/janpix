package com.janpix.rup.empi

/**
 * Servicio que matchea personas segun sus datos demograficos
 *
 */
class DemographicPersonService {
	def grailsApplication
	def identityComparatorService
	def factoryMatchRecord
	static transactional = false
	
	List<MatchRecord> matchedPersons = []
	List<MatchRecord> possibleMatchedPersons = []
	
	/**
	 * Busca una persona entre la lista de personas que 
	 * tenga un cierto nivel de matcheo en sus datos demograficos
	 * @param Person p: la persona a buscar
	 * @return List<MatchRecord> perons : lista de personas matcheadas
	 */
	def matchPerson(Person p){
		def upperLimit = this.getUpperLimit()
		def lowerLimit = this.getLowerLimit()
		matchedPersons.clear()
		possibleMatchedPersons.clear()
		
		//Hago un primer filtro en base a datos excluyentes como ser rango de edades
		def candidates = blockIndexing(p)
		
		
		log.info("Verificando matcheos de "+p+" entre "+candidates.size()+" candidatos ...")
		candidates.each{
			def percentage = identityComparatorService.calculatePercentageOfMatch(p,it)
			def matchRecord = factoryMatchRecord.buildWithPersonAndPercentage(it,percentage)
			log.info("${matchRecord}")
			if( percentage > upperLimit){
				matchedPersons.add(matchRecord)
			}else if(percentage > lowerLimit){
				possibleMatchedPersons.add(matchRecord)
			}
		}
		log.info("Verificación de matcheos finalizada.")
		
		return matchedPersons
	}
	
	/**
	 * Devuelve las ultimas personas que matchearon cuando se ejecuto matchPerson()
	 * @return List<Person> persons : ultimas personas matcheadas
	 */
	List<MatchRecord> lastMatchedPersons(){
		return matchedPersons
	}
	
	/**
	 * Devuelve las ultimas personas que son un POSIBLE matcheo luego de ejecutar matchPerson()
	 * @return List<Person> persons : ultimas personas que son un POSIBLE match
	 */
	List<MatchRecord> lastPossibleMatchedPersons(){
		return possibleMatchedPersons
	}
	
	
	/** Getter privados que devuelven upper y lower bound **/
	private Double upperBound
	private Double lowerBound
	
	private Double getUpperLimit(){
		if(!upperBound)
			upperBound = grailsApplication.config.demographic.upperBound as Double
		
		return upperBound
	}
	
	private Double getLowerLimit(){
		if(!lowerBound)
			lowerBound = grailsApplication.config.demographic.lowerBound as Double
		
		return lowerBound
	}
	
	/**
	 * Realiza un filtro de personas devolviendo solo aquellas que tienen
	 * posibilidades de matchear
	 * Se basa en atributos excluyentes para armar el bloque
	 * @param p
	 * @return
	 */
	private List<Person> blockIndexing(Person p){
		//TODO hacer
		def candidates = Person.list()
		candidates.remove(p)
		return candidates
	}
}
