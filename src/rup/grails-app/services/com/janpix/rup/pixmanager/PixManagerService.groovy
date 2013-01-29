package com.janpix.rup.pixmanager

import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException

/**
 * Servicio encargado de procesar las peticiones de las diferentes Entidades Sanitarias
 * y comunicarse con el EMPIService para llevar a cabo dichas peticiones
 * @author martin
 *
 */
class PixManagerService {
	//TODO ver como integrarlo al un webservice. Usar el plugin cxf
	//TODO ver como se pasan los parametros a los metodos. Si le puedo pasar un Patient o tengo que pasar todos Strings y despues armarlo
	
	def EMPIService
	
	/**
	 *Agrega el paciente de la Entidad Sanitaria al eMPI
	 *Si el paciente ya existe agrega el identificador de la Entidad Sanitaria a los identificadores del paciente
	 *Si el paciente no existia lo crea y asigna el identificador de la Entidad Sanitaria a los identificadores del paciente
	 *@param Los datos demograficos (TODO ver como pasarlos)
	 *@param La Entidad Sanitaria (TODO ver como pasarlos)
	 *@param El Id que la Entidad Sanitaria usa para el paciente (TODO ver como pasarlos)
	 *@return El Id Unico del paciente (CUIS) si lo pudo agregar correctamente (TODO ver que dice el protocolo que se debe devolver)
	 *TODO ver que retornar si la informacion brindada no es suficiente para matchear un solo paciente o para crear uno nuevo
	 */
	def addNewPatient(){
		//TODO borrador del metodo
		def person
		try{
			if(!EMPIService.matchPerson(person)){
				EMPIService.createPatient(person)
			}else{
				//TODO agregar identificador
			}
		}
		catch(ExistingPatientException e){
			//TODO en vez de preguntar puedo capturar la exception
			//TODO agregar el identificador al paciente ya existente
		}
		catch(ShortDemographicDataException e){
			//TODO avisar
		}
	}
	
	/**
	 * Devuelve el identificador unico de un paciente (CUIS)
	 * @return
	 */
	def getUniqueIdPatient(){
		
	}
	
	/**
	 * Actualiza el identificador que una entidad sanitaria tiene asignado a un paciente
	 * @return
	 */
	def updateIdentifier(){
		
	}
	
	/**
	 * Actualiza informacion demografica del paciente
	 * @return
	 */
	def updateDemographicData(){
		
	}
}
