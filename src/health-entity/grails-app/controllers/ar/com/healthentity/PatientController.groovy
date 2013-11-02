package ar.com.healthentity

import grails.plugins.springsecurity.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured(['HealthWorker'])
@Transactional(readOnly = true)
class PatientController {

    static scaffold = true
}
