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

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
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
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
 
grails.converters.encoding = "UTF-8"
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
    democonsultorio {
        grails.logging.jul.usebridge = false
    }
    demohospital {
        grails.logging.jul.usebridge = false
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

/** log4j configuration **/
levelsDebug = [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity'
	]

log4j = {
	appenders {
		console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
					
		file name: 'applicationLog',
					maxFileSize: 1024,
					file: "/var/log/tomcat7/janpix/healthentity/application.log"
					
		file name: 'localApplicationLog',
					maxFileSize: 1024,
					file: "application.log"
	}

	environments {
		production {
			root {
				error 'applicationLog'
			}
		}
		test{
			root {
				error 'applicationLog','stdout'
			}
			//Usa los appenders 'stdout' y 'applicationLog' para el nivel debug
			debug stdout: [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity'
	] ,additivity:false
		
			debug applicationLog: [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity'
	],additivity:false

		}
		development{
			root {
				error 'localApplicationLog','stdout'
			}
			//Usa los appenders 'stdout' y 'applicationLog' para el nivel debug
			debug stdout: [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity',
	'groovyx.net.ws',
	'org.apache.cxf'
	],additivity:false
		
			debug localApplicationLog: [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity',
	'groovyx.net.ws',
	'org.apache.cxf'
	],additivity:false

		}
		demohospital {
			root {
				error 'localApplicationLog','stdout'
			}
			//Usa los appenders 'stdout' y 'applicationLog' para el nivel debug
			debug stdout: [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity',
	'groovyx.net.ws',
	'org.apache.cxf'
	],additivity:false
		
			debug localApplicationLog: [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity',
	'groovyx.net.ws',
	'org.apache.cxf'
	],additivity:false

		}
		democonsultorio {
			root {
				error 'localApplicationLog','stdout'
			}
			//Usa los appenders 'stdout' y 'applicationLog' para el nivel debug
			debug stdout: [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity',
	'groovyx.net.ws',
	'org.apache.cxf'
	],additivity:false
		
			debug localApplicationLog: [
	'grails.app.controllers.ar.com.janpix.healthentity',
	'grails.app.domain.com.janpix.healthentity',
	'grails.app.services.com.janpix.healthentity',
	'grails.app.taglib.com.janpix.healthentity',
	'grails.app.conf.com.janpix.healthentity',
	'grails.app.filters.com.janpix.healthentity',
	'groovyx.net.ws',
	'org.apache.cxf'
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
}


/** CXF Client **/
service.janpix.repodoc.url = ""
service.janpix.regdoc.url = ""
service.janpix.pixmanager.serverURL = ""
healthEntity {
	name = "Hospital Zaraza"
	oid = "2.16.840.1.113883.2.10.130.503"
	admin { 
		username = "admin"
		nombre = "Juan Carlos"
		apellido = "Administrador"
	}
	esConsultorio = true
}


// set per-environment service url
environments {
	democonsultorio {
		service.janpix.regdoc.serverURL  = "http://192.168.2.2:9090/regdoc-0.1"
		service.janpix.repodoc.serverURL = "http://192.168.2.2:9090/repodoc-0.1"
		service.janpix.pixmanager.serverURL = "http://192.168.2.2:9090/rup-0.1"
		healthEntity {
			name = "Consultorio"
			oid = "2.16.840.1.113883.2.10.100.221"
			admin {
				username = "drcasa"
				nombre = "Gregorio"
				apellido = "Casa"
			}
			esConsultorio = true
		}
	}
	demohospital {
		service.janpix.regdoc.serverURL  = "http://192.168.2.2:9090/regdoc-0.1"
		service.janpix.repodoc.serverURL = "http://192.168.2.2:9090/repodoc-0.1"
		service.janpix.pixmanager.serverURL = "http://192.168.2.2:9090/rup-0.1"
		healthEntity {
			name = "Clínica San Carlos"
			oid = "2.16.840.1.113883.2.10.100.220"
			admin {
				username = "susana"
				nombre = "Susana"
				apellido = "Gimenez"
			}
			esConsultorio = false
		}
	}
	production {
		service.janpix.regdoc.serverURL  = "http://localhost:9090/regdoc-0.1"
		service.janpix.repodoc.serverURL = "http://localhost:9090/repodoc-0.1"
		service.janpix.pixmanager.serverURL = "http://localhost:9090/rup-0.1"
	}
	development {
		service.janpix.regdoc.serverURL  = "http://localhost:9090/regdoc-0.1"
		service.janpix.repodoc.serverURL = "http://localhost:9090/repodoc-0.1"
		service.janpix.pixmanager.serverURL = "http://localhost:9090/rup-0.1"
	}
	test {
		service.janpix.regdoc.serverURL  = "http://localhost:8080/regdoc"
		service.janpix.repodoc.serverURL = "http://localhost:9090/repodoc-0.1"
		service.janpix.pixmanager.serverURL = "http://localhost:9090/rup-0.1"
	}
}

service.janpix.repodoc.url = "${service.janpix.repodoc.serverURL}/services/repositorioJanpix"
service.janpix.regdoc.url = "${service.janpix.regdoc.serverURL}/services/documentJanpix"
service.janpix.pixmanager.url = "${service.janpix.pixmanager.serverURL}/services/PIXManagerJanpix"

cxf {
	client {
		janpixRepodocServiceClient {
			// para usar con grails wsdl2java
			wsdlArgs = [ "-autoNameResolution", "-validate" ]
			wsdl = "${service.janpix.repodoc.url}?wsdl"
			namespace = "com.janpix.webclient.repodoc"
			client = false
			
			// conexion con WS
			clientInterface = com.janpix.webclient.repodoc.RepositorioJanpixService
			serviceEndpointAddress = "${service.janpix.repodoc.url}"
		}
		
		janpixPixManagerServiceClient {
			// para usar con grails wsdl2java
			wsdlArgs = [ "-autoNameResolution", "-validate" ]
			wsdl = "${service.janpix.pixmanager.url}?wsdl"
			namespace = "com.janpix.webclient.rup"
			client = false

			// conexion con WS
			clientInterface = com.janpix.webclient.rup.PIXManagerJanpixService
			serviceEndpointAddress = "${service.janpix.pixmanager.url}"
		}
		
		janpixRegdocServiceClient {
			// para usar con grails wsdl2java
			wsdlArgs = [ "-autoNameResolution", "-validate" ]
			wsdl = "${service.janpix.regdoc.url}?wsdl"
			namespace = "com.janpix.webclient.regdoc"
			client = false
						
			// conexion con WS
			clientInterface = com.janpix.webclient.regdoc.DocumentJanpixService
			serviceEndpointAddress = "${service.janpix.regdoc.url}"
		}
	}
}

/** Resources **/
// Elimina el pre-procesamiento
// No genera errores al no encontrar los archivos
//grails.resources.debug = false
grails.resources.processing.enabled = false 
// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'ar.com.healthentity.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'ar.com.healthentity.UserRole'
grails.plugins.springsecurity.authority.className = 'ar.com.healthentity.Role'
grails.plugins.springsecurity.authority.nameField = 'authority'

grails.gorm.failOnError=true

/** Configuracion de los datos del RUP **/
rup {
	oid = "2.16.32.1.256.0"
	name = "RUP"
}
