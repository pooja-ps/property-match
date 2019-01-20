package com.radius.propertymatch.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.radius.propertymatch.dao.ISearchRequirementDao;
import com.radius.propertymatch.memorydb.IMemoryDBService;
import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.model.SearchRequirement;
import com.radius.propertymatch.repository.SearchRequirementRepository;

@Repository
public class SearchRequirementDaoImpl implements ISearchRequirementDao {
	
	@Autowired
	IMemoryDBService memoryDBService;

	@Autowired
	private SearchRequirementRepository searchRequirementRepo;
	
	@Override
	public void add(SearchRequirement requirement) {
		searchRequirementRepo.save(requirement);
		memoryDBService.geoAdd("Requirement", requirement.getLongitude(), requirement.getLatitude(),Integer.toString(requirement.getId()));
	}

	@Override
	public void bulkAdd(ArrayList<SearchRequirement> lstRequirement) {
		searchRequirementRepo.saveAll(lstRequirement);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void bulkAddRequirementsGeoInMemory() {
		try (Stream<SearchRequirement> stream = searchRequirementRepo.streamAll()) {
			  stream.forEach(requirement -> memoryDBService.geoAdd("Requirement", requirement.getLongitude(), requirement.getLatitude(),Integer.toString(requirement.getId())));
		}
	}
	
	@Override
	public List<SearchRequirement> findByIdIn(List<Integer> lstRequirementId){
		return searchRequirementRepo.findByIdIn(lstRequirementId);
	}
	
}
