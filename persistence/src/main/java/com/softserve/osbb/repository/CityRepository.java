package com.softserve.osbb.repository;

import com.softserve.osbb.model.Region;
import com.softserve.osbb.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YuriPushchalo on 16.11.2016.
 */
@Repository
public interface CityRepository extends JpaRepository<City,Integer>, JpaSpecificationExecutor<City>  {

    public City findById(Integer id);
    public List<City> findByName(String name);
    public List<City> findByRegion(Region region);

}
