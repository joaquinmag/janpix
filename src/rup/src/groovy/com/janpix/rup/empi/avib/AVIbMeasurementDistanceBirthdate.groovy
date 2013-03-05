package com.janpix.rup.empi.avib

import com.janpix.rup.empi.Identity;
import com.janpix.rup.empi.MeasurementDistanceAttribute;

/**
 * Clase que proporciona la interfaz para medir distancias 
 * entre atributos del tipo Birthdate usando el algoritmo AVI-b
 *
 */
class AVIbMeasurementDistanceBirthdate extends MeasurementDistanceAttribute {
	
	/**
	 * Calcula la distancia entre 2 atributos de la identidad ponderados por el peso del atrbuto
	 * @param Identity identity1
	 * @param Identity identity2
	 * @return Double indicando la distancia. Cuanto mas cerca de 1 mayor distancia
	 */
	Double calculateDistance(Identity identity1, Identity identity2){
		def date1 = identity1.birthdate
		def date2 = identity2.birthdate
		def distance = 1.0
		
		if(identity1.birthdate == identity2.birthdate){
			distance = 0.0
		}else if((date1.year == date2.year) && (date1.month == date2.month)){
			//Si el mes y el año son iguales hay una cierta similitud
			distance =  0.3
		}
		return distance * this.weight
	}
	

	
}
