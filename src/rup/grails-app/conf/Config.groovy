// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://rup.janpix.org"
    }
}

// log4j configuration
log4j = {
	appenders {
		console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
		
		file name: "demographicLog",
					maxFileSize: 2048,
					file: "/var/log/tomcat7/janpix/rup/demographicPersonsService.log"
					
		file name: 'applicationLog',
					maxFileSize: 2048,
					file: "/var/log/tomcat7/janpix/rup/application.log"
	}

	environments {
		production {
			root {
				error 'applicationLog'
			}
			debug applicationLog: [
				'grails.app.controllers.com.janpix.rup',
				'grails.app.domain.com.janpix.rup',
				'grails.app.services.com.janpix.rup',
				'grails.app.taglib.com.janpix.rup',
				'grails.app.conf.com.janpix.rup',
				'grails.app.filters.com.janpix.rup'
		   ],additivity:false
		}
		test{
			root {
				error 'applicationLog','stdout'
			}
			//Usa los appenders 'stdout' y 'applicationLog' para el nivel debug
			debug stdout: [
				'grails.app.controllers.com.janpix.rup',
				'grails.app.domain.com.janpix.rup',
				'grails.app.services.com.janpix.rup',
				'grails.app.taglib.com.janpix.rup',
				'grails.app.conf.com.janpix.rup',
				'grails.app.filters.com.janpix.rup'
			],additivity:false
		
			debug applicationLog: [
				'grails.app.controllers.com.janpix.rup',
				'grails.app.domain.com.janpix.rup',
				'grails.app.services.com.janpix.rup',
				'grails.app.taglib.com.janpix.rup',
				'grails.app.conf.com.janpix.rup',
				'grails.app.filters.com.janpix.rup'
		   ],additivity:false

		}
		development{
			root {
				error 'applicationLog','stdout'
			}
			//Usa los appenders 'stdout' y 'applicationLog' para el nivel debug
			debug stdout: [
				'grails.app.controllers.com.janpix.rup',
				'grails.app.domain.com.janpix.rup',
				'grails.app.services.com.janpix.rup',
				'grails.app.taglib.com.janpix.rup',
				'grails.app.conf.com.janpix.rup',
				'grails.app.filters.com.janpix.rup'
			],additivity:false
		
			debug applicationLog: [
				'grails.app.controllers.com.janpix.rup',
				'grails.app.domain.com.janpix.rup',
				'grails.app.services.com.janpix.rup',
				'grails.app.taglib.com.janpix.rup',
				'grails.app.conf.com.janpix.rup',
				'grails.app.filters.com.janpix.rup'
		   ],additivity:false

		}
	}
	
	//###Configuraciones ajenas a los entornos
	//#Grails
    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
	
		      
	//#Janpix
	//Log para la informacion del empi que muestra porcentajes de matcheo
	//En cualquier enviroment solo lo graba en el archivo que marca 'empi:'
	info demographicLog:'grails.app.services.com.janpix.rup.empi.DemographicPersonService',additivity:false
			
}



/**
 * Configuración CXF: plugin WS-SOAP 
 */
cxf.endpoint.soap12Binding = true

/**
 * Configuracion del servicio de matcheo de pacientes
 * En este apartado se puede configurar:
 * - Limites de matcheo (inferior y superior)
 * - Metodo de matcheo de pacientes
 **/
demographic{
	lowerBound = 0.6 //A partir de este limite se consideran posible matcheros
	upperBound = 0.8 //A partir de este limite se considera matcheos
	identity.measurementMethod = "avib"//Metodo de comparacion a utilizar (fellegi-sunter, avib, avia)
}

//Atributos de los diferentes metodos de comparacion de identidad
identityMethods{
	avib{
		weights{
			document		= 0.25
			name 			= 0.25
			birthdate		= 0.15
			sex			= 0.05
			livingplace		= 0.2
			address			= 0.1
		}
	}
	fellegi-sunter{
		
	}
}

rup {
	authority {
		rupoid = "2.16.32.1.256.0"
		name = "RUP"
	}
}




