package com.softserve.osbb.repository;

import com.softserve.osbb.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yuri Pushchalo on 15.11.2016.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>, JpaSpecificationExecutor<Region> {

	public Region findById(Integer id);
	public Region findByName(String name);
	public List<Region> findAll();

}
