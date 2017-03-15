package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Utility;
import com.softserve.osbb.model.House;

/**
 * Created by YaroslavStefanyshyn on 02.13.17.
 */

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Integer>{

	public List<Utility> findByOsbb(Osbb osbb);

    public List<Utility> findByHouses(House house);
}
