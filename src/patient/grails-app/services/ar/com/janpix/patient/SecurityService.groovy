package ar.com.janpix.patient

import grails.transaction.Transactional

import org.springframework.web.context.request.RequestContextHolder

import ar.com.janpix.patient.utils.JanpixAssembler;
import com.janpix.exceptions.LoginException

@Transactional
class SecurityService {
	
	private String KEY_CURRENT_USER = "janpix_patient_user"
	
	def janpixService
	 
	/**
	 * Retorna el usuario actual
	 * @return
	 */
    def getCurrentUser() {
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
			throw new LoginException("Usuario y/o contraseña ingresados invalidos")

		// Se guarda en la session
		this.session[KEY_CURRENT_USER] = patient
			
		return "Token"
	}
	
	def logout(){
		this.session.invalidate()
	}
	
	/**
	 * Retorna true si hay algun usuario logueado
	 * @return
	 */
	def isLoggedIn(){
		PatientCommand patientLogged = this.session[KEY_CURRENT_USER]
		return (patientLogged!= null)
	}
	
	private def getSession(){
		return RequestContextHolder.currentRequestAttributes().getSession()
	}
}
