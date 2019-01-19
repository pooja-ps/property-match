package com.radius.propertymatch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radius.propertymatch.dao.ISearchRequirementDao;
import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.model.SearchRequirement;
import com.radius.propertymatch.service.ISearchRequirementService;

@Service
public class SearchRequirementServiceImpl implements ISearchRequirementService {
	
	@Autowired
	ISearchRequirementDao searchRequirementDao;
	
	@Override
	public void add(SearchRequirement requirement) {
		searchRequirementDao.add(requirement);
	}

	@Override
	public void bulkAdd(ArrayList<SearchRequirement> lstRequirement) {
		searchRequirementDao.bulkAdd(lstRequirement);
	}

	@Override
	public List<Property> matchRequirementWithProperties(SearchRequirement requirement) {
		// TODO Add logic to match based on the 4 parameters
		return null;
	}
}
