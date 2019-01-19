package com.radius.propertymatch.dao;

import java.util.ArrayList;

import com.radius.propertymatch.model.Property;

public interface IPropertyDao {

	public void bulkAdd(ArrayList<Property> lstProperty);
	
}
