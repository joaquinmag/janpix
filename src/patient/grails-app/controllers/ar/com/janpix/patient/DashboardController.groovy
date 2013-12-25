package ar.com.janpix.patient

import com.janpix.exceptions.JanpixConnectionException
import com.janpix.exceptions.LoginException

/**
 * Controlador que se utiliza para el dashboard
 */
class DashboardController {
	
	def securityService
	
    def index() { 
		if(!securityService.isLoggedIn()){
			log.info("Usuario NO logueado")
			redirect controller:"login", action:"auth"
			return
		}
		log.info("Obteniendo usuario logueado..")
		PatientCommand patient = securityService.currentUser
		
		log.info("Se obtuvo el usuario "+patient)
		
		respond patient,[ view: 'index',model:[patientInstance:patient]]
	}
	
	
}
