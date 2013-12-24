package ar.com.janpix.patient

import grails.transaction.Transactional

import org.springframework.web.context.request.RequestContextHolder

import ar.com.janpix.patient.utils.JanpixAssembler;
import com.janpix.exceptions.LoginException

@Transactional
class SecurityService {
	
	private String KEY_CURRENT_USER = "janpix_patient_user"
	
	def janpixService
	 
	// TODO por ahora HardCode
    def getCurrentUser() {
		/*PatientCommand user = new PatientCommand()
		user.cuis = "3410ea1d-9f5b-4485-8001-e3c4de3687ee"
		user.user = "mbarnech"
		user.firstname = "Martin"
		user.lastname = "Barnech"
		user.mail = "mgbarnech@gmail.com"
		
		return user*/
		return this.session[KEY_CURRENT_USER]
    }
	
	/**
	 * Se conecta contra el RUP
	 * Obtiene el paciente si es que existe y lo guarda en la session
	 * @param user
	 * @return
	 */
	def login(UserCommand user){
		
		log.info("logueando usuario "+user)
		PatientCommand patient = janpixService.validateUser(user)
		if(!patient)
			throw new LoginException("Error en el usuario y/o contraseña ingresados")

		// Se guarda en la session
		this.session[KEY_CURRENT_USER] = patient
			
		return "Token"
	}
	
	/**
	 * Retorna true si hay algun usuario logueado
	 * @return
	 */
	def isLoggedIn(){
		PatientCommand patientLogged = this.session[KEY_CURRENT_USER]
		return (patientLogged != null)
	}
	
	private def getSession(){
		return RequestContextHolder.currentRequestAttributes().getSession()
	}
}
