package com.janpix.rup.empi.avib

import com.janpix.rup.empi.Identity;
import com.janpix.rup.empi.MeasurementDistanceAttribute;

/**
 * Clase que proporciona la interfaz para medir distancias 
 * entre atributos del tipo Name usando el algoritmo AVI-b
 *
 */
class AVIbMeasurementDistanceDocument extends MeasurementDistanceAttribute {
	
	/**
	 * Calcula la distancia entre 2 atributos de la identidad ponderados por el peso del atrbuto
	 * Para calcular la distancia entre los nombres usa el algoritmo de Levenshtein
	 * @param Identity identity1
	 * @param Identity identity2
	 * @return Double indicando la distancia. Cuanto mas cerca de 1 mayor distancia
	 */
	Double calculateDistance(Identity identity1, Identity identity2){
		//return this.weight * this.calculateLevenshteinDistance(identity1.document, identity2.document)
		if(isValidDocument(identity1.document) && isValidDocument(identity2.document)){
			if(identity1.document == identity2.document)
				return 0*this.weight
		}
		
		return 1*this.weight
	}
	
	private Boolean isValidDocument(String doc){
		return (doc!=null && doc?.trim()!="")
	}
	
}
