package com.radius.propertymatch.repository;

import java.util.List;

import com.radius.propertymatch.model.SearchRequirement;

public interface ISearchRequirementBulkOperation {
	
	public void bulkPersist(List<SearchRequirement> entities);

}
