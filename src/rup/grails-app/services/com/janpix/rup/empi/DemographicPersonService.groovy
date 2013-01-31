package com.janpix.rup.empi

/**
 * Servicio que matchea personas segun sus datos demograficos
 * @author martin
 *
 */
class DemographicPersonService {
	
	/**
	 * Busca una persona entre la lista de personas que 
	 * tenga un cierto nivel de matcheo en sus datos demograficos
	 * @param Person p: la persona a buscar
	 * @return List<Person> perons : lista de personas matcheadas
	 */
	def matchPerson(Person p){
		//TODO Ver de pasarle un nivel de matcheo
		//TODO ver de estandarizar ciertos datos antes de comprar (ej 32.850.137 == 32850137)
		//TODO implement me!
		List<Person> persons = []
		return persons
	}
}
