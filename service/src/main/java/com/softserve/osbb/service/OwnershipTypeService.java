package com.softserve.osbb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softserve.osbb.model.OwneshipType;

@Service
public interface OwnershipTypeService {
	
	OwneshipType getOwnershipType(Integer id);
	
	OwneshipType getOwnershipType(String type);
	
	List<OwneshipType> getAllOwneshipType();
	
}
