package com.softserve.osbb.repository;

import com.softserve.osbb.model.Street;
import com.softserve.osbb.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YuriPushchalo on 16.11.2016.
 */
@Repository
public interface StreetRepository extends JpaRepository<Street,Integer>, JpaSpecificationExecutor<Street> {

    public Street findById(Integer id);
    public List<Street> findByName(String name);
    public List<Street> findByCity(City city);

}
