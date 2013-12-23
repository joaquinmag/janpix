package ar.com.janpix.patient

import com.janpix.exceptions.LoginException

/**
 * Controlador que se utiliza para realizar 
 * el loging de la aplicacion
 */
class LoginController {
	
	def securityService
	
    def index() { }
	
	/**
	 * Muestra la pagina de loging
	 * @return
	 */
	def auth(){
		render view:"auth"
	}
	
	/**
	 * Muestra la pagina principal una vez logueado
	 * @return
	 */
	def authSuccess(){
		redirect controller:"patient", action:"listPatientStudies"
	}
	
	/**
	 * Loguea al usuario
	 * @return
	 */
	def login(){
		UserCommand user = new UserCommand()
		user.user = params.user
		user.pass = params.pass
		
		log.info("Se recibio user="+params.user+" y pass="+params.pass)
		try{
			String token = securityService.login(user)
			redirect action:"authSuccess"
			
		}catch(LoginException e){
			flash.message = e.message
			redirect action:"auth";
		}
	}
}
