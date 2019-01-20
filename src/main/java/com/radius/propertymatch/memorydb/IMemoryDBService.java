package com.radius.propertymatch.memorydb;

import org.springframework.data.geo.Distance;

public interface IMemoryDBService {

	Long geoAdd(String key,Double longitude,Double latitude,String name);
	
	Distance geoDist(String key,String first,String second);
}
