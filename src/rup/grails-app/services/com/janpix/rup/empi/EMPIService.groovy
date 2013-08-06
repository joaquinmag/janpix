package com.janpix.rup.empi

import com.janpix.rup.exceptions.DontExistingPatientException
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.MessageMappingException
import com.janpix.rup.exceptions.RUPException
import com.janpix.rup.exceptions.ShortDemographicDataException
import com.janpix.rup.exceptions.identifier.DuplicateAuthorityIdentifierException
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
	def i18nMessage
	static transactional = false
	
	
	/**
	 * Agrega un nuevo paciente en el eMPI
	 * @param Person p: la persona que se quiere agregar al eMPI
	 * @return Patient: el paciente agregado si lo agrego correctamente 
	 * @throws ShortDemographicDataException Si la informacion brindada no alcanza para crear un paciente
	 */
	def createPatient(Person p) {	
		try{
			//Agrego el paciente
			Patient patient = new Patient(p)
			patient.uuidGenerator = uuidGenerator
			patient.save(failOnError:true)
			
			return patient
		}catch(Exception e){
			log.debug("Error creating patient", e)
			throw new ShortDemographicDataException(message:i18nMessage("empiservice.shortdemographicdata.exception"),person:p)
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
	def updateDemographicDataPatient(Patient patient, Person person)
	{
		if(!patient.isAttached())
			throw new DontExistingPatientException(message:i18nMessage("empiservice.dontexistingpatient.exception"))
			
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
			throw new ExistingPatientException(i18nMessage("empiservice.existingpatient.exception"))		
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
	 * @throw DuplicateIdentifierException Si el identificador a agregar ya existe en otro paciente
	 * @throw DuplicateAuthorityIdentifierException Si el paciente ya tiene un identificador asignado para esa Autoridad de Asignación
	 * @throw IdentifierNotValidException Si los datos pasados son invalidos para crear un nuevo identificador
	 */
	def addEntityIdentifierToPatient(Patient patient,HealthEntity healthEntity, String peId){

		if(!patient.isAttached())
			throw new DontExistingPatientException(message:i18nMessage("empiservice.dontexistingpatient.exception"))
		
		//Verifico que el identificador no este asignado por esa entidad sanitaria a otro paciente
		if(Identifier.findWhere(type:Identifier.TYPE_IDENTIFIER_PI,number:peId,assigningAuthority:healthEntity) != null)
			throw new DuplicateIdentifierException(i18nMessage("empiservice.duplicateidentifier.exception","${peId}","${healthEntity}"))
			
		def identifier = new Identifier(type:Identifier.TYPE_IDENTIFIER_PI,number:peId,assigningAuthority:healthEntity)
		if(!identifier.validate()){
			throw new IdentifierNotValidException(i18nMessage("empiservice.identifiernotvalid.exception"))
		}
		//Verifico que no contenga un identificador para esa entidad sanitaria
		if(patient.identifiers.contains(identifier) || (patient.identifiers.find{it.assigningAuthority == healthEntity} != null)){
			throw new DuplicateAuthorityIdentifierException(i18nMessage("empiservice.duplicateauthorityidentifier.exception","${healthEntity}"))
		}
		
		patient.addToIdentifiers(identifier)
		return patient
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
	def updateEntityIdentifierToPatient(Patient patient,HealthEntity he,newId,oldId=null)
	{	
		if(!patient.isAttached())
			throw new DontExistingPatientException(message:i18nMessage("empiservice.dontexistingpatient.exception"))
		
		if(newId == oldId) {return}
				
		if(Identifier.findWhere(type:Identifier.TYPE_IDENTIFIER_PI,number:newId,assigningAuthority:he) != null){
			throw new DuplicateIdentifierException()
		}
		//Busco el identificador en el paciente
		Identifier findedIdentifier = patient.identifiers.find {it.type == Identifier.TYPE_IDENTIFIER_PI && it.assigningAuthority == he}
		if(findedIdentifier)
			findedIdentifier.number = newId
		else
			throw new IdentifierNotFoundException(i18nMessage("empiservice.identifiernotfound.exception","${he}"))
		
	}
	
	/**
	 * Elimina un identifacor de una entidad sanitaria asignada a un paciente
	 * @param Patient p: el paciente que contiene el identificador de la entidad saniataria
	 * @param HealthEntity he: La entidad sanitaria de la cual se quiere eliminar el identificador
	 * @return
	 */
	def removeEntityIdentifierToPatient(Patient p, HealthEntity he)
	{
		//TODO implement me!
	}
	
	
	/**
	 * Devuelve una lista de todos los pacientes que matchean con ciertos datos demograficos
	 * @param Patient p: el paciente que se va a comparar
	 * @param Boolean includePossible: Si debo incluir los posibles matcheos
	 * @return List<MatchRecord>: Lista de los pacientes matcheados y su nivel de matcheo
	 */
	List<MatchRecord> getAllMatchedPatients(Person p,Boolean includePossible=false)
	{
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
		def query =Patient.where {uniqueId.mainId == uuid?.mainId} 
		return query.find()

	}
	
	/**
	 * Devuelve un paciente a partir del identificador que usa una entidad sanitaria para referenciarlo
	 * @param String peId: Id que utiliza la Entidad Sanitaria para el paciente
	 * @param HealthEntity he: Entidad Sanitaria que esta buscando al paciente
	 * @return Patient p: El paciente si lo encontro, sino NULL
	 */
	Patient findPatientByHealthEntityId(String peId,HealthEntity he){
		def c = Patient.createCriteria()
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
