package com.janpix.rup.empi

import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.RUPException
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
	def factoryMatchRecord
	
	
	/**
	 * Agrega un nuevo paciente en el eMPI
	 * Si el paciente ya existe o tiene un cierto nivel de matcheo no lo crea
	 * Una vez creado el paciente agrega el identificador de la entidad sanitaria al mismo
	 * @param Person p: la persona que se quiere agregar al eMPI
	 * @return Patient: el paciente agregado si lo agrego correctamente 
	 * @throws ShortDemographicDataException Si la informacion brindada no alcanza para crear un paciente
	 */
	def createPatient(Person p) {	
		try{
			//Agrego el paciente
			def patient = new Patient(p)
			patient.uuidGenerator = uuidGenerator
			patient.save(failOnError:true)
			
			return patient
		}catch(Exception e){
			throw new ShortDemographicDataException(message:"Debe proporcionar mayor información del paciente",person:p)
		}
	}
	
	
	/**
	 * Actualiza la informacion demografico de cierto paciente
	 * @param Person: Los datos demograficos a actualizar
	 * @param Patient: El paciente que se actualizara
	 * @return Patient: el paciente actualizado
	 * @throws ExistingPatientException: Si la informacion modificada del paciente genera un matcheo con otro paciente ya existente
	 * @throw DontExistingPatientException Si el paciente pasado no existia
	 */
	def updateDemographicDataPatient(Patient p, Person person){
		def patient = findPatientByUUID(p.uniqueId)
		if(!patient){
			throw new DontExistingPatientException(message:"No existe ningun paciente que contenga el UUID pasado")
		}
		
		person.properties.each{prop,val->
			if(val){
				//Las propiedades readonly son las que terminan con la palabra "Id"
				if(!isPropertyReadOnly(prop)){
					//Si tiene el metodo update<Propiedad>() llamo ese metodo sino llamo al setter
					def method = "update"+prop.capitalize()
					if (patient.metaClass.respondsTo(patient, method, val)){
						patient."$method"(val) //Llama a updateBirthdate por ejemplo
					}else{
						patient[prop] = val
					}
				}
			}
		}

		//Si el paciente matchea con algun otro vuelvo los cambios para atras
		if( matchPatient(patient)){
			throw new RUPException("Existen pacientes que concuerdan con el paciente actualizado")		
		}

		return patient
	}
	
	
	
	/**
	 * Une la información de dos pacientes supuestamente diferentes que en realidad son el mismo
	 * @return
	 */
	def mergePatients(){
		//FIXME implement me in other version!.
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
	 * @throw DuplicateIdentifierException Si el paciente ya tenia agregado ese identificador o cualquier identificador para dicha entidad sanitaria
	 * @throw IdentifierNotValidException Si los datos pasados son invalidos para crear un nuevo identificador
	 */
	def addEntityIdentifierToPatient(Patient p,HealthEntity he, String peId){
		if(!existsPatient(p)){
			throw new DontExistingPatientException(message:"No existe ningun paciente que contenga el UUID pasado")
		}
		//Verifico que ese identificador no exista ya
		if(Identifier.findWhere(type:Identifier.TYPE_IDENTIFIER_PI,number:peId,assigningAuthority:he) != null)
			throw new DuplicateIdentifierException("Ya se encuentra agregado el identificador ${peId} para la entidad sanitaria ${he}")
			
		def identifier = new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:peId,assigningAuthority:he)
		if(!identifier.validate()){
			throw new IdentifierNotValidException("El identificador pasado contiene errores de validación")
		}
		//Verifico que no contenga el identificador ya
		if(p.identifiers.contains(identifier) || (p.identifiers.find{it.assigningAuthority == he} != null)){
			throw new DuplicateIdentifierException("Ya se encuentra agregado un identificador para la entidad sanitaria ${he}")
		}
		
		p.addToIdentifiers(identifier)
		return p
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
	def removeEntityIdentifierToPatient(Patient p, HealthEntity he){
		//TODO implement me!
	}
	
	
	/**
	 * Devuelve una lista de todos los pacientes que matchean con ciertos datos demograficos
	 * @param Patient p: el paciente que se va a comparar
	 * @param Boolean includePossible: Si debo incluir los posibles matcheos
	 * @return List<MatchRecord>: Lista de los pacientes matcheados y su nivel de matcheo
	 */
	List<MatchRecord> getAllMatchedPatients(Person p,Boolean includePossible=false){
		def matchedPersons = demographicPersonService.matchPerson(p)
		if(includePossible){
			matchedPersons.addAll(demographicPersonService.getPossibleMatchedPersons())
		}
		
		//Paso de Person to Patient
		List<MatchRecord> matchedPatients = []
		matchedPersons.each { MatchRecord it->
			def patient = getPatientFromPerson(it.person)
			matchedPatients.add(factoryMatchRecord.buildWithPersonAndPercentage(patient,it.matchPercentage))
		}
		
		return matchedPatients
		
	}
	
	/**
	 * Verifica si los datos demograficos pasados matchean con algun paciente
	 * @param Person p: persona de la cual se validaran sus datos demograficos
	 * @return TRUE si existe uno o mas pacientes que matchean con esos datos, FALSE sino
	 */
	Boolean matchPatient(Person p,Boolean includePossible=false){
		return  getAllMatchedPatients(p,includePossible).size() > 0
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
		//return Patient.findByUniqueId(uuid)
		def query =Patient.where {uniqueId.mainId == uuid.mainId} 
		return query.find()

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
	 * Verifica la existencia de un paciente en el EMPI en base al identificador que contiene el mismo
	 * @param Patient p: el paciente a verificar si esta agregado
	 * @return TRUE si el paciente existe, FALSE de lo contrario
	 */
	Boolean existsPatient(Patient p){
		if(p.uniqueId){
			if(findPatientByUUID(p.uniqueId)!=null){
				return true;
			}
		}
		return false
	}
	
	/**
	 * FIXME!! segun la estrategia de herencia esto podria llegar a variar
	 * Obtiene un paciente a partir de una persona
	 * @param Person p
	 * @return Patient patient
	 */
	private Patient getPatientFromPerson(Person p){
		return Patient.get(p.id)
	}
	
	/**
	 * Verifica si una propiedad no es readonly
	 * Si el nombre termina en Id es una propiedad readonly
	 * @return
	 */
	private Boolean isPropertyReadOnly(String propName){
		return (propName ==~ /[a-zA-Z]+Id$/)
	}
}
