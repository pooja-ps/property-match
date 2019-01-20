package com.radius.propertymatch.repository;

import java.util.List;

import com.radius.propertymatch.model.Property;


public interface IPropertyBulkOperation {

  public void bulkPersist(List<Property> entities);

}