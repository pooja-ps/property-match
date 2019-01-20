package com.radius.propertymatch.memorydb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.radius.propertymatch.memorydb.IMemoryDBService;

@Service
public class MemoryDBServiceImpl implements IMemoryDBService{

	 @Autowired
	 public RedisTemplate<Object, Object> redisTemplate;
	
	@Override
    public Long geoAdd(String key, Double longitude, Double latitude, String name) {
        return redisTemplate.opsForGeo().add(key,new Point(longitude,latitude),name);
    }
	
	@Override
    public Distance geoDist(String key, String first, String second) {
        return redisTemplate.opsForGeo().distance(key,first,second);
    }
	
    @Override
    public GeoResults<?> geoRadius(String key, Double longitude, Double latitude, Double radius,Metric metric, RedisGeoCommands.GeoRadiusCommandArgs args) {
        return redisTemplate.opsForGeo().radius(key,new Circle(new Point(longitude,latitude),new Distance(radius,metric)),args);
    }
	
}
