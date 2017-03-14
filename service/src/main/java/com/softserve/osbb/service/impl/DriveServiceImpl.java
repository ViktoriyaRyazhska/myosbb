package com.softserve.osbb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.osbb.model.Drive;
import com.softserve.osbb.repository.ChatRepository;
import com.softserve.osbb.repository.DriveRepository;
import com.softserve.osbb.service.ChatService;
import com.softserve.osbb.service.DriveService;

@Service
@Transactional
public class DriveServiceImpl  implements DriveService{
	
	@Autowired
	DriveRepository driveRepository;

	@Override
	public void deleteAll() {
		driveRepository.deleteAll();
	}

	@Override
	public List<Drive> findAll() {
		return driveRepository.findAll();
	}

	@Override
	public Drive save(Drive drive) {
		return driveRepository.save(drive);
	}

}
