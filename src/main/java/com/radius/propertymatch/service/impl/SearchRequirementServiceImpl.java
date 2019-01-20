package com.radius.propertymatch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.stereotype.Service;

import com.radius.propertymatch.dao.IPropertyDao;
import com.radius.propertymatch.dao.ISearchRequirementDao;
import com.radius.propertymatch.memorydb.IMemoryDBService;
import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.model.SearchRequirement;
import com.radius.propertymatch.service.IMatchPercentageService;
import com.radius.propertymatch.service.ISearchRequirementService;

@Service
public class SearchRequirementServiceImpl implements ISearchRequirementService {

	@Autowired
	IMemoryDBService memoryDBService;

	@Autowired
	IMatchPercentageService matchPercentageService;

	@Autowired
	ISearchRequirementDao searchRequirementDao;

	@Autowired
	IPropertyDao propertyDao;

	@Override
	public void add(SearchRequirement requirement) {
		searchRequirementDao.add(requirement);
	}

	@Override
	public void bulkAdd(ArrayList<SearchRequirement> lstRequirement) {
		searchRequirementDao.bulkAdd(lstRequirement);
	}

	@Override
	public void bulkAddRequirementsGeoInMemory() {
		searchRequirementDao.bulkAddRequirementsGeoInMemory();
	}

	@Override
	public List<Property> matchRequirementWithProperties(SearchRequirement requirement) {
		Map<Integer, String> nearestPropertiesDistanceMap = new HashMap<Integer, String>();
		List<Integer> lstPropertyId = new ArrayList<Integer>();

		// For match distance should be within 10 miles
		GeoResults result = memoryDBService.geoRadius("Property", requirement.getLongitude(), requirement.getLatitude(),
				(double) 10, Metrics.MILES, GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance());
		List<GeoResult<RedisGeoCommands.GeoLocation>> locations = result.getContent();
		for (GeoResult<RedisGeoCommands.GeoLocation> location : locations) {
			Integer propertyId = Integer.parseInt((String) location.getContent().getName());
			if (propertyId != null) {
				nearestPropertiesDistanceMap.put(propertyId, location.getDistance().toString().split(" ")[0]);
				lstPropertyId.add(propertyId);
			}
		}

		List<Property> matchedProperties = new ArrayList<Property>();

		if (!lstPropertyId.isEmpty()) {
			List<Property> lstProperty = propertyDao.findByIdIn(lstPropertyId);
			Iterator<Property> it = lstProperty.iterator();

			while (it.hasNext()) {
				Property pr = (Property) it.next();
				int prId = pr.getId();

				//Type of property should match with requirement to consider it valid match
				if (pr.getType() == requirement.getType()) {

					int matchPercentage = matchPercentageService
							.matchBasedOnDistance(Double.parseDouble(nearestPropertiesDistanceMap.get(prId)));
					matchPercentage += matchPercentageService.matchBasedOnBudget(pr.getPrice(),
							requirement.getMinBudget(), requirement.getMaxBudget());
					matchPercentage += matchPercentageService.matchBasedOnRoom(pr.getNoOfBedrooms(),
							requirement.getMinBedrooms(), requirement.getMaxBedrooms());
					matchPercentage += matchPercentageService.matchBasedOnRoom(pr.getNoOfBathrooms(),
							requirement.getMinBathrooms(), requirement.getMaxBathrooms());

					// All matches above 40% can only be considered useful.
					if (matchPercentage > 40) {
						pr.setMatchPercentage(matchPercentage);
						matchedProperties.add(pr);
					}
				}
			}
		}
		return matchedProperties;
	}
}
