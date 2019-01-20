package com.radius.propertymatch.repository;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import java.util.stream.Stream;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.radius.propertymatch.model.SearchRequirement;

@Repository
public interface SearchRequirementRepository extends JpaRepository<SearchRequirement, Integer>{

	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
	@Query("select s from SearchRequirement s")
	Stream<SearchRequirement> streamAll();
}
