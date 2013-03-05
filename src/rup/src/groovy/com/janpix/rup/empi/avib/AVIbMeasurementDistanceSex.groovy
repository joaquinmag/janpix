package com.janpix.rup.empi.avib

import com.janpix.rup.empi.Identity;
import com.janpix.rup.empi.MeasurementDistanceAttribute;

/**
 * Clase que proporciona la interfaz para medir distancias 
 * entre atributos del tipo Sex usando el algoritmo AVI-b
 *
 */
class AVIbMeasurementDistanceSex extends MeasurementDistanceAttribute {
	
	/**
	 * Calcula la distancia entre 2 atributos de la identidad ponderados por el peso del atrbuto
	 * @param Identity identity1
	 * @param Identity identity2
	 * @return Double indicando la distancia. Cuanto mas cerca de 1 mayor distancia
	 */
	Double calculateDistance(Identity identity1, Identity identity2){
		if(identity1.sex == identity2.sex)
			return 0.0
		else
			return 1.0*this.weight
	}
	

	
}
