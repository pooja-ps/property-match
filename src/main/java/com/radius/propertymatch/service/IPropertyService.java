package com.radius.propertymatch.service;

import java.util.ArrayList;
import java.util.List;

import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.model.SearchRequirement;

public interface IPropertyService {

	public void add(Property property);
	
	public void bulkAdd(ArrayList<Property> lstProperty);
	
	public List<SearchRequirement> matchPropertyWithRequirements(Property property);
}
