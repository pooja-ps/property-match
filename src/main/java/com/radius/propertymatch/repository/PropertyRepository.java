package com.radius.propertymatch.repository;

import java.util.stream.Stream;

import javax.persistence.QueryHint;
import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.radius.propertymatch.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer>{

	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
	@Query("select p from Property p")
	Stream<Property> streamAll();
	
}
