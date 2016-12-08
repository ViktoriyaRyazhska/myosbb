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
    
    void delete(Folder folder);
    
    boolean deleteById(Integer id);
    
    Folder update(Folder folder);
    
    Folder update(Integer id, String name); 
    
    List<Folder> findAll();
    
    Folder findById(Integer id);
    
    List<Folder> findByParent(Folder parent);
    
    List<Folder> findByParentId(Integer parentId);
    
    List<Folder> findByOsbb(Osbb osbb);

    Folder save(String folderName, Integer parentId);      

}
