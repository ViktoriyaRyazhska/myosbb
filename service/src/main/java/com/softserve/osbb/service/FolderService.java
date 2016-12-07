package com.softserve.osbb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softserve.osbb.model.Folder;
import com.softserve.osbb.model.Osbb;

/**
 * Folder specific service.
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 06.12.2016
 *
 */
@Service
public interface FolderService {
    
    Folder save(Folder folder);
    
    void delete(Folder folder);
    
    void deleteAll();
    
    void deleteInBatch();
    
    Folder update(Folder folder);
    
    List<Folder> findAll();
    
    Folder findById(Integer id);
    
    List<Folder> findByParent(Folder parent);
    
    List<Folder> findByParentId(Integer parentId);
    
    List<Folder> findByOsbb(Osbb osbb);

    Folder save(String folderName, Integer parentId);

}
