package com.janpix.rup.empi

/**
 * Fabrica abstracta para construir los medidores de distancia
 * entre atributos de identidades
 *
 */
abstract class FactoryMeasurementDistanceAttribute {
	
	abstract MeasurementDistanceAttribute buildMeasurementDistanceName()
	abstract MeasurementDistanceAttribute buildMeasurementDistanceBirthdate()
	abstract MeasurementDistanceAttribute buildMeasurementDistanceSex()
	abstract MeasurementDistanceAttribute buildMeasurementDistanceLivingplace()
	abstract MeasurementDistanceAttribute buildMeasurementDistanceAddress()
	abstract MeasurementDistanceAttribute buildMeasurementDistanceDocument()
}
