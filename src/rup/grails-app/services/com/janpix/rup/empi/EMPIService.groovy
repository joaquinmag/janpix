package com.janpix.rup.empi

import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.DuplicateIdentifierException
import com.janpix.rup.exceptions.identifier.IdentifierNotFoundException
import com.janpix.rup.exceptions.identifier.IdentifierNotValidException

/**
 * Servicio encargado de administrar el eMPI (enterprise Master Patient Index)
 * Da de alta, actualiza, elimina y hace merges de pacientes
 * @author martin
 *
 */
class EMPIService {
	def demographicPersonService
	def uuidGenerator
	
	
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
			throw new ExistingPatientException(message:"Ya existen pacientes que concuerdan con los datos demograficos pasados",patient:new Patient(p.properties))
		}
		try{
			//Agrego el paciente
			def patient = new Patient(p)
			
			//FIXME!. Provisorio hasta que funcion uuidGenerator
			patient.uuidGenerator = uuidGenerator
			//patient.uniqueId =  new PatientIdentifier(mainId:UUID.randomUUID().toString()) 
			patient.save(failOnError:true)
			
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
	 * @throw IdentifierException Si el paciente ya tenia agregado ese identificador o cualquier identificador para dicha entidad sanitaria
	 * @throw IdentifierException Si los datos pasados son invalidos para crear un nuevo identificador
	 */
	def addEntityIdentifierToPatient(Patient p,HealthEntity he, String peId){
		if(!existsPatient(p)){
			throw new DontExistingPatientException(message:"No existe ningun paciente que contenga el UUID pasado")
		}
		def identifier = new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:peId,assigningAuthority:he)
		if(!identifier.validate()){
			throw new IdentifierNotValidException("El identificador pasado contiene errores de validación")
		}
		//Verifico que no contenga el identificador ya
		if(p.identifiers.contains(identifier) || (p.identifiers.find{it.assigningAuthority == he} != null)){
			throw new DuplicateIdentifierException("Ya se encuentra agregado un identificador para la entidad sanitaria ${he}")
		}
		p.addToIdentifiers(identifier)
	}
	
	/**
	 * Actualiza el identificador que una Entidad Sanitaria tiene asignado a un paciente
	 * @param Patient p: el paciente que se le actualizara el identificador
	 * @param HealthEntity he : la entidad sanitaria que actualizara el identificador
	 * @param String oldId : el viejo identificador
	 * @param String newId : el nuevo identificador
	 * @throw DontExistingPatientException Si el paciente pasado no existia
	 * @throw IdentifierException si la entidad ya tiene ese identificador asignado a otro paciente
	 * @throw IdentifierException si no existe el identificador viejo para esa entidad
	 * @return
	 */
	def updateEntityIdentifierToPatient(Patient p,HealthEntity he,oldId,newId){
		if(!existsPatient(p)){
			throw new DontExistingPatientException(message:"No existe ningun paciente que contenga el UUID pasado")
		}
		
		if(newId == oldId) {return}
				
		//Verifico que el nuevo identificador NO exista ya asignado
		//TODO ver de ponerlo en la clase Identifier como un "validator unique"
		if(Identifier.findWhere(type:Identifier.TYPE_IDENTIFIER_PI,number:newId,assigningAuthority:he) != null){
			throw new DuplicateIdentifierException()
		}
		//Busco el identificador en el paciente
		def findedIdentifier
		def searchedIdentifier = new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:oldId,assigningAuthority:he)
		p.identifiers.collect {
			if(it == searchedIdentifier){
				//it.number = newId
				findedIdentifier = it
			}
		}
		if(findedIdentifier)
			findedIdentifier.number = newId
		else
			throw new IdentifierNotFoundException("No existe el identificador pasado en la entidad sanitaria ${he}")
			
		//Grabo el paciente con sus cambios
		//p.save(failOnError:true)
		
	}
	
	/**
	 * Elimina un identifacor de una entidad sanitaria asignada a un paciente
	 * @param Patient p: el paciente que contiene el identificador de la entidad saniataria
	 * @param HealthEntity he: La entidad sanitaria de la cual se quiere eliminar el identificador
	 * @return
	 */
	def removeIdentifierEntityToPatient(Patient p, HealthEntity he){
		//TODO implement me!
	}
	
	
	/**
	 * Devuelve una lista de todos los pacientes que matchean con ciertos datos demograficos
	 * @param Patient p: el paciente que se va a comparar
	 * @param Boolean includePossible: Si debo incluir los posibles matcheos
	 * @return List<Patient>: Lista de los pacientes matcheados
	 */
	List<Patient> getAllMatchedPatients(Patient p,Boolean includePossible=false){
		def matchedPersons = demographicPersonService.matchPerson(p)
		if(includePossible){
			matchedPersons.addAll(demographicPersonService.getPossibleMatchedPersons())
		}
		
		//Paso de Person to Patient
		List<Patient> matchedPatients = []
		matchedPersons.each {
			//FIXME!! segun la estrategia de herencia esto podria llegar a variar
			matchedPatients.add(Patient.get(it.id))
		}
		
		return matchedPatients
		
	}
	
	/**
	 * Verifica si los datos demograficos pasados matchean con algun paciente
	 * @param Person p: persona de la cual se validaran sus datos demograficos
	 * @return TRUE si existe uno o mas pacientes que matchean con esos datos, FALSE sino
	 */
	Boolean matchPerson(Person p,Boolean includePossible=false){
		//creo un paciente temporal a partir de la persona
		def tempPatient = new Patient(p.properties)
		return  getAllMatchedPatients(tempPatient,includePossible).size() > 0
	}
	
	/**
	 * Busca y devuelve el paciente que contenga el UUID pasado
	 * @param PatientIdentifier uuid : el identificador unico del paciente
	 * @return Patient si lo encontro, sino NULL
	 */
	def findPatientByUUID(PatientIdentifier uuid){
		/** FIXME!. Cuando busco por un PatientIdentifier mock falla 
		 * el findBy intenta hacer un flush de las instancias antes
		 * Ver Patient.withNewSession{}
		 * **/
		return Patient.findByUniqueId(uuid)

	}
	
	/**
	 * Devuelve un paciente a partir del identificador que usa una entidad sanitaria para referenciarlo
	 * @param String peId: Id que utiliza la Entidad Sanitaria para el paciente
	 * @param HealthEntity he: Entidad Sanitaria que esta buscando al paciente
	 * @return Patient p: El paciente si lo encontro, sino NULL
	 */
	def findPatientByHealthEntityId(String peId,HealthEntity he){
		def c = Person.createCriteria()
		def result = c.get {
		   identifiers {
			   and{
				   and{
					   	eq("type",Identifier.TYPE_IDENTIFIER_PI)
				   		eq("assigningAuthority",he)
				   }
				   eq("number",peId)
			   }
		   }
		}
		return result
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
