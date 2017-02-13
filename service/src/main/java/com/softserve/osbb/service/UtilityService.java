package com.softserve.osbb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softserve.osbb.model.Utility;
/**
 * Created by YaroslavStefanyshyn on 02.13.17.
 */
@Service
public interface UtilityService {

	Utility save(Utility utility);
	
	List<Utility> getAll();
}
