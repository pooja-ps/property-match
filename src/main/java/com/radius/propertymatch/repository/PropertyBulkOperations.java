package com.radius.propertymatch.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.radius.propertymatch.model.Property;

@Component
@Qualifier("propertybulk")
public class PropertyBulkOperations implements IPropertyBulkOperation {

	  private final JdbcTemplate template;

	  public PropertyBulkOperations(JdbcTemplate template) {
	    this.template = template;
	  }

	  @Override
	  @Transactional
	  public void bulkPersist(final List<Property> entities) {
	    template.batchUpdate("insert into property (type, latitude, longitude, no_of_bedrooms, no_of_bathrooms, price, created_at, created_by, updated_at, updated_by) values (?,?,?,?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

	      @Override
	      public void setValues(PreparedStatement ps, int i) throws SQLException {
	        ps.setInt(1,  entities.get(i).getType());
	        ps.setDouble(2,  entities.get(i).getLatitude());
	        ps.setDouble(3,  entities.get(i).getLongitude());
	        ps.setInt(4,  entities.get(i).getNoOfBedrooms());
	        ps.setInt(5,  entities.get(i).getNoOfBathrooms());
	        ps.setInt(6,  entities.get(i).getPrice());
	        ps.setDate(7,   (Date)entities.get(i).getCreatedAt());
	        ps.setString(8,  entities.get(i).getCreatedBy());
	        ps.setDate(9,  (Date)entities.get(i).getUpdatedAt());
	        ps.setString(10,   entities.get(i).getUpdatedBy());
	      }

	      @Override
	      public int getBatchSize() {
	        return entities.size();
	      }
	    });
	  }
}