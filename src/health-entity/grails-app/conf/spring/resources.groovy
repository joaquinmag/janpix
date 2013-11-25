import org.springframework.web.servlet.i18n.SessionLocaleResolver

import com.janpix.healthentity.PlaceService
import ar.com.healthentity.janpix.StudyTypeRepository


// Place your Spring DSL code here
beans = {
	localeResolver(SessionLocaleResolver) {
		def locale = new Locale("es","ES")
		defaultLocale= locale
		Locale.setDefault (locale)
	 }
	
	placeService(PlaceService)

	studyTypeService(StudyTypeRepository)

}
