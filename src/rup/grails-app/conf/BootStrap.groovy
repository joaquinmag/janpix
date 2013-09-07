import grails.util.Environment

import com.janpix.rup.empi.AssigningAuthority
import com.janpix.rup.empi.AuthorityRUP
import com.janpix.rup.empi.City
import com.janpix.rup.empi.Country
import com.janpix.rup.empi.EmitterCountry
import com.janpix.rup.empi.HealthEntity
import com.janpix.rup.empi.Province

class BootStrap {

	def grailsApplication
	
    def init = { servletContext ->
		if (AuthorityRUP.count() == 0) {
			new AuthorityRUP(grailsApplication.config.rup.authority.rupoid, grailsApplication.config.rup.authority.name ).save(flush: true, failOnError: true)
		}
		
		//Obtenido de http://oid-info.com/get/2.16.32
		CreateEmitterCountry("2.16.32","Argentina")
		
		// Obtenidos de http://hl7.org.ar/index.php?option=com_content&task=view&id=857&Itemid=259
		CreateAssigningAuthority("2.16.840.1.113883.2.10","HL7 Argentina");
		CreateHealthEntity("2.16.840.1.113883.2.10.1","Hospital Italiano de Buenos Aires");
		CreateHealthEntity("2.16.840.1.113883.2.10.11","Clinica Parque SRL");
		CreateHealthEntity("2.16.840.1.113883.2.10.13","Municipalidad de San Isidro");
		
		//Agregados por nosotros (Creo municipio y agrego Entidades Sanitarias al municipio)
		CreateHealthEntity("2.16.840.1.113883.2.10.100","Municipalidad de Luján");
		CreateHealthEntity("2.16.840.1.113883.2.10.100.1","Hospital Municipal Luján");
		CreateHealthEntity("2.16.840.1.113883.2.10.100.2","Clinica Guemes");
		CreateHealthEntity("2.16.840.1.113883.2.10.100.3","CAP - San Pedro");
		CreateHealthEntity("2.16.840.1.113883.2.10.100.4","CAP - Parque Lasa");
		CreateHealthEntity("2.16.840.1.113883.2.10.100.5","CAP - Lanusse");
		
		if(Environment.current == Environment.TEST){
			def testAuthorityOID = "1.2.840.114350.1.13.99998.8734"
			
			def testAuthorityName = "Good Health Clinic"
			new HealthEntity(testAuthorityOID, testAuthorityName).save(flush: true, failOnError: true)
			
			def country = Country.findOrCreateWhere(name: "AR").save(failOnError: true, flush: true)
			def province = Province.findOrCreateWhere(country: country, name: "AR-S").save(failOnError: true, flush: true)
			City.findOrCreateWhere(province: province, name: "Venado Tuerto").save(failOnError: true, flush: true)
			province = Province.findOrCreateWhere(country: country, name: "AR-B").save(failOnError: true, flush: true)
			City.findOrCreateWhere(province: province, name: "Luján").save(failOnError: true, flush: true)
		}
    }
    def destroy = {
    }
	
	def CreateAssigningAuthority(String oid,String name){
		if(!AssigningAuthority.findByOid(oid))
			new AssigningAuthority(oid, name).save(flush:true,failOnError:true)
	}
	
	def CreateHealthEntity(String oid,String name){
		if(!HealthEntity.findByOid(oid))
			new HealthEntity(oid, name).save(flush:true,failOnError:true)
	}
	
	def CreateEmitterCountry(String oid,String name){
		if(!EmitterCountry.findByOid(oid))
			new EmitterCountry(oid, name).save(flush:true,failOnError:true)
	}
}
