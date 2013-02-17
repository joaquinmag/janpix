package com.janpix.rup.pixmanager

import com.janpix.rup.services.contracts.AddNewPatientRequestMessage
import com.janpix.rup.services.contracts.AddNewPatientResponseMessage
import com.janpix.rup.exceptions.ExistingPatientException
import com.janpix.rup.exceptions.ShortDemographicDataException
import org.hl7.v3.II
import org.hl7.v3.MCCIIN000002UV01
import org.hl7.v3.PRPAIN201301UV02

/**
 * Servicio encargado de procesar las peticiones de las diferentes Entidades Sanitarias
 * y comunicarse con el EMPIService para llevar a cabo dichas peticiones
 * @author martin
 *
 */
class PixManagerService {
	//TODO ver como integrarlo al un webservice. Usar el plugin cxf
	//TODO ver como se pasan los parametros a los metodos. Si le puedo pasar un Patient o tengo que pasar todos Strings y despues armarlo
	
	static expose=['cxf']
	
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
	AddNewPatientResponseMessage addNewPatient(AddNewPatientRequestMessage patientRequestMessage){
		def ack = new MCCIIN000002UV01(itsVersion: "hola")
		def typeID = new II()
		typeID.root = "hola"
		ack.id = typeID
		
		return new AddNewPatientResponseMessage(ackMessage: ack)
		//TODO borrador del metodo
		def patient = PersonMapper.mapFromDto(patientDto)
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
	
	
	//###################
	// Agrego los metodos que deberia de tener el WS del PIX-Manager
	// Son todos para el protocolo HL7_V3
	// Ver si son todos necesarios
	//###################
	
	/**
	 * Este metodo es llamado cuando una Entidad Sanitaria agrega un nuevo paciente
	 * Si el paciente ya existe agrega el identificador de la Entidad Sanitaria a los identificadores del paciente
	 * Si el paciente no existia lo crea y asigna el identificador de la Entidad Sanitaria a los identificadores del paciente
	 * 
	 * @nameProtocol Patient Registry Record Added(IHE_ITI Vol 2b - Seccion: 3.44.4.1)
	 * @requestCode PRPA_IN201301UV02(ej: ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/01_PatientRegistryRecordAdded1.xml )
	 * @responseCode ACK : MCCI_IN000002UV01 (ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/02_PatientRegistryRecordAdded1Ack.xml)
	 * @return
	 */
	def addPatient(){
		
	}
	
	/**
	 * Este metodo es invocado cuando se quiere actualizar alguna informacion del paciente.
	 * Supongo que se incluye tanto información demografica como identificadores (por si una entidad quiere actualizar el id del paciente)
	 * 
	 * @nameProtocol Patient Registry Record Revised(IHE_ITI Vol 2b - Seccion: 3.44.4.1)
	 * @requestCode PRPA_IN201302UV02(ej: ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/04_PatientRegistryRecordRevised2.xml )
	 *TODO ver! @responseCode ACK : MCCI_IN000002UV01 (ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/02_PatientRegistryRecordAdded1Ack.xml)
	 * @return
	 */
	def updatePatient(){
		
	}
	
	/**
	 * Une 2 pacientes que estan agregados como pacientes diferentes
	 * 
	 * @nameProtocol Patient Registry Duplicates Resolved (IHE_ITI Vol 2b - Seccion: 3.44.4)
	 * @requestCode PRPA_IN201304UV02 (ej: ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/05_PatientRegistryDuplicatesResolved.xml )
	 * @return
	 */
	def mergePatients(){
		//TODO ver si es necesario
	}
	
	/**
	 * Devuelve todos los identificadores de un determinado paciente
	 * Nosotros lo podemos hacer que el CUIS sea un Identifier y devuelva solo ese
	 * 
	 * @nameProtocol Patient Registry Get Identifiers Query (IHE_ITI Vol 2b - Seccion: 3.45.4)
	 * @requestCode PRPA_IN201309UV02 (ej: ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/06_PIXQuery1.xml)
	 * @responseCode PRPA_IN201310UV02 (ej: ftp://ftp.ihe.net/TF_Implementation_Material/ITI/examples/PIXV3/07_PIXQuery1Response.xml)
	 * @return
	 */
	def getIdentifiersPatient(){
		
	}
	
}
