package com.softserve.osbb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.osbb.model.OwneshipType;
import com.softserve.osbb.repository.OwnershipTypeRepository;
import com.softserve.osbb.service.OwnershipTypeService;

@Service
public class OwnershipTypeServiceImpl implements OwnershipTypeService {

	@Autowired 
	OwnershipTypeRepository ownershipTypeRepository;
	 
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<OwneshipType> getAllOwneshipType() {
		return ownershipTypeRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public OwneshipType getOwnershipType(Integer id) {
		return ownershipTypeRepository.findOne(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public OwneshipType getOwnershipType(String type) {
		return ownershipTypeRepository.findByType(type);
	}

}
