package com.janpix.rup.empi.avib

import com.janpix.rup.empi.Identity;
import com.janpix.rup.empi.MeasurementDistanceAttribute;

/**
 * Clase que proporciona la interfaz para medir distancias 
 * entre atributos del tipo Name usando el algoritmo AVI-b
 *
 */
class AVIbMeasurementDistanceSecondLastName extends MeasurementDistanceAttribute {
	
	/**
	 * Calcula la distancia entre 2 atributos de la identidad ponderados por el peso del atrbuto
	 * Para calcular la distancia entre los nombres usa el algoritmo de Levenshtein
	 * @param Identity identity1
	 * @param Identity identity2
	 * @return Double indicando la distancia. Cuanto mas cerca de 1 mayor distancia
	 */
	Double calculateDistance(Identity identity1, Identity identity2){
		return this.weight * this.calculateLevenshteinDistance(identity1.secondLastName, identity2.secondLastName)
	}
	
	
}
