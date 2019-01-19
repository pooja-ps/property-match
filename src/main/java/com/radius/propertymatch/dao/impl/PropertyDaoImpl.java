package com.radius.propertymatch.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.radius.propertymatch.dao.IPropertyDao;
import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.repository.PropertyRepository;

@Repository
public class PropertyDaoImpl implements IPropertyDao {
	
	@Autowired
	private PropertyRepository propertyRepo;
	
	@Override
	public void add(Property property) {
		propertyRepo.save(property);
	}

	@Override
	public void bulkAdd(ArrayList<Property> lstProperty) {
		propertyRepo.saveAll(lstProperty);
	}

}
