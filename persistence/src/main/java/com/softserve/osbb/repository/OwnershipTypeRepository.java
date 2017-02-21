package com.softserve.osbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.OwneshipType;
/**
 * Created by Yurii.Rozhak on 15.02.2017.
 */
@Repository
public interface OwnershipTypeRepository extends JpaRepository<OwneshipType, Integer>, JpaSpecificationExecutor<OwneshipType> {

	
	 @Query("select r from OwneshipType r where r.type = :type")
	    public OwneshipType findByType(@Param("type") String type);
}
