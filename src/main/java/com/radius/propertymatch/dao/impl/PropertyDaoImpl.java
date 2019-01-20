package com.radius.propertymatch.dao.impl;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.radius.propertymatch.dao.IPropertyDao;
import com.radius.propertymatch.memorydb.IMemoryDBService;
import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.repository.PropertyRepository;

@Repository
public class PropertyDaoImpl implements IPropertyDao {

	@Autowired
	IMemoryDBService memoryDBService;

	@Autowired
	private PropertyRepository propertyRepo;

	@Override
	public void add(Property property) {
		propertyRepo.save(property);
		memoryDBService.geoAdd("Property", property.getLongitude(), property.getLatitude(),
				Integer.toString(property.getId()));
	}

	@Override
	public void bulkAdd(ArrayList<Property> lstProperty) {
		propertyRepo.saveAll(lstProperty);
	}

	@Override
	@Transactional(readOnly = true)
	public void bulkAddPropertiesGeoInMemory() {
		try (Stream<Property> stream = propertyRepo.streamAll()) {
			  stream.forEach(property -> memoryDBService.geoAdd("Property", property.getLongitude(), property.getLatitude(),Integer.toString(property.getId())));
		}
	}

}
