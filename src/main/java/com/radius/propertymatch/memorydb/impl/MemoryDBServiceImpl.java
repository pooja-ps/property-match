package com.radius.propertymatch.memorydb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
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
	
}
