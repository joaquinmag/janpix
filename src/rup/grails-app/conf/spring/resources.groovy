import java.beans.beancontext.BeanContext;
import com.janpix.rup.infrastructure.DateHelper
import com.janpix.rup.infrastructure.UUIDGenerator
import com.janpix.rup.pixmanager.PlaceService
import com.janpix.rup.repository.AssigningAuthorityService
import com.janpix.rup.services.helpers.Hl7v3MessageHelper
import com.janpix.rup.services.mappings.PIXContractMapper
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import com.janpix.rup.infrastructure.I18nHelper
import com.janpix.rup.empi.FactoryMatchRecord



beans = {
	localeResolver(SessionLocaleResolver) {
		def locale = new Locale("es","ES")
		defaultLocale= locale
		Locale.setDefault (locale)
	 }
	
	uuidGenerator(UUIDGenerator) { bean ->
		bean.factoryMethod = "getUUID"
	}
	
	actualDate(DateHelper) { bean ->
		bean.factoryMethod = "getActualDate"
	}
	
	i18nMessage(I18nHelper) { bean ->
		bean.factoryMethod = "getDefaultMessageResolver"
	}
	
	hl7Helper(Hl7v3MessageHelper) {
		uuidGenerator = ref(uuidGenerator)
	}
	
	placeService(PlaceService)
	
	assigningAuthorityService(AssigningAuthorityService){
		grailsApplication = ref('grailsApplication')
	}
	
	pixContractMapper(PIXContractMapper) {
		placeService = ref(placeService)
		hl7Helper = ref(hl7Helper)
		actualDate = ref(actualDate)
		uuidGenerator = ref(uuidGenerator)
	}
	
	factoryMatchRecord(FactoryMatchRecord){
		grailsApplication = ref('grailsApplication')
	}
	

}
