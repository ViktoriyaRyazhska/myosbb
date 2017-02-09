package com.softserve.osbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.OwneshipType;

/**
 * Created by Yurii.Rozhak on 09.02.2017.
 */
@Repository
public interface OwnershipRepository extends JpaRepository<OwneshipType, Integer> {

	@Query("select r from OwneshipType o where o.name = :name")
	public OwneshipType findByName(@Param("name") String name);

}