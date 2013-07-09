package com.janpix.rup.empi

import com.janpix.rup.empi.avib.FactoryAVIb

/**
 * Servicio se utiliza para comparar identidades de personas
 *
 */
class IdentityComparatorService {
	def grailsApplication
	def factory
	static transactional = false
	/**
	 * Calcula el porcentaje de matcheo entre 2 personas
	 * @param Person p1
	 * @param Person p2
	 * @return Double entre 0 y 1 indicando nivel de matcheo
	 */
	Double calculatePercentageOfMatch(Person p1,Person p2){
		//Construyo las identidades
		def identity1 = Identity.buildFromPerson(p1)
		def identity2 = Identity.buildFromPerson(p2)
		
		factory = buildFactory()
		if(factory){
			def distanceName			= factory.buildMeasurementDistanceName().calculateDistance(identity1,identity2)
			def distanceBirthdate 		= factory.buildMeasurementDistanceBirthdate().calculateDistance(identity1,identity2)
			def distanceSex 			= factory.buildMeasurementDistanceSex().calculateDistance(identity1,identity2)
			def distanceLivingplace		= factory.buildMeasurementDistanceLivingplace().calculateDistance(identity1,identity2)
			def distanceAddress			= factory.buildMeasurementDistanceAddress().calculateDistance(identity1,identity2)
			def distanceDocument		= factory.buildMeasurementDistanceDocument().calculateDistance(identity1,identity2)
		
			return 1 - (distanceName + distanceBirthdate + distanceSex + distanceLivingplace +
						distanceAddress + distanceDocument)
		}else{
			//TODO lanzar excepcion
		}
		 
	}
	
	/**
	 * Construye la fabrica que se utilizara para medir variables de la identidad
	 * Se basa en el archivo de configuracion para construir la Fabrica
	 * @return
	 */
	private FactoryMeasurementDistanceAttribute buildFactory(){
		if(!factory){
			def method = grailsApplication.config.demographic.identity.measurementMethod as String
			if(method == "avib"){
				//Obtengo pesos y creo la fabrica	
				def weights = [
					"name":grailsApplication.config.identityMethods.avib.weights.name as Double,
					"birthdate":grailsApplication.config.identityMethods.avib.weights.birthdate as Double,
					"sex":grailsApplication.config.identityMethods.avib.weights.sex as Double,
					"livingplace":grailsApplication.config.identityMethods.avib.weights.livingplace as Double,
					"address":grailsApplication.config.identityMethods.avib.weights.address as Double,
					"document":grailsApplication.config.identityMethods.avib.weights.document as Double
				]
				factory = new FactoryAVIb(weights:weights)
			}else if(method == "fellegi-sunter"){
				//TODO hacer
			}
		}
		return factory
	}	
}
