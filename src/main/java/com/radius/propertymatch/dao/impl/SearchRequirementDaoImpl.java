package com.radius.propertymatch.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.radius.propertymatch.dao.ISearchRequirementDao;
import com.radius.propertymatch.model.SearchRequirement;
import com.radius.propertymatch.repository.SearchRequirementRepository;

@Repository
public class SearchRequirementDaoImpl implements ISearchRequirementDao {

	@Autowired
	private SearchRequirementRepository searchRequirementRepo;
	
	@Override
	public void add(SearchRequirement requirement) {
		searchRequirementRepo.save(requirement);
	}

	@Override
	public void bulkAdd(ArrayList<SearchRequirement> lstRequirement) {
		searchRequirementRepo.saveAll(lstRequirement);
	}
	
}
