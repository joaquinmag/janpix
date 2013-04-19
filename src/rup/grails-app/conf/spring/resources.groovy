import com.janpix.rup.infrastructure.MockUUIDGenerator;
import com.janpix.rup.pixmanager.PlaceService;

beans = {
	uuidGenerator(MockUUIDGenerator) { bean ->
		bean.factoryMethod = "getUUID"
	}
	
	placeService(PlaceService)
	
	
}
