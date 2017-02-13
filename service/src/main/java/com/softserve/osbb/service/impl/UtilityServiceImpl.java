package com.softserve.osbb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.osbb.model.Utility;
import com.softserve.osbb.repository.UtilityRepository;
import com.softserve.osbb.service.UtilityService;
/**
 * Created by YaroslavStefanyshyn on 02/13/2017.
 */
@Service
public class UtilityServiceImpl implements UtilityService{
	
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

}
