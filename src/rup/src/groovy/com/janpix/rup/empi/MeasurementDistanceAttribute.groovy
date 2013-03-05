package com.janpix.rup.empi

/**
 * Clase abstracta que proporciona la interfaz para medir
 * medir distancias entre atributos
 *
 */
abstract class MeasurementDistanceAttribute {
	def weight
	
	/**
	 * Calcula la distancia entre 2 atributos de la identidad ponderados por el peso del atrbuto
	 * @param Identity identity1
	 * @param Identity identity2
	 * @return Double indicando la distancia. Cuanto mas cerca de 1 mayor distancia
	 */
	abstract Double calculateDistance(Identity identity1, Identity identity2)
	
	/**
	 * Setea el peso del atributo para el calculo de distancias
	 * A mayor peso mas influencia tendra el atributo
	 * @param w
	 */
	void setWeight(Double w){
		weight = w
	}
	
	/**
	 * Compara la distancia entre 2 String usando  el algoritmo de Levenshtein
	 * @param String s1
	 * @param String s2
	 * @return Double cercano a 0 si los Strings son parecidos
	 * @return Double cercano a 1 si los Strings son diferentes
	 */
	Double calculateLevenshteinDistance(String s1, String s2){
		// Se pasa todo a minusculas para comparar
		String minS1 = s1.toLowerCase()
		String minS2 = s2.toLowerCase()
	 
		// Largo del string mas largo
		Integer maxLenght = (s1.size() >= s2.size() )?s1.size():s2.size()
	 
		// Calcula el indice basado en la distancia de levenshtein
		return levenshtein(minS1, minS2) / maxLenght
	}
	
	/**
	 * Implementacion algoritmo levenshteint
	 * @url: http://en.wikibooks.org/wiki/Algorithm_implementation/Strings/Levenshtein_distance#Groovy
	 * @return
	 */
	private int levenshtein(String str1, String str2) {
        int[][] distance = new int[str1.size() + 1][str2.size() + 1]
        for (int i in 0..str1.size()) distance[i][0] = i
        for (int j in 0..str2.size()) distance[0][j] = j
        for (int i in 1..str1.size())
            for (int j in 1..str2.size())
                distance[i][j] = [distance[i-1][j]+1,distance[i][j-1]+1,distance[i-1][j-1]+((str1[i-1]==str2[j-1])?0:1)].min()
        return distance[str1.size()][str2.size()]
    }
}
