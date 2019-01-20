package com.radius.propertymatch.dao;

import java.util.ArrayList;
import java.util.List;

import com.radius.propertymatch.model.Property;

public interface IPropertyDao {

	public void add(Property property);
	
	public void bulkAdd(ArrayList<Property> lstProperty);
	
	public void bulkAddPropertiesGeoInMemory();
	
	List<Property> findByIdIn(List<Integer> lstPropertyId);
}
