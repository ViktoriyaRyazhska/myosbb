package com.softserve.osbb.service;

import java.util.List;

import com.softserve.osbb.model.House;
import org.springframework.stereotype.Service;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Utility;

/**
 * Created by YaroslavStefanyshyn on 02.13.17.
 */
@Service
public interface UtilityService {

	Utility save(Utility utility);

	List<Utility> getAll();

	Utility findById(Integer integer);

	void delete(Integer id);

	Utility updateUtility(Utility utility);

	List<Utility> getUtilitiesByHouse(House house);
	
	public List<Utility> getUtilitiesByOsbb(Osbb osbb);
	
}
