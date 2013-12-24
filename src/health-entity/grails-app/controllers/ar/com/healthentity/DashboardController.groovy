package ar.com.healthentity

import grails.plugins.springsecurity.Secured

@Secured("hasRole('HealthWorker')")
//@Transactional(readOnly = true,noRollbackFor=[JanpixException]) // TODO ver porque no funciona
class DashboardController {

    def index = {
        render view: 'index'
    }

}
