package com.janpix.rup.empi

/**
 * Servicio encargado de administrar el eMPI (enterprise Master Patient Index)
 * Da de alta, actualiza, elimina y hace merges de pacientes
 * @author martin
 *
 */
class EMPIService {
	def demographicPatientService
	
	
	/**
	 * Agrega un nuevo paciente en el eMPI
	 * Si el paciente ya existe o tiene un cierto nivel de matcheo no lo crea
	 * Una vez creado el paciente agrega el identificador de la entidad sanitaria al mismo
	 * @return Patient: el paciente agregado si lo agrego correctamente
	 * @return null Si el paciente ya existia
	 */
	def addNewPatient(Patient p,HealthEntity he,String peId){
		if(existsPatient(p)){
			return null
		}
		//Agrego el paciente
		if(!p.save(flush:true)){
			return null
		} 
		//Agrego el identificador
		addIdentifierEntityToPatient(p, he, peId)
		
		return p
	}
	
	
	/**
	 * Actualiza la informacion demografico de cierto paciente
	 * @return Patient: el paciente actualizado
	 */
	def updateDemographicDataPatient(){
		//TODO ver si es necesario y como implementar
		//TODO implement me!	
	}
	
	/**
	 * Une la información de dos pacientes supuestamente diferentes que en realidad son el mismo
	 * @return
	 */
	def mergePatients(){
		//TODO implement me!
	}
	
	/**
	 * Elimina un paciente del eMPI
	 * @param Patient p: el paciente a eliminar
	 * @param HealthEntity he: La entidad sanitaria que quiere eliminar el paciente
	 * @return
	 */
	def deletePatient(Patient p, HealthEntity he){
		//TODO implement me!
	}
	
	/**
	 * Agrega un identificador de entidad saniatria a un paciente existente en el eMPI
	 * @return Patient p: el paciente al que se le agrego el identificador
	 * @return null si el paciente no existia o no se pudo agregar el identificador
	 */
	def addIdentifierEntityToPatient(Patient p,HealthEntity he, String peId){
		if(!existsPatient(p)){
			return null
		}
		
		def identifier = new Identifier(type:Identifier.TYPE_PI,number:peId,assigningAuthority:he)
		p.addToIdentifiers(identifier)
		
		if(!p.save(flush:true)){
			return null
		}
		
		return p
	}
	
	/**
	 * Elimina un identifacor de una entidad sanitaria determinada a un paciente
	 * @param Patient p: el paciente que contiene el identificador de la entidad saniataria
	 * @param HealthEntity he: La entidad sanitaria de la cual se quiere eliminar el identificador
	 * @return
	 */
	def removeIdentifierEntityToPatient(Patient p, HealthEntity he){
		//TODO implement me!
	}
	
	
	/**
	 * Devuelve una lista de todos los pacientes que matchean con ciertos datos demograficos
	 * @return
	 */
	def getMatchedPatients(){
		//TODO implement me!
		//def matchedPatient = demographicPatientService.matchPatient(p)
	}
	
	/**
	 * Devuelve el primer paciente que matchea con ciertos datos demograficos
	 * @return
	 */
	def getFirstMatchedPatient(){
		//TODO implement me!
	}
	
	/**
	 * Verifica la existencia de un paciente en base a los datos demograficos pasados
	 * @param Patient p: el paciente a verificar si esta agregado
	 * @return TRUE si el paciente existe, FALSE de lo contrario
	 */
	def existsPatient(Patient p){
		//TODO implement me!
		//TODO ver si hay que basarse en datos demograficos
		if(!p.id){
			return false;
		}
		return true
	}
}
