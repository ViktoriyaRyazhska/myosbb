package com.softserve.osbb.repository;

import com.softserve.osbb.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.Utility;

import java.util.List;

/**
 * Created by YaroslavStefanyshyn on 02.13.17.
 */
@Repository
public interface UtilityRepository extends JpaRepository<Utility, Integer>{

    public List<Utility> findByHouses(House house);
}
