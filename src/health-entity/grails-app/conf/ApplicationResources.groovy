import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH

modules = {
	jquerytheme {
		def dir = "theme"
		resource url:[dir:"${dir}/js", file:'jquery-1.10.2.min.js'],
			exclude: 'minimify',
			wrapper: { s -> "<!--[if IE]>$s<![endif]-->" }
		resource url:[dir:"${dir}/js", file:'jquery-2.0.3.min.js'],
			exclude: 'minimify',
			wrapper: { s -> "<!--[if !IE]>-->$s<!--<![endif]-->" }
		resource url:[dir:'js', file:'theme-jquery.js'],
			wrapper: { s -> "<!--[if !IE]>-->$s<!--<![endif]-->" }
		resource url:[dir:'js', file:'theme-jquery-ie.js'],
			wrapper: { s -> "<!--[if IE]>$s<![endif]-->" }
		resource url:[dir:"${dir}/js",file:'jquery-migrate-1.2.1.min.js'], exclude: 'minimify'
	}

	bootstrap {
		dependsOn 'jquerytheme'
		def dir = "theme"
		resource url:[dir:"${dir}/js",file:'bootstrap.min.js'], exclude: 'minimify'
		resource url:[dir:"${dir}/css",file:'bootstrap.min.css'], exclude: 'minimify'
	}

	dropzonecss {
		def dir = "theme"
		resource url:[dir:"${dir}/css",file:'dropzone.css']
	}
	dropzone {
		dependsOn 'dropzonecss'
		def dir = "theme"
		resource url:[dir:"${dir}/js",file:'dropzone.min.js'], exclude: 'minimify'
	}
	
	multiplefileinput {
		dependsOn 'jquerytheme'
		resource url:[dir:'css', file: 'multiplefileinput.css']
		resource url:[dir:'js', file: 'multiplefileinput.js']
	}

	chosencss {
		def dir = "theme"
		resource url:[dir: "${dir}/css",file:"chosen.css"]
	}
	chosenjquery {
		dependsOn 'chosencss'
		def dir = "theme"
		resource url:[dir:"${dir}/js",file:'jquery.chosen.min.js'], exclude: 'minimify'
	}

	xchartscss {
		def dir = "theme"
		resource url:[dir: "${dir}/css",file:"xcharts.min.css"], exclude: 'minimify'
	}
	xcharts {
		dependsOn 'xchartscss'
		def dir = "theme"
		resource url:[dir:"${dir}/js",file:'xcharts.min.js'], exclude: 'minimify'
	}

	jqueryeasypiechartcss {
		def dir = "theme"
		resource url:[dir: "${dir}/css",file:"jquery.easy-pie-chart.css"]
	}
	jqueryeasypiechart {
		dependsOn 'jqueryeasypiechartcss'
		def dir = "theme"
		resource url:[dir:"${dir}/js",file:'jquery.easy-pie-chart.min.js'], exclude: 'minimify'
	}
	
	bootstrapdatepicker {
		dependsOn 'bootstrap'
		def dir = "theme"
		resource url:[dir:"${dir}/js",file:'bootstrap-datepicker.min.js'], exclude: 'minimify' 
	}
	select2 {
		dependsOn 'bootstrap'
		resource url:[dir:"js",file:'select2.min.js'], exclude: 'minimify'
		resource url:[dir:"js",file:'select2_locale_es.js']
		resource url:[dir:"css",file:'select2.css']
		resource url:[dir:"css",file:'select2-bootstrap.css']
	}
	jquerynestable {
		dependsOn 'jquerytheme'
		resource url:[dir:"js",file:'jquery.nestable.js']
		resource url:[dir:"css",file:'nestable.css']
	}
	theme {
		dependsOn 'bootstrap'
		dependsOn 'dropzonecss'
		dependsOn 'select2'
		dependsOn 'xchartscss'
		dependsOn 'jqueryeasypiechartcss'
		
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
		dependsOn 'jquerytheme'
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
