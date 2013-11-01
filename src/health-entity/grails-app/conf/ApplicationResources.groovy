import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH

modules = {
    application {
        resource url:'js/application.js'
    }
	
	theme {
		dependsOn 'jquery'
		
		// Load Js
		getFilesForPath('/theme/js').each {
			resource url: it
		  }
		// Load Css
		def dir = "theme";
		resource url:[dir:"${dir}/css",file:'bootstrap.min.css']
		resource url:[dir:"${dir}/css",file:'bootstrap-responsive.min.css']
		resource url:[dir:"${dir}/css",file:'style.min.css']
		resource url:[dir:"${dir}/css",file:'style-responsive.min.css']
		resource url:[dir:"${dir}/css",file:'retina.css']
		resource url:[dir:"${dir}/css",file:'application.css']
	}
}

/**
 * Retorna todos los recursos dentro de un path
 * @param path
 * @return
 */
def getFilesForPath(path) {
		def webFileCachePaths = []
		def servletContext = SCH.getServletContext()
		
		//context isn't present when testing in integration mode. -jg
		if(!servletContext) return webFileCachePaths
		
		def realPath = servletContext.getRealPath('/')
		def appDir = new File("$realPath/$path")
		appDir.eachFileRecurse {File file ->
			if (file.isDirectory() || file.isHidden()) return
			webFileCachePaths << file.path.replace(realPath, '')
		}
		
		return webFileCachePaths
	}