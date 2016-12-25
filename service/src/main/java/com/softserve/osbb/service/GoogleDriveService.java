package com.softserve.osbb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.services.drive.model.File;

/**
 * GoogleDrive-specific service.
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 24.12.2016
 */
@Service
public interface GoogleDriveService {
    
    File create(String name, String parentId);
    
    String delete(String id);
    
    File getFile(String id);
    
    List<File> getAll();
    
    List<File> findByParentId(String id);
    
    File update(String id, String name);
}
