package com.softserve.osbb.service;

import java.util.List;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.model.Drive;



public interface DriveService {

	void deleteAll();
	
	List<Drive> findAll();
	
	Drive save(Drive drive);
}
