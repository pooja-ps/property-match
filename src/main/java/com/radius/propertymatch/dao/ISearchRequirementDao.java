package com.radius.propertymatch.dao;

import java.util.ArrayList;
import java.util.List;

import com.radius.propertymatch.model.SearchRequirement;

public interface ISearchRequirementDao {

	public void add(SearchRequirement requirement);
	
	public void bulkAdd(ArrayList<SearchRequirement> lstRequirement);
	
	public void bulkAddRequirementsGeoInMemory();
	
	public List<SearchRequirement> findByIdIn(List<Integer> lstRequirementId);
}
