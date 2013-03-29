import com.janpix.rup.infrastructure.MockUUIDGenerator;

beans = {
	uuidGenerator(MockUUIDGenerator) { bean ->
		bean.factoryMethod = "getUUID"
	}
}
