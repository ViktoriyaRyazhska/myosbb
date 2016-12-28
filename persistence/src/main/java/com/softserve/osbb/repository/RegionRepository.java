package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.Region;

/**
 * Created by Yuri Pushchalo on 15.11.2016.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>, JpaSpecificationExecutor<Region> {

	public Region findById(Integer id);
	public Region findByName(String name);
	public List<Region> findAllByOrderByNameAsc();

}
