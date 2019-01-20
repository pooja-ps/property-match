package com.radius.propertymatch.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radius.propertymatch.dao.IPropertyDao;
import com.radius.propertymatch.memorydb.IMemoryDBService;
import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.model.SearchRequirement;
import com.radius.propertymatch.service.IPropertyService;

@Service
public class PropertyServiceImpl implements IPropertyService {
	
	@Autowired
	IPropertyDao propertyDao;
	
	@Autowired
	IMemoryDBService memoryDBService;

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
		// TODO Add logic to match based on the 4 parameters
		return null;
	}
	
}
