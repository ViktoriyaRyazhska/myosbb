package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softserve.osbb.model.Folder;
import com.softserve.osbb.model.Osbb;

/**
 * Folder specific extension of org.springframework.data.jpa.repository.JpaRepository.
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 06.12.2016
 *
 */
public interface FolderRepository extends JpaRepository<Folder, Integer> {

    Folder findById(Integer id);
    List<Folder> findByParent(Folder parent);
    List<Folder> findByOsbb(Osbb osbb);
    
}
