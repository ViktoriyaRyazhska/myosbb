package com.softserve.osbb.repository;

import com.softserve.osbb.model.District;
import com.softserve.osbb.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YuriPushchalo on 23.12.2016.
 */
@Repository
public interface DistrictRepository extends JpaRepository<District,Integer>, JpaSpecificationExecutor<District> {

    public District findById(Integer id);
    public List<District> findByName(String name);
    public List<District> findByCityOrderByName(City city);

}
