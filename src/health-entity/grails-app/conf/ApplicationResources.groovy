import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH

modules = {
	bootstrap {
		dependsOn 'jquery'
		
		def dir = "theme";
		resource url:[dir:"${dir}/js",file:'bootstrap.min.js'], exclude: 'minimify'
		resource url:[dir:"${dir}/css",file:'bootstrap.min.css'], exclude: 'minimify'
	}
	
	theme {
		dependsOn 'bootstrap'
		
		// Load Css
		def dir = "theme";
		
		resource url:[dir:"${dir}/css",file:'style.min.css'], exclude: 'minimify'
		resource url:[dir:"${dir}/css",file:'retina.min.css'], exclude: 'minimify'
		resource url:'http://html5shim.googlecode.com/svn/trunk/html5.js',
				 disposition: 'head',
				 wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
		resource url:[ dir: "${dir}/js", file:'respond.min.js' ],
				 exclude: 'minimify',
				 disposition: 'head',
				 wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
		resource url: [dir:"${dir}/css",file:'ie6-8.css'],
				 disposition: 'head',
				 wrapper: { s -> "<!--[if lt IE 9]>$s<![endif]-->" }
				 
		resource url:[dir:"${dir}/js",file:'custom.min.js'], exclude: 'minimify'
		resource url:[dir:"${dir}/js",file:'core.min.js'], exclude: 'minimify'

	}

	application {
		dependsOn 'jquery'
		resource url:'js/application.js'
		resource url:'css/application.css'
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
