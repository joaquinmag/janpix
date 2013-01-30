package com.janpix.rup.empi

import com.janpix.rup.exceptions.*

/**
 * Servicio encargado de administrar el eMPI (enterprise Master Patient Index)
 * Da de alta, actualiza, elimina y hace merges de pacientes
 * @author martin
 *
 */
class EMPIService {
	def demographicPersonService
	
	
	/**
	 * Agrega un nuevo paciente en el eMPI
	 * Si el paciente ya existe o tiene un cierto nivel de matcheo no lo crea
	 * Una vez creado el paciente agrega el identificador de la entidad sanitaria al mismo
	 * @return Patient: el paciente agregado si lo agrego correctamente
	 * @throw ExistingPatientException Si ya existe uno o mas paciente en el eMPI con esa informacion demografica 
	 * @throw ShortDemographicDataException Si la informacion brindada no alcanza para crear un paciente
	 */
	def createPatient(Person p){	
		if(matchPerson(p)){
			throw new ExistingPatientException(message:"Ya existen pacientes que concuerdan con los datos demograficos pasados",patient:patient)
		}
		try{
			//Agrego el paciente
			def patient = new Patient(p.properties) //TODO hacer que se autogenere el uniqueId
			patient.save(flush:true,failOnError:true) 
			return patient
		}catch(Exception e){
			throw new ShortDemographicDataException(message:"Debe proporcionar mayor información del paciente",person:p)
		}
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
	 * @throw DontExistingPatientException Si el paciente pasado no existia
	 */
	def addEntityIdentifierToPatient(Patient p,HealthEntity he, String peId){
		if(!existsPatient(p)){
			throw new DontExistingPatientException(message:"No existe ningun paciente que contenga el UUID pasado")
		}
		def identifier = new Identifier(type:Identifier.TYPE_PI,number:peId,assigningAuthority:he)
		//TODO verificar que dicha entidad sanitaria no tenga ya cargado un identificador
		if(p.identifiers.contains(identifier)){
			throw new IdentifierException(type:IdentifierException.TYPE_ENTITY_DUPLICATE,message:"Ya se encuentra agregado un identificador para la entidad sanitaria "+he)
		}
		p.addToIdentifiers(identifier)
		
		p.save(flush:true,failOnError:true)
	}
	
	/**
	 * Actualiza el identificador que una Entidad Sanitaria tiene asignado a un paciente
	 * @param Patient p: el paciente que se le actualizara el identificador
	 * @param HealthEntity he : la entidad sanitaria que actualizara el identificador
	 * @param oldId : el identificador viejo
	 * @param newId : el nuevo identificador
	 * @throw DontExistingPatientException Si el paciente pasado no existia
	 * TODO lanzar excepcion si la entidad ya tiene ese identificador asignado a otro paciente
	 * TODO lanzar excepcion si no existe el identificador viejo para esa entidad
	 * @return
	 */
	def updateEntityIdentifierToPatient(Patient p,HealthEntity he,oldId,newId){
		if(!existsPatient(p)){
			throw new DontExistingPatientException(message:"No existe ningun paciente que contenga el UUID pasado")
		}
		
		if(newId == oldId) {return}
		
		//Busco el identificador en el paciente
		def findedIdentifier = new Identifier(type:Identifier.TYPE_PI,number:oldId,assigningAuthority:he)
		p.identifiers.collect {
			if(it == findedIdentifier){
				it.number = newId
			}
		}
		
		//Grabo el paciente con sus cambios
		p.save(flush:true,failOnError:true)
		
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
	def getAllMatchedPersons(Person p){
		//TODO implement me!
		return demographicPersonService.matchPerson(p)
		
	}
	
	/**
	 * Verifica si los datos demograficos pasados matchean con algun paciente
	 * @param Person p: persona de la cual se validaran sus datos demograficos
	 * @return TRUE si existe uno o mas pacientes que matchean con esos datos, FALSE sino
	 */
	def matchPerson(Person p){
		return  getAllMatchedPersons(p).size() > 0
	}
	
	/**
	 * Busca y devuelve el paciente que contenga el UUID pasado
	 * @param String uuid : el identificador unico del paciente
	 * @return Patient si lo encontro, sino NULL
	 */
	def findPatientByUUID(String uuid){
		return Patient.findByUniqueId(uuid)
	}
	
	/**
	 * Devuelve un paciente
	 * @param String peId: Id que utiliza la Entidad Sanitaria para el paciente
	 * @param HealthEntity he: Entidad Sanitaria que esta buscando al paciente
	 * @return Patient p: El paciente si lo encontro, sino NULL
	 */
	def findPatientByHealthEntityId(String peId,HealthEntity he){
		//TODO ver si verifico si hay mas de un paciente con esas identificaciones
		def identifier = Identifier.findWhere(	type:Identifier.TYPE_PI,
												assigningAuthority:he,
												number:peId
											)
		if(identifier)
			return identifier.patient
			
		return null
	}
	
	
	/**
	 * Verifica la existencia de un paciente en base al identificador que contiene el mismo
	 * @param Patient p: el paciente a verificar si esta agregado
	 * @return TRUE si el paciente existe, FALSE de lo contrario
	 */
	def existsPatient(Patient p){
		if(p.uniqueId){
			if(findPatientByUUID(p.uniqueId)!=null){
				return true;
			}
		}
		return false
	}
}
