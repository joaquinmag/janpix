package com.janpix.rup.pixmanager

import com.janpix.rup.empi.City
import com.janpix.rup.empi.Country;
import com.janpix.rup.empi.Province;

class PlaceService {
	static transactional = false
	
    City findByPlace(String cityName, String provinceName, String countryName) 
	{
		def hql = "FROM City WHERE name=:cityName AND province.name = :provinceName"
		def arguments = ["cityName":cityName,"provinceName":provinceName]
		
		if(countryName){
			hql += " AND province.country.name = :countryName"
			arguments["countryName"]=countryName
		}
		def result = City.find(hql,arguments);
		return result
	
    }
}
