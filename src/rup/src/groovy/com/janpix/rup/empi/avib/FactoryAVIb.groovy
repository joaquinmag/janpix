package com.janpix.rup.empi.avib

import com.janpix.rup.empi.FactoryMeasurementDistanceAttribute
import com.janpix.rup.empi.MeasurementDistanceAttribute

/**
 * Fabrica abstracta para construir los medidores de distancia
 * entre atributos de identidades
 *
 */
class FactoryAVIb extends FactoryMeasurementDistanceAttribute {
	def weights
	
	MeasurementDistanceAttribute buildMeasurementDistanceName(){
		def measurementDistance = new AVIbMeasurementDistanceName()
		measurementDistance.setWeight(weights["name"])
		
		return measurementDistance
	}
	
	MeasurementDistanceAttribute buildMeasurementDistanceBirthdate(){
		def measurementDistance = new AVIbMeasurementDistanceBirthdate()
		measurementDistance.setWeight(weights["birthdate"])
		
		return measurementDistance
	}
	
	MeasurementDistanceAttribute buildMeasurementDistanceSex(){
		def measurementDistance = new AVIbMeasurementDistanceSex()
		measurementDistance.setWeight(weights["sex"])
		
		return measurementDistance	
	}
	
	MeasurementDistanceAttribute buildMeasurementDistanceSecondLastName(){
		def measurementDistance = new AVIbMeasurementDistanceSecondLastName()
		measurementDistance.setWeight(weights["secondLastName"])
		
		return measurementDistance
	}
	
	MeasurementDistanceAttribute buildMeasurementDistanceLivingplace(){
		def measurementDistance = new AVIbMeasurementDistanceLivingplace()
		measurementDistance.setWeight(weights["livingplace"])
		
		return measurementDistance
	}
	
	MeasurementDistanceAttribute buildMeasurementDistanceAddress(){
		def measurementDistance = new AVIbMeasurementDistanceAddress()
		measurementDistance.setWeight(weights["address"])
		
		return measurementDistance
	}
	
	MeasurementDistanceAttribute buildMeasurementDistanceDocument(){
		def measurementDistance = new AVIbMeasurementDistanceDocument()
		measurementDistance.setWeight(weights["document"])
		
		return measurementDistance
	}
}
