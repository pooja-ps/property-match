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

import com.radius.propertymatch.model.SearchRequirement;

@Component
@Qualifier("requirementbulk")
public class SearchRequirementBulkOperations implements ISearchRequirementBulkOperation{

	private final JdbcTemplate template;

	  public SearchRequirementBulkOperations(JdbcTemplate template) {
	    this.template = template;
	  }

	  @Override
	  @Transactional
	  public void bulkPersist(final List<SearchRequirement> entities) {
	    template.batchUpdate("insert into search_requirement (type, latitude, longitude, min_bedrooms, max_bedrooms, min_bathrooms, max_bathrooms, min_budget, max_budget, created_at, created_by, updated_at, updated_by) values (?,?,?,?,?,?,?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

	      @Override
	      public void setValues(PreparedStatement ps, int i) throws SQLException {
	        ps.setInt(1,  entities.get(i).getType());
	        ps.setDouble(2,  entities.get(i).getLatitude());
	        ps.setDouble(3,  entities.get(i).getLongitude());
	        ps.setObject(4,  entities.get(i).getMinBedrooms(), java.sql.Types.INTEGER);
	        ps.setObject(5,  entities.get(i).getMaxBedrooms(), java.sql.Types.INTEGER);
	        ps.setObject(6,  entities.get(i).getMinBathrooms(), java.sql.Types.INTEGER);
	        ps.setObject(7,   entities.get(i).getMaxBathrooms(), java.sql.Types.INTEGER);
	        ps.setObject(8,  entities.get(i).getMinBudget(), java.sql.Types.INTEGER);
	        ps.setObject(9,   entities.get(i).getMaxBudget(), java.sql.Types.INTEGER);
	        ps.setDate(10,   (Date)entities.get(i).getCreatedAt());
	        ps.setString(11,  entities.get(i).getCreatedBy());
	        ps.setDate(12,  (Date)entities.get(i).getUpdatedAt());
	        ps.setString(13,   entities.get(i).getUpdatedBy());
	      }

	      @Override
	      public int getBatchSize() {
	        return entities.size();
	      }
	    });
	  }
}
