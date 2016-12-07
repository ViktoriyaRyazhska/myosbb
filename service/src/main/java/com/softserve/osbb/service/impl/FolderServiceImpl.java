package com.softserve.osbb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.osbb.model.Folder;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.repository.FolderRepository;
import com.softserve.osbb.service.FolderService;

/**
 * 
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 06.12.2016
 *
 */
@Service
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderRepository repository;

    @Override
    public Folder save(Folder folder) {
        return repository.save(folder);
    }

    @Override
    public void delete(Folder folder) {
        repository.delete(folder);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void deleteInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public Folder update(Folder folder) {
        return repository.saveAndFlush(folder);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Folder> findAll() {
        System.out.println("In FolderServiceImpl.findAll()");
        return repository.findAll();
    }

    @Override
    public Folder findById(Integer id) {
        return repository.findById(id);
    }    

    @Override
    public List<Folder> findByParentId(Integer parentId) {
        return repository.findByParentId(parentId);
    }

    @Override
    public List<Folder> findByParent(Folder parent) {
        return repository.findByParent(parent);
    }

    @Override
    public List<Folder> findByOsbb(Osbb osbb) {
        return repository.findByOsbb(osbb);
    }

//    @Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
    @Override
    public Folder save(String folderName, Integer parentId) {
        Folder parent = repository.findById(parentId);
        System.out.println("Parent = " + parent);
        Folder saved = null;
        
        if (parent != null) {
            Folder newFolder = new Folder();
            newFolder.setName(folderName);
            newFolder.setParent(parent);
            newFolder.setOsbb(parent.getOsbb());
            saved = repository.saveAndFlush(newFolder);
            System.out.println("Saved = " + saved);
        }
        
        return saved;
    }

}
