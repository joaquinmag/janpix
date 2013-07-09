package com.janpix.rup.pixmanager

import com.janpix.rup.empi.City
import com.janpix.rup.empi.Country;
import com.janpix.rup.empi.Province;

class PlaceService {
	static transactional = false
	
    City findByPlace(String cityName, String provinceName, String countryName) {
		def country = Country.findByName(countryName)
		if (country) {
			def province = Province.findByCountryAndName(country, provinceName)
			if (province) {
				return City.findByProvinceAndName(province, cityName)
			}
		}
		return null
    }
}
