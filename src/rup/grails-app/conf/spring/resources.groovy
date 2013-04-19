import java.beans.beancontext.BeanContext;

import com.janpix.rup.infrastructure.MockUUIDGenerator;
import com.janpix.rup.pixmanager.PlaceService;
import com.janpix.rup.services.mappings.PIXContractMapper;

beans = {
	uuidGenerator(MockUUIDGenerator) { bean ->
		bean.factoryMethod = "getUUID"
	}
	
	placeService(PlaceService)
	
	pixContractMapper(PIXContractMapper) {
		placeService = ref(placeService)
	}
	
}
