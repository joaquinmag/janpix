package com.janpix.healthentity

import ar.com.healthentity.City



class PlaceService {
	static transactional = false
	
	City findByPlace(String cityName, String provinceName)
	{
		def hql = "FROM City WHERE name=:cityName AND province.name = :provinceName"
		def arguments = ["cityName":cityName,"provinceName":provinceName]
		
		def result = City.find(hql,arguments);
		return result
	
	}
}
