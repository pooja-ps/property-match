package com.radius.propertymatch.memorydb;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;

public interface IMemoryDBService {

	Long geoAdd(String key,Double longitude,Double latitude,String name);
	
	Distance geoDist(String key,String first,String second);

	GeoResults geoRadius(String key, Double longitude, Double latitude, Double radius, Metric metric,
			GeoRadiusCommandArgs args);
}
