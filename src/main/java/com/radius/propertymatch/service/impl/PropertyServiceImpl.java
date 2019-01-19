package com.radius.propertymatch.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radius.propertymatch.dao.IPropertyDao;
import com.radius.propertymatch.model.Property;
import com.radius.propertymatch.service.IPropertyService;

@Service
public class PropertyServiceImpl implements IPropertyService {
	
	@Autowired
	IPropertyDao propertyDao;

	@Override
	public void bulkAdd(ArrayList<Property> lstProperty) {
		propertyDao.bulkAdd(lstProperty);
	}

}
