package com.radius.propertymatch.service;

import java.util.ArrayList;
import java.util.List;

import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.model.SearchRequirement;

public interface ISearchRequirementService {

	public void add(SearchRequirement requirement);
	
	public void bulkAdd(ArrayList<SearchRequirement> lstRequirement);
	
	public List<Property> matchRequirementWithProperties(SearchRequirement requirement);
	
}
