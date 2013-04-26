import java.beans.beancontext.BeanContext;

import com.janpix.rup.infrastructure.UUIDGenerator;
import com.janpix.rup.pixmanager.PlaceService;
import com.janpix.rup.services.mappings.PIXContractMapper;

beans = {
	uuidGenerator(UUIDGenerator) { bean ->
		bean.factoryMethod = "getUUID"
	}
	
	placeService(PlaceService)
	
	pixContractMapper(PIXContractMapper) {
		placeService = ref(placeService)
	}
	
	hl7Helper(Hl7v3MessageHelper) {
		uuidGenerator = ref(uuidGenerator)
	}
	
}
