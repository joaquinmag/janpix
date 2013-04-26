import java.beans.beancontext.BeanContext;
import com.janpix.rup.infrastructure.DateHelper
import com.janpix.rup.infrastructure.UUIDGenerator
import com.janpix.rup.pixmanager.PlaceService
import com.janpix.rup.services.helpers.Hl7v3MessageHelper
import com.janpix.rup.services.mappings.PIXContractMapper
import org.springframework.web.servlet.i18n.SessionLocaleResolver

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
	
	hl7Helper(Hl7v3MessageHelper) {
		uuidGenerator = ref(uuidGenerator)
	}
	
	placeService(PlaceService)
	
	pixContractMapper(PIXContractMapper) {
		placeService = ref(placeService)
		hl7Helper = ref(hl7Helper)
		actualDate = ref(actualDate)
	}
	
}
