package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.Utility;
import com.softserve.osbb.repository.UtilityRepository;
import com.softserve.osbb.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by YaroslavStefanyshyn on 02/13/2017.
 */
@Service
public class UtilityServiceImpl implements UtilityService {

	@Autowired
	UtilityRepository utilityRepository;

	@Transactional(readOnly = false)
	@Override
	public Utility save(Utility utility) {
		return utilityRepository.saveAndFlush(utility);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Utility> getAll() {
		return utilityRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Utility findById(Integer integer) {
		return utilityRepository.findOne(integer);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void delete(Integer id) {
		utilityRepository.delete(id);

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public Utility updateUtility(Utility utility) {
		if (utilityRepository.exists(utility.getUtilityId())) {
			return utilityRepository.save(utility);
		} else {
			throw new IllegalArgumentException(
					"Utility with id=" + utility.getUtilityId() + " doesn't exist. First try to add this utility.");
		}

	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Utility> getUtilitiesByHouse(House house) {
        return utilityRepository.findByHouses(house);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Utility> getUtilitiesByOsbb(Osbb osbb) {
		return utilityRepository.findByOsbb(osbb);
	}

}
