package com.janpix.rup.empi
/**
 * Servicio encargado de administrar el eMPI (enterprise Master Patient Index)
 * Da de alta, actualiza, elimina y hace merges de pacientes
 * @author martin
 *
 */
class EMPIService {

    def addPatient(Patient p,HealthEntity healthEntity1,String patientEntityId) {
		p.save(flush:true,failOnError:true)
		return p
    }
}
