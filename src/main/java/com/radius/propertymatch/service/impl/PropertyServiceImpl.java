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
import com.radius.propertymatch.service.IPropertyService;

@Service
public class PropertyServiceImpl implements IPropertyService {

	@Autowired
	IMemoryDBService memoryDBService;

	@Autowired
	IMatchPercentageService matchPercentageService;

	@Autowired
	IPropertyDao propertyDao;

	@Autowired
	ISearchRequirementDao searchRequirementDao;

	@Override
	public void add(Property property) {
		propertyDao.add(property);
	}

	@Override
	public void bulkAdd(ArrayList<Property> lstProperty) {
		propertyDao.bulkAdd(lstProperty);
	}

	@Override
	public void bulkAddPropertiesGeoInMemory() {
		propertyDao.bulkAddPropertiesGeoInMemory();
	}

	@Override
	public List<SearchRequirement> matchPropertyWithRequirements(Property property) {
		Map<Integer, String> nearestRequirementsDistanceMap = new HashMap<Integer, String>();
		List<Integer> lstRequirementId = new ArrayList<Integer>();

		// For match distance should be within 10 miles
		GeoResults result = memoryDBService.geoRadius("Requirement", property.getLongitude(), property.getLatitude(),
				(double) 10, Metrics.MILES, GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance());
		List<GeoResult<RedisGeoCommands.GeoLocation>> locations = result.getContent();
		for (GeoResult<RedisGeoCommands.GeoLocation> location : locations) {
			Integer requirementId = Integer.parseInt((String) location.getContent().getName());
			if (requirementId != null) {
				nearestRequirementsDistanceMap.put(requirementId, location.getDistance().toString().split(" ")[0]);
				lstRequirementId.add(requirementId);
			}
		}

		List<SearchRequirement> matchedRequirements = new ArrayList<SearchRequirement>();

		if (!lstRequirementId.isEmpty()) {
			List<SearchRequirement> lstSearchRequirement = searchRequirementDao.findByIdIn(lstRequirementId);
			Iterator<SearchRequirement> it = lstSearchRequirement.iterator();

			while (it.hasNext()) {
				SearchRequirement sr = (SearchRequirement) it.next();
				int srId = sr.getId();

				//Type of property should match with requirement to consider it valid match
				if (sr.getType() == property.getType()) {

					int matchPercentage = matchPercentageService
							.matchBasedOnDistance(Double.parseDouble(nearestRequirementsDistanceMap.get(srId)));
					matchPercentage += matchPercentageService.matchBasedOnBudget(property.getPrice(), sr.getMinBudget(),
							sr.getMaxBudget());
					matchPercentage += matchPercentageService.matchBasedOnRoom(property.getNoOfBedrooms(),
							sr.getMinBedrooms(), sr.getMaxBedrooms());
					matchPercentage += matchPercentageService.matchBasedOnRoom(property.getNoOfBathrooms(),
							sr.getMinBathrooms(), sr.getMaxBathrooms());

					// All matches above 40% can only be considered useful.
					if (matchPercentage > 40) {
						sr.setMatchPercentage(matchPercentage);
						matchedRequirements.add(sr);
					}
				}
			}
		}
		return matchedRequirements;
	}

}
